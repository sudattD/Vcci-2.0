package vcci.consumer.android.webservice.consumer;

import android.text.TextUtils;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import vcci.consumer.android.constants.RestClientConstants;
import vcci.consumer.android.qualifiers.BaseUrl;
import vcci.consumer.android.qualifiers.HTTPClientConsumer;
import vcci.consumer.android.scopes.ApplicationScope;

@ApplicationScope
public class RestClient {

    public static final String BASE_URL = RestClientConstants.VCCI_LOCAL;


    private final Retrofit retrofit;

    @Inject
    RestClient(@HTTPClientConsumer OkHttpClient client, GsonConverterFactory gsonConverterFactory, RxJava2CallAdapterFactory rxJavaCallAdapterFactory, @BaseUrl String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(TextUtils.isEmpty(baseUrl) ? BASE_URL : baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
    }

    public ApiService createRetrofitClient() {
        return retrofit.create(ApiService.class);
    }
}
