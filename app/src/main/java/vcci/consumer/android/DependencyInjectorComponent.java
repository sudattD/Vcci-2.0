package vcci.consumer.android;


import android.app.Activity;
import android.content.Context;

import dagger.Component;
import vcci.consumer.android.EmailConfirmation.EmailConfirmationActivity;
import vcci.consumer.android.aboutus.AboutUsActivity;
import vcci.consumer.android.base.activity.BaseActivityComponent;
import vcci.consumer.android.base.activity.BaseViewModule;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.base.schedulers.SchedulerProvider;
import vcci.consumer.android.category.CategoryFragment;
import vcci.consumer.android.committees.CommitteesActivity;
import vcci.consumer.android.data.local.SBNRILocalDataSource;
import vcci.consumer.android.data.local.SBNRIPref;
import vcci.consumer.android.data.source.SBNRIDataSource;
import vcci.consumer.android.dignnitaries.DignitariesActivity;
import vcci.consumer.android.events.EventDetailsActivity;
import vcci.consumer.android.events.EventsActivity;
import vcci.consumer.android.gallery.GalleryActivity;
import vcci.consumer.android.membership.MembershipActivity;
import vcci.consumer.android.news.NewsFragment;
import vcci.consumer.android.onboarding.OnBoardingActivity;
import vcci.consumer.android.qualifiers.ActivityContext;
import vcci.consumer.android.qualifiers.ApplicationContext;
import vcci.consumer.android.qualifiers.LocalDataSource;
import vcci.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import vcci.consumer.android.scopes.ActivityScope;
import vcci.consumer.android.splash.SplashActivity;

@ActivityScope
@Component(modules = BaseViewModule.class, dependencies = BaseActivityComponent.class)
public interface DependencyInjectorComponent {

    BaseView baseView();

    BaseViewModule baseViewModule();

    @ApplicationContext
    Context getContext();

    @ActivityContext
    Context getActivityContext();

    Activity getActivity();



    @LocalDataSource
    SBNRILocalDataSource getLocalDataSource();

    @SBNRIRepositoryQualifier
    SBNRIDataSource getRemoteDataSource();

    SchedulerProvider getSchedulerProvider();

//    Picasso getPicasso();

    SBNRIPref getSBNRIPref();

    //Activities
    void injectDependencies(SplashActivity activity);
    void injectDependencies(OnBoardingActivity activity);

    void injectDependencies(EmailConfirmationActivity activity);

    void injectDependencies(AboutUsActivity activity);
    void injectDependencies(CommitteesActivity activity);
    void injectDependencies(DignitariesActivity activity);
    void injectDependencies(EventsActivity activity);
    void injectDependencies(EventDetailsActivity activity);
    void injectDependencies(MembershipActivity activity);
    void injectDependencies(GalleryActivity activity);
    void injectDependencies(CategoryFragment fragment);

}
