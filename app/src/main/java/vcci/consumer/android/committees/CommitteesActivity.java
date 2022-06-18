package vcci.consumer.android.committees;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
import vcci.consumer.android.api.ApiClient;
import vcci.consumer.android.api.ApiInterface;
import vcci.consumer.android.base.activity.BaseActivity;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.committees.adapter.CommitteeListAdapter;
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.data.models.committee.CommitteeDataItem;
import vcci.consumer.android.data.models.committee.CommitteeDataResponse;
import vcci.consumer.android.data.models.committee.Committees;

import static androidx.constraintlayout.widget.Constraints.TAG;

import com.orhanobut.logger.Logger;

public class CommitteesActivity extends BaseActivity implements BaseView {

    @BindView(R.id.rv_committee)
    RecyclerView rv_committee;
    private RecyclerView.LayoutManager layoutManager;

    private CommitteeListAdapter committeeListAdapter;

    private int id = 0;
    private String value = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);
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
        rv_committee.setHasFixedSize(true);

        getCommitteesData(String.valueOf(7));
    }

    private void getCommitteesData(String id) {

       showProgress();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String token = sbnriPref.getString("fcm_token");
        Log.d(TAG, "getSliderMenu: " + token);

        Call<CommitteeDataResponse> call = apiService.getCommitteeList(Constants.version, Constants.DEVICE_TYPE, "get-committees-page",
                token, "123456", id);
        call.enqueue(new Callback<CommitteeDataResponse>() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<CommitteeDataResponse> call, Response<CommitteeDataResponse> response) {
                hideProgress();
                assert response.body() != null;
                Logger.d(CommitteesActivity.class.getSimpleName(),response.body());
                Committees committees = response.body().getCommittees();
                //tv_menu_title.setText(value);
//                tv_menu_title.setText(committees.getTitle());
                rv_committee.setLayoutManager(layoutManager);
                List<CommitteeDataItem> committeeDataItems = committees.getData();
                committeeListAdapter = new CommitteeListAdapter(context, committeeDataItems);
                rv_committee.setAdapter(committeeListAdapter);
            }

            @Override
            public void onFailure(Call<CommitteeDataResponse> call, Throwable t) {
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
}
