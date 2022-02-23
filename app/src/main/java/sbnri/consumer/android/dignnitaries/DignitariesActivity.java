package sbnri.consumer.android.dignnitaries;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sbnri.consumer.android.DependencyInjectorComponent;
import sbnri.consumer.android.R;
import sbnri.consumer.android.api.ApiClient;
import sbnri.consumer.android.api.ApiInterface;
import sbnri.consumer.android.base.activity.BaseActivity;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.constants.Constants;
import sbnri.consumer.android.data.models.dignitary.dig_photo.DignitaryPhotoResponse;
import sbnri.consumer.android.data.models.dignitary.dig_video.DignitaryVideoResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DignitariesActivity extends BaseActivity implements BaseView {


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_title)
    TextView tv_title;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.web_view)
    WebView web_view;

    private int id = 0;
    private String value = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dignitary_msg);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected BaseView getBaseView() {
        return null;
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {

        injectorComponent.injectDependencies(this);
    }

    @Override
    public void initView() {
        initToolbar(getIntent().getStringExtra(Constants.SCREEN_TITLE));
        getDignitaryData("2");
    }



    @Override
    public void showProgress() {
            rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        rl_progress.setVisibility(View.GONE);
    }

    @Override
    public void showToastMessage(String toastMessage, boolean isErrortoast) {

    }

    @Override
    public void accessTokenExpired() {

    }

    private void getDignitaryData(String id) {
        if ("2".equals(id)) {
           showProgress();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            String token = sbnriPref.getString("fcm_token");
            Log.d(TAG, "getSliderMenu: " + token);

            Call<DignitaryPhotoResponse> call = apiService.getDignitaryPhotoData(Constants.version, Constants.DEVICE_TYPE,
                    "get-dignitary",
                    token, "123456", id);
            call.enqueue(new Callback<DignitaryPhotoResponse>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(Call<DignitaryPhotoResponse> call, Response<DignitaryPhotoResponse> response) {
                    hideProgress();
                    assert response.body() != null;
                    tv_title.setText(response.body().getDignitary().getTitle());
                    web_view.getSettings().setJavaScriptEnabled(true);
                    web_view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                    web_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                    web_view.getSettings().setAppCacheEnabled(true);
                    web_view.getSettings().setGeolocationEnabled(false);
                    web_view.getSettings().setNeedInitialFocus(false);
                    web_view.loadUrl(response.body().getDignitary().getPopup());
                }

                @Override
                public void onFailure(Call<DignitaryPhotoResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        } else {
            showProgress();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            String token = sbnriPref.getString("fcm_token");
            Log.d(TAG, "getSliderMenu: " + token);

            Call<DignitaryVideoResponse> call = apiService.getDignitaryVideoData(Constants.version, Constants.DEVICE_TYPE, "get-dignitary",
                    token, "123456", "1");
            call.enqueue(new Callback<DignitaryVideoResponse>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(Call<DignitaryVideoResponse> call, Response<DignitaryVideoResponse> response) {
                    hideProgress();
                    assert response.body() != null;
                    tv_title.setText(response.body().getDignitaryVideo().getTitle());
                    web_view.getSettings().setJavaScriptEnabled(true);
                    web_view.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                    web_view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                    web_view.getSettings().setAppCacheEnabled(true);
                    web_view.getSettings().setGeolocationEnabled(false);
                    web_view.getSettings().setNeedInitialFocus(false);
                    web_view.loadUrl("https://www.youtube.com/embed/" + response.body().getDignitaryVideo().getData().get(0).getYoutubeId());
                }

                @Override
                public void onFailure(Call<DignitaryVideoResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }

    }
}
