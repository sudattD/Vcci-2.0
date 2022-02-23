package vcci.consumer.android.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import vcci.consumer.android.SBNRIApp;
import vcci.consumer.android.qualifiers.ApplicationContext;
import vcci.consumer.android.scopes.ApplicationScope;

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
