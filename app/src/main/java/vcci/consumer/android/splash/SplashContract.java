package vcci.consumer.android.splash;

import android.content.Context;

import androidx.annotation.NonNull;
import vcci.consumer.android.base.contract.BasePresenterImp;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.data.source.SBNRIDataSource;

public interface SplashContract {


    interface View extends BaseView {

        void navigateToPlayStore(String resp);
    }

    abstract class Presenter extends BasePresenterImp
    {

        public Presenter(@NonNull SBNRIDataSource sbnriDataSource, @NonNull SchedulerProvider schedulerProvider, BaseView baseView, Context context) {
            super(sbnriDataSource, schedulerProvider, baseView, context);
        }


        abstract void getAllNews();
    }
}
