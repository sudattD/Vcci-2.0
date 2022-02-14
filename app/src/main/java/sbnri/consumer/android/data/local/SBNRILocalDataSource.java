package sbnri.consumer.android.data.local;

import android.content.Context;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.room.Room;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import okhttp3.RequestBody;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.data.models.GenerateUploadUrl;
import sbnri.consumer.android.data.models.login.LoginResponse;
import sbnri.consumer.android.data.source.SBNRIDataSource;
import sbnri.consumer.android.webservice.model.SBNRIResponse;

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
