package vcci.consumer.android.events;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

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
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.data.models.events.event_detail.EventDetailResponse;
import vcci.consumer.android.data.models.events.event_detail.EventDetails;
import vcci.consumer.android.events.adapter.PhotosAdapter;


import static androidx.constraintlayout.widget.Constraints.TAG;

public class EventDetailsActivity extends BaseActivity implements BaseView {


    @BindView(R.id.tv_event_title)
    TextView tv_event_title;

    @BindView(R.id.iv_event)
    ImageView iv_event;

    @BindView(R.id.tv_start_date)
    TextView tv_start_date;

    @BindView(R.id.tv_end_date)
    TextView tv_end_date;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_venue)
    TextView tv_venue;

    @BindView(R.id.tv_in_association)
    TextView tv_in_association;

    /*@BindView(R.id.tv_description)
    TextView tv_description;*/

    @BindView(R.id.wv_description)
    WebView wv_description;

    private boolean from_notification = false;

    @BindView(R.id.rv_photos)
    RecyclerView rv_photos;
    private RecyclerView.LayoutManager layoutManager;
    private PhotosAdapter photosAdapter;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        String getID = getIntent().getStringExtra("id");
        Log.d("getID", "onCreate: " + getID);

        from_notification = getIntent().getBooleanExtra("from_notification", false);

        layoutManager = new LinearLayoutManager(this);
        rv_photos.setLayoutManager(layoutManager);
        rv_photos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        getEventDetail(getID);
    }

    private void getEventDetail(String getID) {
        showProgress();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String token = sbnriPref.getString("fcm_token");
        Log.d(TAG, "getSliderMenu: " + token);

        Call<EventDetailResponse> call = apiService.getEventsDetail(Constants.version, Constants.DEVICE_TYPE, "get-event-details",
                token, "123456", getID);
        call.enqueue(new Callback<EventDetailResponse>() {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void onResponse(Call<EventDetailResponse> call, Response<EventDetailResponse> response) {
                hideProgress();
                assert response.body() != null;
                EventDetails eventDetails = response.body().getEventDetails();
                tv_event_title.setText(eventDetails!=null ? eventDetails.getTitle() : "");
                Picasso.get().load(eventDetails.getThumb()).placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_placeholder).into(iv_event);

                photosAdapter = new PhotosAdapter(EventDetailsActivity.this, eventDetails.getPhotos());
                rv_photos.setAdapter(photosAdapter);
                tv_start_date.setText(eventDetails.getStartDate());
                tv_end_date.setText(eventDetails.getEndDate());
                tv_time.setText(eventDetails.getEventTime());
                tv_venue.setText(eventDetails.getVenue());
                tv_in_association.setText(eventDetails.getAssoWith());
                //tv_description.setText(eventDetails.getDescription());

                wv_description.getSettings().setJavaScriptEnabled(true);
                wv_description.getSettings().setDefaultFontSize(16);
                wv_description.loadData(eventDetails.getDescription(), "text/html; charset=utf-8", "UTF-8");
                /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    tv_description.setText(Html.fromHtml(galleryDetails.getDescription(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tv_description.setText(Html.fromHtml(galleryDetails.getDescription()));
                }*/
            }

            @Override
            public void onFailure(Call<EventDetailResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity

            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
