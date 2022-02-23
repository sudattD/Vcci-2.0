package sbnri.consumer.android.base.activity;


import android.app.Activity;
import android.content.Context;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.squareup.picasso.Picasso;

import dagger.Component;
import sbnri.consumer.android.SBNRIAppComponent;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.data.local.SBNRILocalDataSource;
import sbnri.consumer.android.data.local.SBNRIPref;
import sbnri.consumer.android.data.source.SBNRIDataSource;
import sbnri.consumer.android.qualifiers.ActivityContext;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.qualifiers.LocalDataSource;
import sbnri.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import sbnri.consumer.android.scopes.BaseActivityScope;

@BaseActivityScope
@Component(modules = BaseActivityModule.class, dependencies = SBNRIAppComponent.class)
public interface BaseActivityComponent {

    @ApplicationContext
    Context getContext();

    @ActivityContext
    Context getActivityContext();

    Activity getActivity();

    @SBNRIRepositoryQualifier
    SBNRIDataSource getRemoteDataSource();

    @LocalDataSource
    SBNRILocalDataSource getLocalDataSource();

    SchedulerProvider getSchedulerProvider();

   Picasso getPicasso();

    SBNRIPref getSBNRIPref();

    BottomSheetLayout bottomSheet();

}
