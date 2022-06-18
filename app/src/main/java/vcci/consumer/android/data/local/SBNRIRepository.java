package vcci.consumer.android.data.local;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import vcci.consumer.android.data.models.login.LoginResponse;
import vcci.consumer.android.data.source.SBNRIDataSource;
import vcci.consumer.android.webservice.consumer.ApiService;
import vcci.consumer.android.webservice.model.SBNRIResponse;

public class SBNRIRepository implements SBNRIDataSource {

    private final ApiService service;
    private final SBNRIDataSource mLocalDataSource;


    public SBNRIRepository(ApiService apiService, SBNRIDataSource localDataSource) {
        service = apiService;
        this.mLocalDataSource = localDataSource;
    }

    @Override
    public Flowable<SBNRIResponse> getAllNews(HashMap<String, Object> params) {
        return service.getAllNews(params);
    }


    @Override
    public Flowable<SBNRIResponse> generateLinkForEmail(HashMap<String, Object> params) {
        return service.generateLinkForEmail(params);
    }

    @Override
    public Completable uploadFileOnAmazon(long size,String url, RequestBody image) {
        return service.uploadFileOnAmazon(size,url, image);
    }

    @Override
    public Flowable<SBNRIResponse<LoginResponse>> getMemberLogin(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Flowable<SBNRIResponse> getHomeData(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Flowable<SBNRIResponse> getCategories(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Flowable<SBNRIResponse> getHomeBanners(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Flowable<SBNRIResponse> getAboutUS(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Flowable<SBNRIResponse> getCommittes(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Flowable<SBNRIResponse> getCommittesPage(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Flowable<SBNRIResponse> getDignitary(HashMap<String, Object> params) {
        return null;
    }
}
