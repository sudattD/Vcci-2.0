package vcci.consumer.android.home;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.data.local.SBNRILocalDataSource;
import vcci.consumer.android.data.local.SBNRIPref;
import vcci.consumer.android.data.source.SBNRIDataSource;
import vcci.consumer.android.qualifiers.ApplicationContext;
import vcci.consumer.android.qualifiers.LocalDataSource;
import vcci.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import vcci.consumer.android.scopes.ActivityScope;
import vcci.consumer.android.webservice.ApiCallTags;
import vcci.consumer.android.webservice.model.SBNRIResponse;

@ActivityScope
class HomePresenterImplJ extends HomeContract.HomePresenter{

    private HomeContract.HomeFragmentView fragmentView;
    private HomeContract.HomeActivityView activityView;

    SBNRILocalDataSource sbnriLocalDataSource;
    @ApiCallTags.ApiCallIdentifiers
    private List<String> apiCallTracker;
    private SBNRIPref sbnriPref;



    @Inject
    public HomePresenterImplJ(@SBNRIRepositoryQualifier SBNRIDataSource sbnriDataSource, @LocalDataSource SBNRILocalDataSource sbnriLocalDataSource,
                              HomeContract.HomeFragmentView fragmentView, @NonNull SchedulerProvider schedulerProvider, @ApplicationContext Context context, SBNRIPref sbnriPref) {
        super(sbnriDataSource,schedulerProvider,fragmentView,context);
        this.mSbnriDataSource = sbnriDataSource;
        this.fragmentView = fragmentView;
        this.mSchedulerProvider = schedulerProvider;
        this.sbnriLocalDataSource = sbnriLocalDataSource;
        this.sbnriPref = sbnriPref;
        apiCallTracker = new ArrayList<>();
    }

    public HomePresenterImplJ(@SBNRIRepositoryQualifier SBNRIDataSource sbnriDataSource, @LocalDataSource SBNRILocalDataSource sbnriLocalDataSource,
                              HomeContract.HomeActivityView homeActivityView, @NonNull SchedulerProvider schedulerProvider, @ApplicationContext Context context, SBNRIPref sbnriPref) {
       super(sbnriDataSource,schedulerProvider,homeActivityView,context);
        this.mSbnriDataSource = sbnriDataSource;
        this.activityView = homeActivityView;
        this.mSchedulerProvider = schedulerProvider;
        this.sbnriLocalDataSource = sbnriLocalDataSource;
        this.sbnriPref = sbnriPref;
        apiCallTracker = new ArrayList<>();
    }
    @Override
    void setHomeActivityInstance(HomeContract.HomeActivityView homeActivityView) {

    }

    @Override
    void getAllBanksData() {

        fragmentView.showProgress();

    }

    @Override
    public void onSuccess(String callTag, SBNRIResponse response, HashMap<String, Object> extras) {
            fragmentView.hideProgress();
            switch (callTag)
            {
                case ApiCallTags.GET_ALL_BANKS_DATA:
                    handleSuccessGetAllBanksData(response);
                    break;
            }

    }

    private void handleSuccessGetAllBanksData(SBNRIResponse data) {
        //((AllBanksData) data.getData()).banks


    }

    @Override
    public void onError(String callTag, Throwable e, HashMap<String, Object> extras) {
        fragmentView.hideProgress();
    }

    @Override
    public void onFailure(String callTag, SBNRIResponse response, HashMap<String, Object> extras) {
       fragmentView.hideProgress();
    }

    @Override
    public void onSessionExpired() {
        fragmentView.hideProgress();
    }
}
