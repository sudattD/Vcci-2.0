package vcci.consumer.android.home;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.data.local.SBNRILocalDataSource;
import vcci.consumer.android.data.local.SBNRIPref;
import vcci.consumer.android.data.source.SBNRIDataSource;
import vcci.consumer.android.qualifiers.ApplicationContext;
import vcci.consumer.android.qualifiers.HomeActivityPresenter;
import vcci.consumer.android.qualifiers.HomeFragmentPresenter;
import vcci.consumer.android.qualifiers.LocalDataSource;
import vcci.consumer.android.qualifiers.SBNRIRepositoryQualifier;

@Module
class HomeModuleJ {

    private final BaseView baseView;

    public HomeModuleJ(BaseView baseView) {
        this.baseView = baseView;
    }

    @HomeActivityPresenter
    @Provides
    HomePresenterImplJ getHomeActivityPresenter(@SBNRIRepositoryQualifier SBNRIDataSource sbnriDataSource,
                                               @LocalDataSource SBNRILocalDataSource sbnriLocalDataSource,
                                               SchedulerProvider schedulerProvider, @ApplicationContext Context context,
                                                SBNRIPref sbnriPref) {
        return new HomePresenterImplJ(sbnriDataSource,sbnriLocalDataSource,(HomeContract.HomeActivityView) baseView,schedulerProvider,context,sbnriPref);
    }

    @HomeFragmentPresenter
    @Provides
    HomePresenterImplJ getHomeFragmentPresenter(@SBNRIRepositoryQualifier SBNRIDataSource sbnriDataSource,
                                                @LocalDataSource SBNRILocalDataSource sbnriLocalDataSource,
                                                SchedulerProvider schedulerProvider, @ApplicationContext Context context,
                                                SBNRIPref sbnriPref) {
        return new HomePresenterImplJ(sbnriDataSource, sbnriLocalDataSource, (HomeContract.HomeFragmentView) baseView, schedulerProvider, context, sbnriPref);
    }


}
