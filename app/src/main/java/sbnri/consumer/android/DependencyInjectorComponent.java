package sbnri.consumer.android;


import android.app.Activity;
import android.content.Context;

import dagger.Component;
import sbnri.consumer.android.EmailConfirmation.EmailConfirmationActivity;
import sbnri.consumer.android.aboutus.AboutUsActivity;
import sbnri.consumer.android.base.activity.BaseActivityComponent;
import sbnri.consumer.android.base.activity.BaseViewModule;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.bottomsheetDialoguesFrags.UserEmailBottomSheetFragment;
import sbnri.consumer.android.committees.CommitteesActivity;
import sbnri.consumer.android.data.local.SBNRILocalDataSource;
import sbnri.consumer.android.data.local.SBNRIPref;
import sbnri.consumer.android.data.source.SBNRIDataSource;
import sbnri.consumer.android.dignnitaries.DignitariesActivity;
import sbnri.consumer.android.events.EventDetailsActivity;
import sbnri.consumer.android.events.EventsActivity;
import sbnri.consumer.android.gallery.GalleryActivity;
import sbnri.consumer.android.membership.MembershipActivity;
import sbnri.consumer.android.news.NewsFragment;
import sbnri.consumer.android.onboarding.OnBoardingActivity;
import sbnri.consumer.android.qualifiers.ActivityContext;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.qualifiers.LocalDataSource;
import sbnri.consumer.android.qualifiers.SBNRIRepositoryQualifier;
import sbnri.consumer.android.scopes.ActivityScope;
import sbnri.consumer.android.splash.SplashActivity;

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



    void injectDependencies(UserEmailBottomSheetFragment fragment);

    void injectDependencies(EmailConfirmationActivity activity);

    void injectDependencies(AboutUsActivity activity);
    void injectDependencies(CommitteesActivity activity);
    void injectDependencies(DignitariesActivity activity);
    void injectDependencies(EventsActivity activity);
    void injectDependencies(EventDetailsActivity activity);
    void injectDependencies(MembershipActivity activity);
    void injectDependencies(GalleryActivity activity);
    void injectDependencies(NewsFragment fragment);

}
