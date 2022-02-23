package vcci.consumer.android.webservice.consumer;

        import android.content.Context;
        import android.text.TextUtils;
        import android.util.Log;

        import com.google.gson.Gson;
        import com.orhanobut.hawk.Hawk;

        import java.io.IOException;

        import javax.inject.Inject;

        import androidx.annotation.NonNull;
        import okhttp3.Interceptor;
        import okhttp3.Request;
        import okhttp3.Response;
        import vcci.consumer.android.data.local.SBNRIPref;
        import vcci.consumer.android.onboarding.OnBoardingPresenterImpl;
        import vcci.consumer.android.qualifiers.ApplicationContext;

public class HeaderInterceptor implements Interceptor {

    private final Context context;
    private final SBNRIPref sbnriPref;

    @Inject
    public HeaderInterceptor(@ApplicationContext Context context, SBNRIPref sbnriPref) {
        this.context = context;
        this.sbnriPref = sbnriPref;
    }


    @Override
    public Response intercept(@NonNull Chain chain) {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();


        // put request on s3 fails if two authorization used
        if (!request.method().equalsIgnoreCase("put")) {
            String accessToken = "Bearer "+ Hawk.get(OnBoardingPresenterImpl.Companion.getTOKEN(),"");
            if (!TextUtils.isEmpty(Hawk.get(OnBoardingPresenterImpl.Companion.getTOKEN(),"")))
            {
                builder.addHeader("Authorization", accessToken);
            }

            if(!TextUtils.isEmpty(Hawk.get(OnBoardingPresenterImpl.Companion.getUSERNAME(),"")))
            {
                builder.addHeader("X-USERNAME",Hawk.get(OnBoardingPresenterImpl.Companion.getUSERNAME(),""));

            }

                builder.addHeader("X-DEVICE","2"); //2 for android //1 for ios

        }
        else
        {

            builder.addHeader("Content-Type", "image/jpeg");
        }
        //Bearer fe9e06313a5d46dcbd32c991123d42d141cc9d5c
        //Bearer 95ea95098e540370853ccd5bc1b944681ac6ba55

        request = builder.build();

        Log.d("###HEADERS###",new Gson().toJson(request.headers()));
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
           // Logger.d(e.getMessage());
        } catch (Exception e) {
           // Logger.d(e.getMessage());
        }
        return response;
    }


}
