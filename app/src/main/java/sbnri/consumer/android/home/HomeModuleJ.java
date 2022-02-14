package sbnri.consumer.android.home;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.data.local.SBNRILocalDataSource;
import sbnri.consumer.android.data.local.SBNRIPref;
import sbnri.consumer.android.data.source.SBNRIDataSource;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.qualifiers.HomeActivityPresenter;
import sbnri.consumer.android.qualifiers.HomeFragmentPresenter;
import sbnri.consumer.android.qualifiers.LocalDataSource;
import sbnri.consumer.android.qualifiers.SBNRIRepositoryQualifier;

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
