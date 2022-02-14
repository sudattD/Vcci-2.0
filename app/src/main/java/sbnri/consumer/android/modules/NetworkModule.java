package sbnri.consumer.android.modules;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sbnri.consumer.android.BuildConfig;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.constants.Constants;
import sbnri.consumer.android.data.local.SBNRILocalDataSource;
import sbnri.consumer.android.data.local.SBNRIPref;
import sbnri.consumer.android.data.local.SBNRIRepository;
import sbnri.consumer.android.data.source.SBNRIDataSource;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.qualifiers.BaseUrl;
import sbnri.consumer.android.qualifiers.HTTPClientConsumer;
import sbnri.consumer.android.qualifiers.LocalDataSource;
import sbnri.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import sbnri.consumer.android.scopes.ApplicationScope;
import sbnri.consumer.android.webservice.consumer.ApiService;
import sbnri.consumer.android.webservice.consumer.CurlLoggingInterceptor;
import sbnri.consumer.android.webservice.consumer.HeaderInterceptor;
import sbnri.consumer.android.webservice.consumer.RestClient;

@Module
public class NetworkModule {

    @HTTPClientConsumer
    @ApplicationScope
    @Provides
    OkHttpClient okHttpClient(@ApplicationContext Context context, HeaderInterceptor headerInterceptor, HttpLoggingInterceptor loggingInterceptor) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIMEOUT, TimeUnit.SECONDS);

        return clientBuilder.addInterceptor(loggingInterceptor).addInterceptor(headerInterceptor).addInterceptor(new CurlLoggingInterceptor()).build();
    }

    @ApplicationScope
    @Provides
    HttpLoggingInterceptor httpLoggingInterceptor(HttpLoggingInterceptor.Level level) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(level);
        return interceptor;
    }

    // TODO : GO LIVE STEP : SET LOG LEVEL TO NONE
    @ApplicationScope
    @Provides
    HttpLoggingInterceptor.Level logLevel() {
        return BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;
    }

    @ApplicationScope
    @Provides
    GsonConverterFactory gsonConverterFactory() {
        GsonBuilder gsonBuilder = new GsonBuilder();
       // JsonSerializerFactory.addSensorValueSerialization(gsonBuilder);
        //JsonSerializerFactory.addSensorValueDeserialization(gsonBuilder);
        return GsonConverterFactory.create(gsonBuilder.create());
    }

    @ApplicationScope
    @Provides
    RxJava2CallAdapterFactory rxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @ApplicationScope
    @Provides
    ApiService getApiService(RestClient restClient) {
        return restClient.createRetrofitClient();
    }


    @ApplicationScope
    @Provides
    public Gson gson() {
        return new Gson();
    }

    @BaseUrl
    @ApplicationScope
    @Provides
    public String getBaseUrl(SBNRIPref sbnriPref) {
        return sbnriPref.getBaseUrl();
    }

    @LocalDataSource
    @ApplicationScope
    @Provides
    public SBNRILocalDataSource getLocalDataSource(@ApplicationContext Context context, SchedulerProvider schedulerProvider) {
        return new SBNRILocalDataSource(context, schedulerProvider);
    }

    @SBNRIRepositoryQualifier
    @ApplicationScope
    @Provides
    public SBNRIDataSource getSBNRIRepository(ApiService apiService, @LocalDataSource SBNRILocalDataSource localDataSource) {
        return new SBNRIRepository(apiService, localDataSource);
    }

}
