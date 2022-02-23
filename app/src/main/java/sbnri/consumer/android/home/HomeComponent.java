package sbnri.consumer.android.home;

import android.content.Context;

import dagger.Component;
import sbnri.consumer.android.base.activity.BaseActivityComponent;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.scopes.ActivityScope;


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
