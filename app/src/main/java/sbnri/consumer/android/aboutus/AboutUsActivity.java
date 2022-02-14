package sbnri.consumer.android.aboutus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sbnri.consumer.android.DependencyInjectorComponent;
import sbnri.consumer.android.R;
import sbnri.consumer.android.aboutus.adapters.PastPresidentAdapter;
import sbnri.consumer.android.aboutus.adapters.PersonnelDataAdapter;
import sbnri.consumer.android.aboutus.adapters.SecretriatDataAdapter;
import sbnri.consumer.android.api.ApiClient;
import sbnri.consumer.android.api.ApiInterface;
import sbnri.consumer.android.base.activity.BaseActivity;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.constants.Constants;
import sbnri.consumer.android.data.models.about_us.about_us_content.About;
import sbnri.consumer.android.data.models.about_us.about_us_content.AboutUsContentResponse;
import sbnri.consumer.android.data.models.about_us.about_us_personnel.AboutPersonnel;
import sbnri.consumer.android.data.models.about_us.about_us_personnel.DataItem;
import sbnri.consumer.android.data.models.about_us.about_us_personnel.PersonnelsDataResponse;
import sbnri.consumer.android.data.models.about_us.about_us_secretariat.Secretariat;
import sbnri.consumer.android.data.models.about_us.about_us_secretariat.SecretariatItem;
import sbnri.consumer.android.data.models.about_us.about_us_secretariat.SecretariatResponse;
import sbnri.consumer.android.webservice.consumer.ApiService;

public class AboutUsActivity extends BaseActivity implements BaseView {


    @BindView(R.id.rl_content)
    RelativeLayout rl_content;
    @BindView(R.id.rl_pdf)
    RelativeLayout rl_pdf;
    @BindView(R.id.rl_personnel_listing)
    RelativeLayout rl_personnel_listing;
    @BindView(R.id.rl_past_presidents_listing)
    RelativeLayout rl_past_presidents_listing;
    @BindView(R.id.rl_secreteriat)
    RelativeLayout rl_secreteriat;

    @BindView(R.id.tv_menu_title)
    TextView tv_menu_title;
    @BindView(R.id.wv_content)
    WebView wv_content;
    /*@BindView(R.id.tv_content)
    TextView tv_content;*/
    @BindView(R.id.wv_pdf)
    WebView wv_pdf;

    @BindView(R.id.rv_personnel)
    RecyclerView rv_personnel;
    private RecyclerView.LayoutManager layoutManager;
    private PersonnelDataAdapter personnelDataAdapter;
    @BindView(R.id.rv_past_president)
    RecyclerView rv_past_president;
    private PastPresidentAdapter pastPresidentAdapter;
    @BindView(R.id.rv_secreteriat)
    RecyclerView rv_secreteriat;
    private SecretriatDataAdapter secretriatDataAdapter;
    @BindView(R.id.wv_secreteriat_content)
    WebView wv_secreteriat_content;

