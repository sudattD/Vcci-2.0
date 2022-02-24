package vcci.consumer.android;


import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Component;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.data.local.SBNRILocalDataSource;
import vcci.consumer.android.data.local.SBNRIPref;
import vcci.consumer.android.data.source.SBNRIDataSource;
import vcci.consumer.android.modules.AppModule;
import vcci.consumer.android.modules.NetworkModule;
import vcci.consumer.android.modules.PicassoModule;
import vcci.consumer.android.qualifiers.ApplicationContext;
import vcci.consumer.android.qualifiers.LocalDataSource;
import vcci.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import vcci.consumer.android.scopes.ApplicationScope;

@ApplicationScope
@Component(modules = {AppModule.class, NetworkModule.class, PicassoModule.class})
public interface SBNRIAppComponent {

    @ApplicationContext
    Context getContext();

    @SBNRIRepositoryQualifier
    SBNRIDataSource getRemoteDataSource();

    @LocalDataSource
    SBNRILocalDataSource getLocalDataSource();

    SchedulerProvider getSchedulerProvider();

   Picasso getPicasso();

    SBNRIPref getSBNRIPref();

    void injectDependencies(SBNRIApp sbnriApp);

}
