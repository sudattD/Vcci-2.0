package vcci.consumer.android.data.local;

import android.content.Context;

import java.util.HashMap;

import androidx.annotation.NonNull;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.data.models.GenerateUploadUrl;
import vcci.consumer.android.data.models.login.LoginResponse;
import vcci.consumer.android.data.source.SBNRIDataSource;
import vcci.consumer.android.webservice.model.SBNRIResponse;

public class SBNRILocalDataSource implements SBNRIDataSource {


    private final SchedulerProvider schedulerProvider;
    public SBNRILocalDataSource(@NonNull Context context,
                                  @NonNull SchedulerProvider schedulerProvider) {


        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public Flowable<SBNRIResponse> getAllNews(HashMap<String, Object> params) {
        return null;
    }




    @Override
    public Flowable<SBNRIResponse> generateLinkForEmail(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Flowable<SBNRIResponse<GenerateUploadUrl>> getFilePath(HashMap<String, Object> params) {
        return null;
    }

    @Override
    public Completable uploadFileOnAmazon(long size,String url, RequestBody image) {
        return null;
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
