package vcci.consumer.android.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vcci.consumer.android.DependencyInjectorComponent;
import vcci.consumer.android.R;
import vcci.consumer.android.aboutus.AboutUsActivity;
import vcci.consumer.android.adapters.OnCommonItemClickListener;
import vcci.consumer.android.api.ApiClient;
import vcci.consumer.android.api.ApiInterface;
import vcci.consumer.android.base.activity.BaseActivity;
import vcci.consumer.android.base.activity.BaseActivityComponent;
import vcci.consumer.android.base.activity.BaseFragment;
import vcci.consumer.android.base.contract.BaseView;
import vcci.consumer.android.committees.CommitteesActivity;
import vcci.consumer.android.constants.Constants;
import vcci.consumer.android.data.models.dashboard_news.BannersItem;
import vcci.consumer.android.data.models.dashboard_news.DashboardResponse;
import vcci.consumer.android.dignnitaries.DignitariesActivity;
import vcci.consumer.android.events.EventsActivity;
import vcci.consumer.android.gallery.GalleryActivity;
import vcci.consumer.android.membership.MembershipActivity;
import vcci.consumer.android.news.adapter.DashboardBannersSliderAdapter;
import vcci.consumer.android.qualifiers.HomeFragmentPresenter;
import vcci.consumer.android.util.WordUtils;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragmentJ extends BaseFragment implements HomeContract.HomeFragmentView, OnCommonItemClickListener {


    @HomeFragmentPresenter
    @Inject
    HomePresenterImplJ presenter;

    HomeItemsAdapter mAdapter;

    ArrayList<HomeActionItem> homeActionItemArrayList;
    HomeActionItem homeActionItem;
    private List<BannersItem> bannersItemList;

    @Inject
    Picasso picasso;

    @BindView(R.id.rvHomeItems)
    RecyclerView rvHomeItems;

    @BindView(R.id.home_viewPager)
    ViewPager viewPager1;

    @BindView(R.id.indicator)
    TabLayout indicator;

    @BindView(R.id.tvActions)
    TextView tvActions;


    @Override
    protected BaseView getBaseView() {
        return this;
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {
            //nothing
    }

    @Override
    public View onCreateKnifeView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    public static HomeFragmentJ newInstance() {
        HomeFragmentJ fragment = new HomeFragmentJ();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initView();
        setBaseActivityPresenter(presenter);

    }

    @Override
    public void initView() {
        tvActions.setText(WordUtils.fromHtml(String.format(getString(R.string.welcome_vcci),String.format(getString(R.string.vcci)))));
        setUpViewPagerAdapter();
        setUpHomeItemsAdapter();
        getNewsDataList();
    }

    private void setUpViewPagerAdapter() {
    }

    private void setUpHomeItemsAdapter() {

        setupHomeActionItems();
        mAdapter = new HomeItemsAdapter(setupHomeActionItems(),picasso,this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        rvHomeItems.setLayoutManager(gridLayoutManager);
        rvHomeItems.setAdapter(mAdapter);
        rvHomeItems.setHasFixedSize(false);
        rvHomeItems.setDrawingCacheEnabled(true);
    }

    private ArrayList<HomeActionItem> setupHomeActionItems() {

        homeActionItemArrayList = new ArrayList<>();
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_aboutus,context.getResources().getString(R.string.about_us),1));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_committees,context.getResources().getString(R.string.committies),2));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_messages,context.getResources().getString(R.string.dignitaries_msg),3));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_events,context.getResources().getString(R.string.events),4));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_membership,context.getResources().getString(R.string.membership),5));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_gallery,context.getResources().getString(R.string.gallery),6));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_memlist,context.getResources().getString(R.string.member_list),7));

        return homeActionItemArrayList;
    }

    @Override
    public void showProgress() {

        ((BaseActivity)activity).rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ((BaseActivity) activity).rl_progress.setVisibility(View.GONE);
    }

    @Override
    public void showToastMessage(String toastMessage, boolean isErrortoast) {

    }

    @Override
    protected void initialiseDaggerDependencies(BaseActivityComponent activityComponent) {
        DaggerHomeComponent.builder()
                .homeModuleJ(new HomeModuleJ(this))
                .baseActivityComponent(activityComponent)
                .build().injectDependencies(this);

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
              /*  List<LatestNewsItem> newsItemList = response.body().getLatestNews();
                latestNewsAdapter = new LatestNewsAdapter(getContext(), newsItemList, listener);
                rv_news.setAdapter(latestNewsAdapter);*/

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

    @Override
    public void onItemClick(View view, Object object) {

        if(object instanceof HomeActionItem)
        {
            HomeActionItem item = (HomeActionItem) object;
            switch (item.getId())
            {
                case 1:
                    //AboutUs Activity
                    Intent intent = new Intent(context,AboutUsActivity.class);
                    intent.putExtra(Constants.ID,"1");
                    intent.putExtra(Constants.SCREEN_TITLE,"About Us");
                    startActivity(intent);
                    break;

                case 2:
                    //Committies page
                    Intent intentCommittee = new Intent(context, CommitteesActivity.class);
                    intentCommittee.putExtra(Constants.SCREEN_TITLE,"Committee");
                    startActivity(intentCommittee);
                    break;

                case 3:
                    //Dignitaries msg page
                    Intent intentDignitaries = new Intent(context, DignitariesActivity.class);
                    intentDignitaries.putExtra(Constants.SCREEN_TITLE,"Dignitaries");
                    startActivity(intentDignitaries);
                    break;

                case 4:
                    //Event page
                    Intent intentx = new Intent(context, EventsActivity.class);
                    intentx.putExtra(Constants.SCREEN_TITLE,"Events");
                    startActivity(intentx);
                    break;

                case 5:
                    //Membership page
                    Intent member = new Intent(context, MembershipActivity.class);
                    member.putExtra(Constants.SCREEN_TITLE,"Membership");
                    startActivity(member);
                    break;

                case 6:
                    //Gallery page
                    Intent gallery = new Intent(context, GalleryActivity.class);
                    gallery.putExtra(Constants.SCREEN_TITLE,"Gallery");
                    startActivity(gallery);
                    break;

                    case 7:
                        //VCCI member list
                        break;

                default:

                    break;

            }
        }

    }
}
