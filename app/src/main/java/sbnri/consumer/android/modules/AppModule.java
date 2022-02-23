package sbnri.consumer.android.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import sbnri.consumer.android.SBNRIApp;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.scopes.ApplicationScope;

@Module
public class AppModule {

    private final SBNRIApp app;

    public AppModule(SBNRIApp app) {
        this.app = app;
    }

    @ApplicationContext
    @ApplicationScope
    @Provides
    public Context getContext() {
        return this.app;
    }

}
