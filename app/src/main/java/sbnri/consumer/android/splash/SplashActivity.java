package sbnri.consumer.android.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.installations.FirebaseInstallations;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import sbnri.consumer.android.DependencyInjectorComponent;
import sbnri.consumer.android.R;
import sbnri.consumer.android.base.activity.BaseActivity;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.constants.Constants;
import sbnri.consumer.android.home.HomeActivity;
import sbnri.consumer.android.onboarding.OnBoardingActivity;
import sbnri.consumer.android.util.DeeplinkUtils;

import static sbnri.consumer.android.constants.Constants.IS_PROFILE_COMPLETED;

public class SplashActivity extends BaseActivity implements SplashContract.View{

    private static final long SPLASH_DELAY = 1000;
    @Inject
    protected SplashPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (this.getIntent().getData() != null) {
            Logger.e("Deeplink: " + this.getIntent().getData().toString());
            DeeplinkUtils.parseAndHandleDynamicLinks(sbnriPref, this);
        }
    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {

        injectorComponent.injectDependencies(this);
    }

    @Override
    public void navigateToPlayStore(String response) {

        //TO-DO
    }

    @Override
    public void initView() {

        baseToolbar.setVisibility(View.GONE);

        new Handler().postDelayed(() -> {

                Intent intent = new Intent(SplashActivity.this,OnBoardingActivity.class);
                startActivity(intent);


            finish();
        },SPLASH_DELAY);
    }

    public void getFCMToken() {

        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        
                        Log.d("Installations", "Installation ID: " + task.getResult());
                    } else {
                        Log.e("Installations", "Unable to get Installation ID");
                    }
                });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToastMessage(String toastMessage, boolean isErrortoast) {

    }

    @Override
    public void accessTokenExpired() {

    }
}
