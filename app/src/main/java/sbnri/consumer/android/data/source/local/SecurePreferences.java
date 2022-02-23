package sbnri.consumer.android.data.source.local;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Base64;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.Nullable;
import sbnri.consumer.android.R;
import sbnri.consumer.android.util.AuthorizationUtils;
import sbnri.consumer.android.util.Optional;


/**
 */
public class SecurePreferences implements SharedPreferences {

    //the backing pref file
    private SharedPreferences preferences;

    //Keys for current session
    private SecretKey key;
    private String keyString;
    private String keyName;

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String UTF_8 = "UTF-8";

    public SecurePreferences(Context context) {
        try {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SecretKey generatedKey = generateAesKey(context);
            keyName = hashPrefKey(encodeKeyToString(generatedKey));
            if (preferences.contains(keyName)) {
                try {
                    keyString = decrypt(generatedKey, preferences.getString(keyName, null));
                    this.key = decodeStringToKey(keyString);
                    return;
                } catch (Exception e) {
                    //Logger.e(e, e.getMessage());
                }
            }
            generatedKey();
            String key = encrypt(encodeKeyToString(this.key), generatedKey);
            edit().putEncryptedKey(keyName, key);
        } catch (Exception e) {
            //Logger.e(e.getMessage());
        }
    }

    public boolean isCipherKeyNameEncypted(String key) {
        return keyName == key;
    }

    /**
     * Uses device and application values to generate the pref key for the encryption key
     *
     * @param context should be ApplicationContext not Activity
     * @return String to be used as the AESkey Pref key
     */
    private SecretKey generateAesKey(Context context) {
        final String password = context.getPackageName();
        final String salt = getSalt(context);
        return AuthorizationUtils.generateKey(context,salt, password);
    }

    private void generatedKey() {
        byte[] key = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(key);
        this.key = new SecretKeySpec(key, ALGORITHM);
    }

    private String encodeKeyToString(SecretKey secretKey) {
        return Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
    }

    private SecretKey decodeStringToKey(String secretKey) {
        byte[] key = Base64.decode(secretKey, Base64.DEFAULT);
        return new SecretKeySpec(key, 0, key.length, ALGORITHM);
    }

    private String decrypt(SecretKey generatedKey, String toDecrypt) throws Exception {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(Base64.decode(toDecrypt, Base64.NO_WRAP));
            int ivLength = byteBuffer.getInt();
            if (ivLength < 12 || ivLength >= 16) { // check input parameter
                throw new IllegalArgumentException("invalid iv length");
            }
            byte[] iv = new byte[ivLength];
            byteBuffer.get(iv);
            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);

