package vcci.consumer.android.webservice.consumer;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import vcci.consumer.android.data.models.login.LoginResponse;
import vcci.consumer.android.webservice.model.SBNRIResponse;

public interface ApiService {


    @GET("get_all_news")
    Flowable<SBNRIResponse> getAllNews(@QueryMap HashMap<String, Object> params);


    @POST("generate_link_for_email")
    Flowable<SBNRIResponse> generateLinkForEmail(@Body HashMap<String, Object> params);


    @PUT
    Completable uploadFileOnAmazon(@Header("Content-Length") long length, @Url String url, @Body RequestBody image);


    //VCCI APIs
    @POST("member-login.php")
    Flowable<SBNRIResponse<LoginResponse>> getMemberLogin(@Body HashMap<String, Object> params);

    //https://www.niagidc.com/web-service/get-home-data.php
    @POST("get-home-data.php")
    Flowable<SBNRIResponse> getHomeData(@Body HashMap<String, Object> params);

    //https://niagidc.com/web-service/get-categories.php
    @POST("get-categories.php")
    Flowable<SBNRIResponse> getCategories(@Body HashMap<String, Object> params);

    //https://www.niagidc.com/web-service/get-home-banners.php
    @POST
    Flowable<SBNRIResponse> getHomeBanners(@Body HashMap<String, Object> params);

    //https://www.niagidc.com/web-service/get-about-us.php
    @POST
    Flowable<SBNRIResponse> getAboutUS(@Body HashMap<String, Object> params);

    //https://www.niagidc.com/web-service/get-committees.php
    @GET("get-committees.php")
    Flowable<SBNRIResponse> getCommittes(@Body HashMap<String, Object> params);

    //https://www.niagidc.com/web-service/get-committees-page.php
    @POST("get-committees-page.php")
    Flowable<SBNRIResponse> getCommittesPage(@Body HashMap<String, Object> params);

    //https://www.niagidc.com/web-service/get-dignitary.php
    @POST("get-dignitary.php")
    Flowable<SBNRIResponse> getDignitary(@Body HashMap<String, Object> params);





}
