package vcci.consumer.android.membership;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcci.consumer.android.DependencyInjectorComponent;
import vcci.consumer.android.R;
import vcci.consumer.android.adapters.OnCommonItemClickListener;
import vcci.consumer.android.api.ApiClient;
import vcci.consumer.android.api.ApiInterface;
import vcci.consumer.android.base.activity.BaseActivity;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.data.models.membership_form.Membership;
import vcci.consumer.android.data.models.membership_form.MembershipForm;
import vcci.consumer.android.data.models.membership_form.MembershipFormsResponse;
import vcci.consumer.android.data.models.membership_form.OtherDocs;
import vcci.consumer.android.membership.adapter.MembershipFormAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MembershipActivity extends BaseActivity implements BaseView , OnCommonItemClickListener {


    @BindView(R.id.rv_membership)
    RecyclerView rv_membership;

    @BindView(R.id.wv_description)
    WebView wv_description;

    @BindView(R.id.tv_other_associations)
    TextView tv_other_associations;

    private RecyclerView.LayoutManager layoutManager;
    private MembershipFormAdapter membershipFormAdapter;

    private OnCommonItemClickListener onCommonItemClickListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
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
        layoutManager = new LinearLayoutManager(context);
        rv_membership.setHasFixedSize(true);
        onCommonItemClickListener = this;
        getMembershipForms();
    }

    private void getMembershipForms() {

        showProgress();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String token = sbnriPref.getString("fcm_token");
        Log.d(TAG, "getSliderMenu: " + token);

        Call<MembershipFormsResponse> call = apiService.getMembershipForms(Constants.version, Constants.DEVICE_TYPE, "get-membership",
                token, "123456");
        call.enqueue(new Callback<MembershipFormsResponse>() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<MembershipFormsResponse> call, Response<MembershipFormsResponse> response) {
               hideProgress();
                assert response.body() != null;
                Membership membership = response.body().getMembership();
                rv_membership.setLayoutManager(layoutManager);
                List<MembershipForm> membershipFormList = membership.getData();
                if (membershipFormList != null && membershipFormList.size() > 0) {
                    membershipFormAdapter = new MembershipFormAdapter(context, membershipFormList, onCommonItemClickListener);
                    rv_membership.setAdapter(membershipFormAdapter);

                }
                OtherDocs otherDocs = response.body().getOthers();
                tv_other_associations.setText(otherDocs.getTitle());
                wv_description.getSettings().setJavaScriptEnabled(true);
                wv_description.getSettings().getAllowContentAccess();
                wv_description.getSettings().getDefaultTextEncodingName();
                wv_description.getSettings().setDefaultFontSize(18);
                String htmlContent = otherDocs.getData();
                String encodedHtml = Base64.encodeToString(htmlContent.getBytes(), Base64.NO_PADDING);
                wv_description.loadData(encodedHtml, "text/html", "base64");
            }

            @Override
            public void onFailure(Call<MembershipFormsResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
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

    @Override
    public void onItemClick(View view, Object object) {

        if(object instanceof MembershipForm)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((MembershipForm) object).getView()));
            startActivity(browserIntent);
        }

    }
}