            final Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            AlgorithmParameterSpec parameterSpec;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                parameterSpec = new GCMParameterSpec(128, iv);
            } else {
                parameterSpec = new IvParameterSpec(iv);
            }
            cipher.init(Cipher.DECRYPT_MODE, generatedKey, parameterSpec);
            byte[] plainText = cipher.doFinal(cipherText);
            //Paranoia
            Arrays.fill(iv, (byte) 0);
            Arrays.fill(cipherText, (byte) 0);

            return new String(plainText, UTF_8);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private String getSalt(Context context) {
        return context.getString(R.string.temp_s);
    }

    private String encrypt(String toEncrypt, SecretKey key) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[12]; //NEVER REUSE THIS IV WITH SAME KEY
        secureRandom.nextBytes(iv);
        final Cipher cipher;
        try {
            cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            AlgorithmParameterSpec parameterSpec;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                parameterSpec = new GCMParameterSpec(128, iv);
            } else {
                parameterSpec = new IvParameterSpec(iv);
            }
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            byte[] cipherText = cipher.doFinal(toEncrypt.getBytes());
            ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + cipherText.length);
            byteBuffer.putInt(iv.length);
            byteBuffer.put(iv);
            byteBuffer.put(cipherText);
            return Base64.encodeToString(byteBuffer.array(), Base64.NO_WRAP);
        } catch (Exception e) {
            //Logger.e(e, e.getMessage());
            throw new Exception(e);
        }
    }

    private String encrypt(String toEncrypt) throws Exception {
        return encrypt(toEncrypt, key);
    }

    private String decrypt(String toDecrypt) throws Exception {
        return decrypt(key, toDecrypt);
    }

    /**
     * The aes key must be same each time so we're using a hash to obscure the stored value
     *
     * @param prefKey
     * @return SHA-256 Hash of the preference key
     */
    public static String hashPrefKey(String prefKey) {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = prefKey.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);

            return Base64.encodeToString(digest.digest(), Base64.DEFAULT);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
           // Logger.e(e, "Problem generating hash " + e.getMessage());
        }
        return prefKey;
    }

    /**
     * @return map of with decrypted values (excluding the key if present)
     */
    @Override
    public Map<String, String> getAll() {
        //wont be null as per http://androidxref.com/5.1.0_r1/xref/frameworks/base/core/java/android/app/SharedPreferencesImpl.java
        final Map<String, ?> encryptedMap = preferences.getAll();
        final Map<String, String> decryptedMap = new HashMap<>(
                encryptedMap.size());
        for (Map.Entry<String, ?> entry : encryptedMap.entrySet()) {
            try {
                Object cipherText = entry.getValue();
                //don't include the key
                if (cipherText != null && !cipherText.equals(keyString)) {
                    //the prefs should all be strings
                    decryptedMap.put(entry.getKey(),
                            decrypt(cipherText.toString()));
                }
            } catch (Exception e) {
               // Logger.e(e, "error during getAll");
                // Ignore issues that unencrypted values and use instead raw cipher text string
                decryptedMap.put(entry.getKey(),
                        entry.getValue().toString());
            }
        }
        return decryptedMap;
    }


    public Map<String, ?> getUnencryptedAll() {
        //wont be null as per http://androidxref.com/5.1.0_r1/xref/frameworks/base/core/java/android/app/SharedPreferencesImpl.java
        final Map<String, ?> encryptedMap = preferences.getAll();
        return encryptedMap;
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        final String encryptedValue = preferences.getString(SecurePreferences.hashPrefKey(key), null);
        try {
            return encryptedValue != null ? Optional.orElse(decrypt(encryptedValue), defValue).get() : defValue;
        } catch (Exception e) {
            //Logger.e(e, e.getMessage());
            return defValue;
        }
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        final Set<String> encryptedSet = preferences.getStringSet(
                SecurePreferences.hashPrefKey(key), null);
        if (encryptedSet == null) {
            return defValues;
        }
        final Set<String> decryptedSet = new HashSet<>(
                encryptedSet.size());
        for (String encryptedValue : encryptedSet) {
            try {
                decryptedSet.add(decrypt(encryptedValue));
            } catch (Exception e) {
                //Logger.e(e, e.getMessage());
            }
        }
        return decryptedSet;
    }

    @Override
    public int getInt(String key, int defValue) {
        final String encryptedValue = preferences.getString(SecurePreferences.hashPrefKey(key), null);
        try {
            String decryptedValue = encryptedValue != null ? Optional.orElse(decrypt(encryptedValue), null).get() : null;
            return decryptedValue != null ? Integer.parseInt(decryptedValue) : defValue;
        } catch (Exception e) {
            return defValue;
        }
    }

    @Override
    public long getLong(String key, long defValue) {
        final String encryptedValue = preferences.getString(SecurePreferences.hashPrefKey(key), null);
        try {
            String decryptedValue = encryptedValue != null ? Optional.orElse(decrypt(encryptedValue), null).get() : null;
            return decryptedValue != null ? Long.parseLong(decryptedValue) : defValue;
        } catch (Exception e) {
            return defValue;
        }
    }

    @Override
    public float getFloat(String key, float defValue) {
        final String encryptedValue = preferences.getString(SecurePreferences.hashPrefKey(key), null);
        try {
            String decryptedValue = encryptedValue != null ? Optional.orElse(decrypt(encryptedValue), null).get() : null;
            return decryptedValue != null ? Float.parseFloat(decryptedValue) : defValue;
        } catch (Exception e) {
            return defValue;
        }
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        final String encryptedValue = preferences.getString(SecurePreferences.hashPrefKey(key), null);
        try {
            String decryptedValue = encryptedValue != null ? Optional.orElse(decrypt(encryptedValue), null).get() : null;
            return decryptedValue != null ? Boolean.parseBoolean(decryptedValue) : defValue;
        } catch (Exception e) {
            return defValue;
        }
    }

    public boolean getUnencryptedBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    @Override
    public boolean contains(String key) {
        return preferences.contains(SecurePreferences.hashPrefKey(key));
    }

    @Override
    public Editor edit() {
        return new Editor();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(
            final OnSharedPreferenceChangeListener listener) {
        preferences
                .registerOnSharedPreferenceChangeListener(listener);
    }

    /**
     * @param listener    OnSharedPreferenceChangeListener
     * @param decryptKeys Callbacks receive the "key" parameter decrypted
     */
    public void registerOnSharedPreferenceChangeListener(
            final OnSharedPreferenceChangeListener listener, boolean decryptKeys) {

        if (!decryptKeys) {
            registerOnSharedPreferenceChangeListener(listener);
        }
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(
            OnSharedPreferenceChangeListener listener) {
        preferences
                .unregisterOnSharedPreferenceChangeListener(listener);
    }

    /**
     * Wrapper for Android's {@link SharedPreferences.Editor}.
     * <p>
     * Used for modifying values in a {@link SecurePreferences} object. All
     * changes you make in an editor are batched, and not copied back to the
     * original {@link SecurePreferences} until you call {@link #commit()} or
     * {@link #apply()}.
     */
    public final class Editor implements SharedPreferences.Editor {
        private SharedPreferences.Editor mEditor;

        /**
         * Constructor.
         */
        private Editor() {
            mEditor = preferences.edit();
        }


        public SharedPreferences.Editor putEncryptedKey(String key, String value) {
            try {
                mEditor.putString(key, value).apply();
            } catch (Exception e) {
                //Logger.e(e, e.getMessage());
            }
            return this;
        }

        @Override
        public SharedPreferences.Editor putString(String key, String value) {
            try {
                mEditor.putString(SecurePreferences.hashPrefKey(key),
                        encrypt(value)).apply();
            } catch (Exception e) {
                //Logger.e(e, e.getMessage());
            }
            return this;
        }

        /**
         * This is useful for storing values that have be encrypted by something
         * else or for testing
         *
         * @param key   - encrypted as usual
         * @param value will not be encrypted
         * @return
         */
        public SharedPreferences.Editor putUnencryptedString(String key,
                                                             String value) {
            mEditor.putString(SecurePreferences.hashPrefKey(key), value).apply();
            return this;
        }

        @Override
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public SharedPreferences.Editor putStringSet(String key,
                                                     Set<String> values) {
            final Set<String> encryptedValues = new HashSet<>(
                    values.size());
            for (String value : values) {
                try {
                    encryptedValues.add(encrypt(value));
                } catch (Exception e) {
                    //Logger.e(e, e.getMessage());
                }
            }
            mEditor.putStringSet(SecurePreferences.hashPrefKey(key),
                    encryptedValues).apply();
            return this;
        }

        @Override
        public SharedPreferences.Editor putInt(String key, int value) {
            try {
                mEditor.putString(SecurePreferences.hashPrefKey(key),
                        encrypt(Integer.toString(value))).apply();
            } catch (Exception e) {
                //Logger.e(e, e.getMessage());
            }
            return this;
        }

        @Override
        public SharedPreferences.Editor putLong(String key, long value) {
            try {
                mEditor.putString(SecurePreferences.hashPrefKey(key),
                        encrypt(Long.toString(value))).apply();
            } catch (Exception e) {
                //Logger.e(e, e.getMessage());
            }
            return this;
        }

        @Override
        public SharedPreferences.Editor putFloat(String key, float value) {
            try {
                mEditor.putString(SecurePreferences.hashPrefKey(key),
                        encrypt(Float.toString(value))).apply();
            } catch (Exception e) {
                //Logger.e(e, e.getMessage());
            }
            return this;
        }

        @Override
        public SharedPreferences.Editor putBoolean(String key, boolean value) {
            try {
                mEditor.putString(SecurePreferences.hashPrefKey(key),
                        encrypt(Boolean.toString(value))).apply();
            } catch (Exception e) {
               // Logger.e(e, e.getMessage());
            }
            return this;
        }

        public SharedPreferences.Editor putUnencryptedBoolean(String key, boolean value) {
            try {
                mEditor.putBoolean(key, value).apply();
            } catch (Exception e) {
                //Logger.e(e, e.getMessage());
            }
            return this;
        }

        @Override
        public SharedPreferences.Editor remove(String key) {
            mEditor.remove(SecurePreferences.hashPrefKey(key));
            return this;
        }

        public SharedPreferences.Editor removeUnencryptedKey(String key) {
            mEditor.remove(key);
            return this;
        }


        @Override
        public SharedPreferences.Editor clear() {
            mEditor.clear();
            return this;
        }

        @Override
        public boolean commit() {
            return mEditor.commit();
        }

        @Override
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public void apply() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                mEditor.apply();
            } else {
                commit();
            }
        }
    }
}
