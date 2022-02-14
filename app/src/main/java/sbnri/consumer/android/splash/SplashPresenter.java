package sbnri.consumer.android.splash;

import android.content.Context;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.data.local.SBNRIPref;
import sbnri.consumer.android.data.source.SBNRIDataSource;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import sbnri.consumer.android.scopes.ActivityScope;
import sbnri.consumer.android.util.NetworkUtils;
import sbnri.consumer.android.webservice.ApiCallTags;
import sbnri.consumer.android.webservice.model.SBNRIResponse;

@ActivityScope
public class SplashPresenter extends SplashContract.Presenter{


    private final SplashContract.View mSplashView;
    private final Context context;
    private final SBNRIPref sbnriPref;


    @Inject
    public SplashPresenter(@SBNRIRepositoryQualifier @NonNull SBNRIDataSource sbnriDataSource, @NonNull SchedulerProvider schedulerProvider, BaseView baseView, @ApplicationContext Context context, SBNRIPref sbnriPref) {
        super(sbnriDataSource, schedulerProvider, baseView, context);
        this.mSplashView = (SplashContract.View) baseView;
        this.context = context;
        this.sbnriPref = sbnriPref;

    }


    //TESTING PURPOSE
    @Override
    void getAllNews() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("page",1);
        params.put("last_hash_id",0);


       // NetworkUtils.makeNetworkCall(ApiCallTags.GET_ALL_NEWS, mSbnriDataSource.getAllNews(params), mSchedulerProvider, this);


    }


    @Override
    public void onSuccess(String callTag, SBNRIResponse response, HashMap<String, Object> extras) {

        switch (callTag)
        {
            case ApiCallTags.GET_ALL_NEWS:
                mSplashView.navigateToPlayStore(new Gson().toJson(response));
                break;
        }
    }

    @Override
    public void onError(String callTag, Throwable e, HashMap<String, Object> extras) {
        super.onError(callTag, e, extras);
        switch (callTag)
        {

            case ApiCallTags.GET_ALL_NEWS:
                mSplashView.navigateToPlayStore(callTag +"onError");

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + callTag);
        }
    }

    @Override
    public void onFailure(String callTag, SBNRIResponse response, HashMap<String, Object> extras) {
        super.onFailure(callTag, response, extras);
        switch (callTag)
        {
            case ApiCallTags.GET_ALL_NEWS:
                mSplashView.navigateToPlayStore(callTag +"onFailure");
                break;
        }
    }

    @Override
    public void onSessionExpired() {
        super.onSessionExpired();
        mSplashView.navigateToPlayStore( "onSessionExpired");

    }
}
