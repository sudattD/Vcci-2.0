package sbnri.consumer.android.webservice.consumer;

import android.content.Context;
import android.os.StatFs;

import com.squareup.picasso.Downloader;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.qualifiers.HTTPClientConsumer;
import sbnri.consumer.android.util.ActivityUtils;

public class PicassoHttpClient implements Downloader {

    private static final String PICASSO_CACHE = "picasso-cache";
    private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
    private Context context;

    private static File createDefaultCacheDir(Context context) {
        File cache = new File(context.getApplicationContext().getCacheDir(), PICASSO_CACHE);
        if (!cache.exists()) {
            //noinspection ResultOfMethodCallIgnored
            cache.mkdirs();
        }
        return cache;
    }

    private static long calculateDiskCacheSize(File dir) {
        long size = MIN_DISK_CACHE_SIZE;

        try {
            StatFs statFs = new StatFs(dir.getAbsolutePath());
            long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
            // Target 2% of the total space.
            size = available / 50;
        } catch (IllegalArgumentException ignored) {
        }

        // Bound inside min/max size for disk cache.
        return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
    }

    private static OkHttpClient defaultOkHttpClient(File cacheDir, long maxSize) {
        return new OkHttpClient.Builder()
                .cache(new okhttp3.Cache(cacheDir, maxSize))
                .build();
    }

    private final Call.Factory client;
    private final Cache cache;

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into your application
     * cache directory.
     */
    public PicassoHttpClient(Context context) {
        this(createDefaultCacheDir(context));
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into the specified
     * directory.
     *
     * @param cacheDir The directory in which the cache should be stored
     */
    private PicassoHttpClient(File cacheDir) {
        this(cacheDir, calculateDiskCacheSize(cacheDir));
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into your application
     * cache directory.
     *
     * @param maxSize The size limit for the cache.
     */
    public PicassoHttpClient(final Context context, final long maxSize) {
        this(createDefaultCacheDir(context), maxSize);
        this.context = context;
    }

    /**
     * Create new downloader that uses OkHttp. This will install an image cache into the specified
     * directory.
     *
     * @param cacheDir The directory in which the cache should be stored
     * @param maxSize  The size limit for the cache.
     */
    private PicassoHttpClient(File cacheDir, long maxSize) {
        this(defaultOkHttpClient(cacheDir, maxSize));
    }

    public PicassoHttpClient(@HTTPClientConsumer OkHttpClient client, @ApplicationContext Context context) {
        this.client = client;
        this.cache = client.cache();
        this.context = context;
    }

    private PicassoHttpClient(Call.Factory client) {
        this.client = client;
        this.cache = null;
    }

    @NonNull
    @Override
    public Response load(@NonNull Request request) throws IOException {
        CacheControl cacheControl;

        if (ActivityUtils.isNetworkOnline(context)) {
            CacheControl.Builder builder = new CacheControl.Builder();
            builder.noCache();
            cacheControl = builder.build();
        } else {
            cacheControl = CacheControl.FORCE_CACHE;
        }

        Request.Builder builder = request.newBuilder();
        if (cacheControl != null) {
            builder.cacheControl(cacheControl);
        }

        return client.newCall(builder.build()).execute();
    }

    @Override
    public void shutdown() {
        if (cache != null) {
            try {
                cache.close();
            } catch (IOException ignored) {
            }
        }
    }
}
