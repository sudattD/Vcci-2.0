package sbnri.consumer.android.modules;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.scopes.ApplicationScope;
import sbnri.consumer.android.webservice.consumer.PicassoHttpClient;


/**
 */

@Module
public class PicassoModule {

    @ApplicationScope
    @Provides
    OkHttp3Downloader okHttpDownloader() {
        return new OkHttp3Downloader(new OkHttpClient());
    }

    @ApplicationScope
    @Provides
    PicassoHttpClient picassoHttpClient(@ApplicationContext Context context) {
        return new PicassoHttpClient(context, Integer.MAX_VALUE);
    }

    @ApplicationScope
    @Provides
    public Picasso picasso(PicassoHttpClient picassoHttpClient, @ApplicationContext Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        return builder.downloader(picassoHttpClient).loggingEnabled(false).build();
    }

}