    private int id = 0;
    private String value = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);

        getIntentData();
    }

    private void getIntentData() {
        Intent intent =  getIntent();
        if(intent != null)
        {
            String id = intent.getStringExtra(Constants.ID);
            getAboutUS(id);

        }
    }

    private void getAboutUS(String id) {
        switch (id) {
            case "1":
            case "2":
            case "3":
                rl_content.setVisibility(View.VISIBLE);
                rl_pdf.setVisibility(View.GONE);
                rl_personnel_listing.setVisibility(View.GONE);
                rl_past_presidents_listing.setVisibility(View.GONE);
                rl_secreteriat.setVisibility(View.GONE);
                getAboutUsContentData(id);
                break;

            case "4":
            case "5":
                rl_personnel_listing.setVisibility(View.VISIBLE);
                rl_content.setVisibility(View.GONE);
                rl_pdf.setVisibility(View.GONE);
                rl_past_presidents_listing.setVisibility(View.GONE);
                rl_secreteriat.setVisibility(View.GONE);
                getAboutUsPersonnelData(id);
                break;

            case "6":
                rl_personnel_listing.setVisibility(View.GONE);
                rl_content.setVisibility(View.GONE);
                rl_pdf.setVisibility(View.GONE);
                rl_secreteriat.setVisibility(View.GONE);
                rl_past_presidents_listing.setVisibility(View.VISIBLE);
                getAboutUsPersonnelData(id);
                break;

            case "7":
                rl_past_presidents_listing.setVisibility(View.GONE);
                rl_personnel_listing.setVisibility(View.GONE);
                rl_content.setVisibility(View.GONE);
                rl_secreteriat.setVisibility(View.VISIBLE);
                rl_pdf.setVisibility(View.GONE);
                getSecreteriatData(id);
                break;

        }

    }

    private void getSecreteriatData(String id) {
        showProgress();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String token = sbnriPref.getString("fcm_token");

        Call<SecretariatResponse> call = apiService.getSecretariatData(Constants.version, Constants.DEVICE_TYPE, "get-about-us",
                token, "123456", id);
        call.enqueue(new Callback<SecretariatResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<SecretariatResponse> call, Response<SecretariatResponse> response) {
               hideProgress();
                assert response.body() != null;
                Secretariat secretariat = response.body().getSecretariat();
                tv_menu_title.setText(value);
                wv_secreteriat_content.getSettings().setJavaScriptEnabled(true);
                wv_secreteriat_content.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                wv_secreteriat_content.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                wv_secreteriat_content.getSettings().setAppCacheEnabled(true);
                wv_secreteriat_content.getSettings().setBlockNetworkImage(true);
                wv_secreteriat_content.getSettings().setLoadsImagesAutomatically(true);
                wv_secreteriat_content.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                wv_secreteriat_content.getSettings().setGeolocationEnabled(false);
                wv_secreteriat_content.getSettings().setNeedInitialFocus(false);
                wv_secreteriat_content.getSettings().setSaveFormData(false);
                wv_secreteriat_content.getSettings().setFixedFontFamily("HindVadodara-Regular.ttf");
                wv_secreteriat_content.getSettings().setDefaultFontSize(16);
                wv_secreteriat_content.getSettings().setDomStorageEnabled(true);
                wv_secreteriat_content.loadData(secretariat.getData(), "text/html; charset=utf-8", "UTF-8");
//                wv_content.loadDataWithBaseURL("", about.getData(), "text/html", "UTF-8", "");
                /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tv_content.setText(Html.fromHtml(about.getData(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tv_content.setText(Html.fromHtml(about.getData()));
                }*/

                rv_secreteriat.setLayoutManager(layoutManager);
                List<SecretariatItem> members = secretariat.getMembers();
                secretriatDataAdapter = new SecretriatDataAdapter(context, members);
                rv_secreteriat.setAdapter(secretriatDataAdapter);
            }

            @Override
            public void onFailure(Call<SecretariatResponse> call, Throwable t) {
                // Log error here since request failed
               // Log.e(TAG, t.toString());
            }
        });
    }

    private void getAboutUsPersonnelData(String id) {

        showProgress();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String token = sbnriPref.getString("fcm_token");
        Call<PersonnelsDataResponse> call = apiService.getAbouUsPersonnelData(Constants.version, Constants.DEVICE_TYPE, "get-about-us",
                token, "123456", id);
        call.enqueue(new Callback<PersonnelsDataResponse>() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<PersonnelsDataResponse> call, Response<PersonnelsDataResponse> response) {
                hideProgress();
                assert response.body() != null;
                AboutPersonnel about = response.body().getAbout();
                tv_menu_title.setText(value);
                if ("6".equals(id)) {
                    rv_past_president.setLayoutManager(layoutManager);
                    List<DataItem> dataItemList = about.getData();
                    pastPresidentAdapter = new PastPresidentAdapter(context, dataItemList);
                    rv_past_president.setAdapter(pastPresidentAdapter);
                } else {
                    rv_personnel.setLayoutManager(layoutManager);
                    List<DataItem> dataItemList = about.getData();
                    personnelDataAdapter = new PersonnelDataAdapter(context, dataItemList);
                    rv_personnel.setAdapter(personnelDataAdapter);
                }
            }

            @Override
            public void onFailure(Call<PersonnelsDataResponse> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    private void getAboutUsContentData(String id) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String token = sbnriPref.getString("fcm_token");
        Call<AboutUsContentResponse> call = apiService.getAbouUsContent(Constants.version, Constants.DEVICE_TYPE, "get-about-us",
                token, "123456", id);
        call.enqueue(new Callback<AboutUsContentResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<AboutUsContentResponse> call, Response<AboutUsContentResponse> response) {
                hideProgress();
                assert response.body() != null;
                About about = response.body().getAbout();
                tv_menu_title.setText(value);
                wv_content.getSettings().setJavaScriptEnabled(true);
                wv_content.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
                wv_content.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                wv_content.getSettings().setAppCacheEnabled(true);
                wv_content.getSettings().setBlockNetworkImage(true);
                wv_content.getSettings().setLoadsImagesAutomatically(true);
                wv_content.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                wv_content.getSettings().setGeolocationEnabled(false);
                wv_content.getSettings().setNeedInitialFocus(false);
                wv_content.getSettings().setSaveFormData(false);
                wv_content.getSettings().setDefaultFontSize(16);
                wv_content.getSettings().setDomStorageEnabled(true);
                wv_content.loadData(about.getData(), "text/html; charset=utf-8", "UTF-8");
//                wv_content.loadDataWithBaseURL("", about.getData(), "text/html", "UTF-8", "");
                /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tv_content.setText(Html.fromHtml(about.getData(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tv_content.setText(Html.fromHtml(about.getData()));
                }*/
            }

            @Override
            public void onFailure(Call<AboutUsContentResponse> call, Throwable t) {
                // Log error here since request failed
                //Log.e(TAG, t.toString());
            }
        });
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
}
