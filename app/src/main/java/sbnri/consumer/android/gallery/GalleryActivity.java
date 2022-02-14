package sbnri.consumer.android.gallery;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sbnri.consumer.android.DependencyInjectorComponent;
import sbnri.consumer.android.R;
import sbnri.consumer.android.adapters.OnCommonItemClickListener;
import sbnri.consumer.android.api.ApiClient;
import sbnri.consumer.android.api.ApiInterface;
import sbnri.consumer.android.base.activity.BaseActivity;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.constants.Constants;
import sbnri.consumer.android.data.models.gallery.photos.gallery_list.Gallery;
import sbnri.consumer.android.data.models.gallery.photos.gallery_list.GalleryItem;
import sbnri.consumer.android.data.models.gallery.photos.gallery_list.GalleryResponse;
import sbnri.consumer.android.data.models.gallery.videos.video_list.VideoGallery;
import sbnri.consumer.android.data.models.gallery.videos.video_list.VideoItem;
import sbnri.consumer.android.data.models.gallery.videos.video_list.VideoResponse;
import sbnri.consumer.android.gallery.adapter.PhotoGalleryListAdapter;
import sbnri.consumer.android.gallery.adapter.VideoGalleryListAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class GalleryActivity extends BaseActivity implements BaseView , OnCommonItemClickListener {

    @BindView(R.id.tv_menu_title)
    TextView tv_menu_title;

    @BindView(R.id.rv_gallery)
    RecyclerView rv_gallery;
    private RecyclerView.LayoutManager layoutManager;

    private PhotoGalleryListAdapter photoGalleryListAdapter;
    private VideoGalleryListAdapter videoGalleryListAdapter;

    private int id = 0;
    private String value = "";

    private OnCommonItemClickListener onCommonItemClickListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
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
        onCommonItemClickListener = this;
    }

    private void getGalleryData(String id) {
        if ("1".equals(id)) {
            showProgress();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            String token = sbnriPref.getString("fcm_token");
            Log.d(TAG, "getSliderMenu: " + token);

            Call<GalleryResponse> call = apiService.getGalleryList(Constants.version, Constants.DEVICE_TYPE, "get-gallery",
                    token, "123456", id);
            call.enqueue(new Callback<GalleryResponse>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                    hideProgress();
                    assert response.body() != null;
                    Gallery galleryItem = response.body().getGallery();
                    tv_menu_title.setText(value);
                    rv_gallery.setLayoutManager(layoutManager);
                    List<GalleryItem> galleryItemList = galleryItem.getData();
                    if (galleryItemList != null && galleryItemList.size() > 0) {
                        photoGalleryListAdapter = new PhotoGalleryListAdapter(context, galleryItemList, onCommonItemClickListener);
                        rv_gallery.setAdapter(photoGalleryListAdapter);
                    }
                }

                @Override
                public void onFailure(Call<GalleryResponse> call, Throwable t) {
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

            Call<VideoResponse> call = apiService.getGalleryVideoList(Constants.version, Constants.DEVICE_TYPE, "get-gallery",
                    token, "123456", "2");
            call.enqueue(new Callback<VideoResponse>() {
                @SuppressLint("SetJavaScriptEnabled")
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    hideProgress();
                    assert response.body() != null;
                    VideoGallery gallery_video = response.body().getVideoGallery();
                    tv_menu_title.setText(value);
                    rv_gallery.setLayoutManager(layoutManager);
                    List<VideoItem> galleryItemList = gallery_video.getData();
                    if (galleryItemList != null && galleryItemList.size() > 0) {
                        videoGalleryListAdapter = new VideoGalleryListAdapter(context, galleryItemList, onCommonItemClickListener);
                        rv_gallery.setAdapter(videoGalleryListAdapter);
                    }
                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }

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

    }
}
