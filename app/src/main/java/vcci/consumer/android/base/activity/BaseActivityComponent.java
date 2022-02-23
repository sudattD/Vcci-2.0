package vcci.consumer.android.base.activity;


import android.app.Activity;
import android.content.Context;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.squareup.picasso.Picasso;

import dagger.Component;
import vcci.consumer.android.SBNRIAppComponent;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.data.local.SBNRILocalDataSource;
import vcci.consumer.android.data.local.SBNRIPref;
import vcci.consumer.android.data.source.SBNRIDataSource;
import vcci.consumer.android.qualifiers.ActivityContext;
import vcci.consumer.android.qualifiers.ApplicationContext;
import vcci.consumer.android.qualifiers.LocalDataSource;
import vcci.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import vcci.consumer.android.scopes.BaseActivityScope;

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
