package sbnri.consumer.android;

import android.content.Context;

import com.google.android.libraries.places.api.Places;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.NoEncryption;

import javax.inject.Inject;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import sbnri.consumer.android.data.local.SBNRIPref;
import sbnri.consumer.android.modules.AppModule;

public class SBNRIApp extends MultiDexApplication {

    private SBNRIAppComponent component;

    @Inject
    SBNRIPref sbnriPref;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildAppComponent();
        initPlaces();
        HawkBuilder builder = Hawk.init(this);
        builder.setEncryption(new NoEncryption());
        builder.build();
    }

    private void initPlaces() {
        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.maps_api_key));
        }

    }

    private void buildAppComponent() {

        component = DaggerSBNRIAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        component.injectDependencies(this);
    }

    public SBNRIAppComponent getComponent() {
        return component;
    }
}
