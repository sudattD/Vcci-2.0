package sbnri.consumer.android;


import android.content.Context;

import com.squareup.picasso.Picasso;

import dagger.Component;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.data.local.SBNRILocalDataSource;
import sbnri.consumer.android.data.local.SBNRIPref;
import sbnri.consumer.android.data.local.SBNRIRepository;
import sbnri.consumer.android.data.source.SBNRIDataSource;
import sbnri.consumer.android.modules.AppModule;
import sbnri.consumer.android.modules.NetworkModule;
import sbnri.consumer.android.modules.PicassoModule;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.qualifiers.LocalDataSource;
import sbnri.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import sbnri.consumer.android.scopes.ApplicationScope;

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
