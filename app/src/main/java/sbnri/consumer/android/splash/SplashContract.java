package sbnri.consumer.android.splash;

import android.content.Context;

import java.util.HashMap;

import androidx.annotation.NonNull;
import sbnri.consumer.android.base.contract.BasePresenterImp;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.data.source.SBNRIDataSource;

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
