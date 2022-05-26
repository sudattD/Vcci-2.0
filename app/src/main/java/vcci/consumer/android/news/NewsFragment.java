package vcci.consumer.android.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
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
import vcci.consumer.android.base.activity.BaseFragment;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.data.models.dashboard_news.BannersItem;
import vcci.consumer.android.data.models.dashboard_news.DashboardResponse;
import vcci.consumer.android.data.models.dashboard_news.FeaturedNewsItem;
import vcci.consumer.android.data.models.dashboard_news.LatestNewsItem;
import vcci.consumer.android.data.models.dashboard_news.LeftAdItem;
import vcci.consumer.android.data.models.dashboard_news.RightAdItem;
import vcci.consumer.android.data.models.newsDetail.BottomAdsItem;
import vcci.consumer.android.news.adapter.DashboardBannersSliderAdapter;
import vcci.consumer.android.news.adapter.LatestNewsAdapter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class NewsFragment extends BaseFragment implements BaseView , OnCommonItemClickListener {


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.home_viewPager)
    ViewPager viewPager1;

    @BindView(R.id.indicator)
    TabLayout indicator;

    @BindView(R.id.rv_news)
    RecyclerView rv_news;

    private LatestNewsAdapter latestNewsAdapter;
    private OnCommonItemClickListener listener;

    private List<BannersItem> bannersItemList;
    private List<FeaturedNewsItem> featuredNewsItems;
    private List<RightAdItem> rightAdItems;
    private List<LeftAdItem> leftAdItems;
    private List<BottomAdsItem> bottomAdsItems;


    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
        injectorComponent.injectDependencies(this);
    }

    @Override
    public View onCreateKnifeView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        listener = this;
        initView();
    }

    @Override
    public void initView() {
        getNewsDataList();
    }

    @Override
    public void showProgress() {
        ((BaseActivity) activity).rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ((BaseActivity) activity).rl_progress.setVisibility(View.GONE);

    }

    @Override
    public void showToastMessage(String toastMessage, boolean isErrortoast) {

    }

    private void getNewsDataList() {
        showProgress();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        String token = sbnriPref.getString("fcm_token");
        Log.d(TAG, "getSliderMenu: " + token);

        Call<DashboardResponse> call = apiService.getDashboardData(Constants.version, Constants.DEVICE_TYPE, "get-home-data",
                token, "123456", "1", "100");
        call.enqueue(new Callback<DashboardResponse>() {
            @Override
            public void onResponse(Call<DashboardResponse> call, Response<DashboardResponse> response) {
                hideProgress();
                assert response.body() != null;

                bannersItemList = response.body().getBanners();
                viewPager1.setAdapter(new DashboardBannersSliderAdapter(getContext(), bannersItemList));
                indicator.setupWithViewPager(viewPager1, true);
                Timer timer1 = new Timer();
                timer1.scheduleAtFixedRate(new SliderTimer1(), 6000, 6000);

          /*      featuredNewsItems = response.body().getFeaturedNews();
                viewPager2.setAdapter(new FeaturedNewsSliderAdapter(getContext(), featuredNewsItems, HomeFragment.this));
                indicator2.setupWithViewPager(viewPager2, true);
                Timer timer2 = new Timer();
                timer2.scheduleAtFixedRate(new SliderTimer2(), 6000, 6000);
*/
                List<LatestNewsItem> newsItemList = response.body().getLatestNews();
                latestNewsAdapter = new LatestNewsAdapter(getContext(), newsItemList, listener);
                rv_news.setAdapter(latestNewsAdapter);

/*                List<BulletinsItem> bulletinsItemList = response.body().getBulletins();
                dashboardBulletinsAdapter = new DashboardBulletinsAdapter(getContext(), bulletinsItemList, HomeFragment.this);
                rv_bulletins.setAdapter(dashboardBulletinsAdapter);

                List<TrendingNewsItem> trendingNewsItems = response.body().getTrendingNews();
                gcciTrendingAdapter = new GCCITrendingAdapter(getContext(), trendingNewsItems, HomeFragment.this);
                rv_gcci_trending.setAdapter(gcciTrendingAdapter);

                leftAdItems = response.body().getLeftAd();
                avt_left.setAdapter(new LeftAdsSliderAdapter(getContext(), leftAdItems));
                indicator_avt_left.setupWithViewPager(avt_left, true);
                Timer timer4 = new Timer();
                timer4.scheduleAtFixedRate(new SliderTimer4(), 6000, 6000);

                rightAdItems = response.body().getRightAd();
                avt_right.setAdapter(new RightAdsSliderAdapter(getContext(), rightAdItems));
                indicator_avt_right.setupWithViewPager(avt_right, true);
                Timer timer3 = new Timer();
                timer3.scheduleAtFixedRate(new SliderTimer3(), 6000, 6000);

                bottomAdsItems = response.body().getBottomAds();
                avt_bottom.setAdapter(new BottomAdsSliderAdapter(getContext(), bottomAdsItems));
                indicator_avt_bottom.setupWithViewPager(avt_bottom, true);
                Timer timer5 = new Timer();
                timer5.scheduleAtFixedRate(new SliderTimer5(), 6000, 6000);*/
            }

            @Override
            public void onFailure(Call<DashboardResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                hideProgress();
                Toast.makeText(getActivity(), "No news found", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onItemClick(View view, Object object) {

    }

    private class SliderTimer1 extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                if (viewPager1.getCurrentItem() < bannersItemList.size() - 1) {
                    viewPager1.setCurrentItem(viewPager1.getCurrentItem() + 1);
                } else {
                    viewPager1.setCurrentItem(0);
                }
            });
        }
    }
}
