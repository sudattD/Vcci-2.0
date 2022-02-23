package sbnri.consumer.android.util;

import android.content.Context;
import android.util.Base64;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sbnri.consumer.android.BuildConfig;
import sbnri.consumer.android.R;
import sbnri.consumer.android.data.models.TempAuthorization;

public class AuthorizationUtils {

    public static String getTempAuthorization(Context context) {
        Long tsLong = System.currentTimeMillis();
        TempAuthorization tempAuthorization = new TempAuthorization(tsLong.toString(), context.getString(R.string.temp_ac), context.getString(R.string.rest_client_app_name), BuildConfig.VERSION_CODE);
        return new Gson().toJson(tempAuthorization);
    }

    public static String encrypt(Context context, String salt, String iv, String passphrase, String plaintext) {
        try {
            SecretKey key = generateKey(context,salt, passphrase);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, plaintext.getBytes("UTF-8"));
            return base64(encrypted);
        } catch (UnsupportedEncodingException e) {
            throw fail(e);
        }
    }

    public static String getRandomHexString(int numchars) {
        SecureRandom r = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < numchars) {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

    public static String decrypt(Context context, String salt, String iv, String passphrase, String ciphertext) {
        try {
            SecretKey key = generateKey(context,salt, passphrase);
            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, base64(ciphertext));
            if (decrypted == null) return "";
            return new String(decrypted, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw fail(e);
        }
    }

    private static byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) {
        try {
//            Logger.i("Hex iv: " + hex(iv));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
            return cipher.doFinal(bytes);
        } catch (InvalidKeyException
                | InvalidAlgorithmParameterException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw fail(e);
        } catch (NoSuchPaddingException e) {
           // Logger.d(e.getLocalizedMessage());
        } catch (NoSuchAlgorithmException e) {
           // Logger.d(e.getLocalizedMessage());
        }
        return null;
    }

    public static SecretKey generateKey(Context context, String salt, String passphrase) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance(context.getString(R.string.secret_key_shared_preference));//pbkdf2withhmacsha256//PBEWithHmacSHA256AndAES_128
//            Logger.i("Hex salt " + hex(salt));
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), 64, 256);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw fail(e);
        }
    }

    private static String base64(byte[] bytes) {
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    private static byte[] base64(String str) {
        return Base64.decode(str, Base64.DEFAULT);
    }

    private static String random(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);
        return hex(salt);
    }

    private static String hex(byte[] bytes) {
        return Hex.encodeHex(bytes, false);
    }

    private static byte[] hex(String str) {
        try {
            return Hex.decodeHex(str);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }
}
