package sbnri.consumer.android.webservice.consumer;

import android.text.TextUtils;

import com.orhanobut.hawk.Hawk;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sbnri.consumer.android.constants.Constants;
import sbnri.consumer.android.constants.RestClientConstants;
import sbnri.consumer.android.qualifiers.BaseUrl;
import sbnri.consumer.android.qualifiers.HTTPClientConsumer;
import sbnri.consumer.android.scopes.ApplicationScope;

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
