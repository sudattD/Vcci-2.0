package vcci.consumer.android.home;

import android.content.Context;

import androidx.annotation.NonNull;
import vcci.consumer.android.base.contract.BasePresenterImp;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.data.source.SBNRIDataSource;


/**
 */

public interface HomeContract {

    interface HomeFragmentView extends BaseView {

    }


    interface HomeActivityView extends BaseView {
        void showNotificationStatus(int unreadCount);
    }

    abstract class HomePresenter extends BasePresenterImp {

        HomePresenter(@NonNull SBNRIDataSource sbnriDataSource, @NonNull SchedulerProvider schedulerProvider, BaseView baseView, Context context) {
            super(sbnriDataSource, schedulerProvider, baseView, context);
        }

        abstract void setHomeActivityInstance(HomeActivityView homeActivityView);

        abstract void getAllBanksData();
    }


}
