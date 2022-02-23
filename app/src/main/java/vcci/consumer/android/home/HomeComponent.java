package vcci.consumer.android.home;

import android.content.Context;

import dagger.Component;
import vcci.consumer.android.base.activity.BaseActivityComponent;
import vcci.consumer.android.qualifiers.ApplicationContext;
import vcci.consumer.android.scopes.ActivityScope;


/**
 */
@ActivityScope
@Component(modules = HomeModuleJ.class, dependencies = BaseActivityComponent.class)
public interface HomeComponent {

    @ApplicationContext
    Context getContext();

    void injectDependencies(HomeActivity activity);
    void injectDependencies(HomeFragmentJ fragmentJ);


}
