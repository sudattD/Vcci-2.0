package vcci.consumer.android.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import vcci.consumer.android.DependencyInjectorComponent;
import vcci.consumer.android.R;
import vcci.consumer.android.base.activity.BaseActivity;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.home.HomeActivity;

public class OnBoardingActivity extends BaseActivity implements OnBoardingContract.OnBoardingView {

    public static int RC_SIGN_IN = 1000;
    public static String TAG = OnBoardingActivity.class.getSimpleName();
    @BindView(R.id.tab_indicator)
    TabLayout tabIndicator;

    @BindView(R.id.signUpGoogle)
    Button btnSignUpGoogle;

    @BindView(R.id.screen_viewpager)
    ViewPager screenViewPager;


    @BindView(R.id.btnEmail)
    Button btnEmail;

    @BindView(R.id.tvTermsAndCondition)
    TextView tvTermsAndCondition;

    @Inject
    OnBoardingPresenterImpl onBoardingPresenter;
    // [END declare_auth]
    private GoogleSignInClient mGoogleSignInClient;
    // [START declare_auth]
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);

        getIntentData();
        initView();


    }

    private void getIntentData() {

        Intent intent =  getIntent();
        if(intent != null)
        {
            String idToken = intent.getStringExtra(Constants.ID_TOKEN);
            if(!TextUtils.isEmpty(idToken))
            onBoardingPresenter.getFireBasetokenVerified(idToken);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {

        injectorComponent.injectDependencies(this);
    }


    @OnClick(R.id.signUpGoogle)
    public void googleSignUp() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        // Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // [START_EXCLUDE]
                    // [END_EXCLUDE]
                });
    }


    private void updateUI(FirebaseUser mUser) {

        if (mUser != null) {
            mUser.getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            // Send token to your backend via HTTPS

                            onBoardingPresenter.getFireBasetokenVerified(idToken);
                            // ...
                        } else {
                            // Handle error -> task.getException();
                        }
                    });
        }

    }




    @OnClick({R.id.btnEmail})
    public void onEmailClicked()
    {
       // BottomSheetUtil.Companion.showEmailBottomSheet(context,bottomSheet);
        startActivity(HomeActivity.createInstance(context));

    }


    @Override
    public void initView() {
        baseToolbar.setVisibility(View.GONE);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]


        final List<OnBoardingItem> mList = new ArrayList<>();
        mList.add(new OnBoardingItem(context.getString(R.string.onboarding_title_one), context.getString(R.string.onboarding_subtitle_one)));
        mList.add(new OnBoardingItem(context.getString(R.string.onboarding_title_two), context.getString(R.string.onboarding_subtitle_two)));
        mList.add(new OnBoardingItem(context.getString(R.string.onboarding_title_three), context.getString(R.string.onboarding_subtitle_three)));
        mList.add(new OnBoardingItem(context.getString(R.string.onboarding_title_four), context.getString(R.string.onboarding_subtitle_four)));


        OnBoardingViewPagerAdapter onBoardingViewPagerAdapter =
                new OnBoardingViewPagerAdapter(this, mList);

        screenViewPager.setAdapter(onBoardingViewPagerAdapter);
        tabIndicator.setupWithViewPager(screenViewPager);

        tvTermsAndCondition.setText(HtmlCompat.fromHtml(context.getString(R.string.terms_and_condition),HtmlCompat.FROM_HTML_MODE_LEGACY));




 /*       //to be removed later
        Intent intent = new Intent(this, AccountIntroSplashActivity.class);
        startActivity(intent);

*/

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
