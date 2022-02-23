package vcci.consumer.android.events;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import vcci.consumer.android.data.models.events.event_list.EventItem;
import vcci.consumer.android.data.models.events.event_list.EventListResponse;
import vcci.consumer.android.data.models.events.event_list.Events;
import vcci.consumer.android.events.adapter.EventsListAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EventsActivity extends BaseActivity implements BaseView, OnCommonItemClickListener {

    @BindView(R.id.tv_menu_title)
    TextView tv_menu_title;

    @BindView(R.id.tv_no_events)
    TextView tv_no_events;

    @BindView(R.id.rv_events)
    RecyclerView rv_events;
    private RecyclerView.LayoutManager layoutManager;

    private EventsListAdapter eventsListAdapter;

    private OnCommonItemClickListener onCommonItemClickListener;
    private int id = 0;
    private String value = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
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
        onCommonItemClickListener = this;
        layoutManager = new LinearLayoutManager(context);
        rv_events.setHasFixedSize(true);
        getEventList(String.valueOf(2));
    }

    private void getEventList(String id) {

        showProgress();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        String token = sbnriPref.getString("fcm_token");
        Log.d(TAG, "getSliderMenu: " + token);

        Call<EventListResponse> call = apiService.getEventsList(Constants.version, Constants.DEVICE_TYPE, "get-events",
                token, "123456", id);
        call.enqueue(new Callback<EventListResponse>() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<EventListResponse> call, Response<EventListResponse> response) {
               hideProgress();
                assert response.body() != null;
                Events events = response.body().getEvents();
                tv_menu_title.setText(value);
                rv_events.setLayoutManager(layoutManager);
                List<EventItem> eventItems = events.getData();
                if (eventItems != null && eventItems.size() > 0) {
                    tv_no_events.setVisibility(View.GONE);
                    rv_events.setVisibility(View.VISIBLE);
                    eventsListAdapter = new EventsListAdapter(context, eventItems,onCommonItemClickListener );
                    rv_events.setAdapter(eventsListAdapter);
                } else {
                    tv_no_events.setVisibility(View.VISIBLE);
                    rv_events.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<EventListResponse> call, Throwable t) {
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
        if(object instanceof EventItem)
        {
            /*Intent toDetails = new Intent(context, EventDetailsActivity.class);
            toDetails.putExtra("id", "2");
            startActivity(toDetails);*/
        }

    }
}
