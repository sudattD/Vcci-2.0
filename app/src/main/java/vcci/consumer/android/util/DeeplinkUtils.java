package vcci.consumer.android.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Set;

import vcci.consumer.android.R;
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.data.local.SBNRIPref;
import vcci.consumer.android.onboarding.OnBoardingActivity;
import vcci.consumer.android.splash.SplashActivity;

public class DeeplinkUtils {

    private static final String SOURCE_DEEPLINK = "deeplink";
    private static boolean isParsed = false;


    public static void parseAndHandleDynamicLinks(SBNRIPref sbnriPref, Activity activity) {
        if (activity == null)
            return;
        isParsed = true;

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(activity.getIntent())
                .addOnSuccessListener(pendingDynamicLinkData -> {
                    // Get deep link from result (may be null if no link is found)
                    Uri deepLink = null;
                    if (pendingDynamicLinkData != null) {
                        deepLink = pendingDynamicLinkData.getLink();
                    }
                    Logger.e("--- DEEPLINK RECEIVED ---");
                    if (deepLink != null) {
                        Logger.e("deeplink: " + deepLink.toString());
                        Set<String> params = deepLink.getQueryParameterNames();
                        Log.v("DeepLink :", deepLink.toString());


                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        Intent intent = activity.getIntent();
                        String emailLink = intent.getData().toString();

                        if(firebaseAuth.isSignInWithEmailLink(emailLink))
                        {
                            String email = Hawk.get("email","");
                            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(emailLink))
                            return;
                            firebaseAuth.signInWithEmailLink(email, emailLink)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                           // Log.d(TAG, "Successfully signed in with email link!");
                                            AuthResult result = task.getResult();
                                            // You can access the new user via result.getUser()
                                            // Additional user info profile *not* available via:
                                            // result.getAdditionalUserInfo().getProfile() == null
                                            // You can check if the user is new or existing:
                                            // result.getAdditionalUserInfo().isNewUser()

                                            FirebaseUser mUser = result.getUser();
                                            if (mUser != null) {
                                                mUser.getIdToken(true)
                                                        .addOnCompleteListener(t -> {
                                                            if (t.isSuccessful()) {
                                                                String idToken = t.getResult().getToken();
                                                                // Send token to your backend via HTTPS
                                                                Intent intent1 = new Intent(activity, OnBoardingActivity.class);
                                                                intent1.putExtra(Constants.ID_TOKEN,idToken);
                                                                intent.putExtra(Constants.IS_PUSHED,true);
                                                                gotoNextActivity(activity, intent1);
                                                               // onBoardingPresenter.getFireBasetokenVerified(idToken);
                                                                // ...
                                                            } else {
                                                                // Handle error -> task.getException();
                                                            }
                                                        });
                                            }






                                        } else {
                                            //Log.e(TAG, "Error signing in with email link", task.getException());
                                        }
                                    });
                        }

                    }
                })
                .addOnFailureListener(activity, e -> {
                    Logger.w("getDynamicLink:onFailure");
                   // SBNRIApp.logCrash(e);
                    isParsed = false;
                });
    }

    private static HashMap<String, String> getDeeplinkData(Uri finalDeepLink) {
        HashMap<String, String> data = new HashMap<>();
      /*  data.put(CleverTapConstants.KEY_CAMPAIGN, finalDeepLink.getQueryParameter(Constants.DEEPLINK_CAMPAIGN));
        data.put(CleverTapConstants.KEY_MEDIUM, finalDeepLink.getQueryParameter(Constants.DEEPLINK_MEDIUM));
        data.put(CleverTapConstants.KEY_SOURCE, finalDeepLink.getQueryParameter(Constants.DEEPLINK_SOURCE));
    */    return data;
    }

    private static void gotoNextActivity(Activity activity, Intent instance) {
        Logger.e("deeplink: next activity:" + instance.toString());
        activity.startActivity(instance);
        activity.overridePendingTransition(R.anim.enter_from_right, R.anim.stay);
        if (activity instanceof SplashActivity)
            activity.finish();
        isParsed = true;
    }

    public static boolean isParsed() {
        return isParsed;
    }
}
