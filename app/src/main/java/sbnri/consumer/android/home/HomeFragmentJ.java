package sbnri.consumer.android.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sbnri.consumer.android.DependencyInjectorComponent;
import sbnri.consumer.android.R;
import sbnri.consumer.android.aboutus.AboutUsActivity;
import sbnri.consumer.android.adapters.OnCommonItemClickListener;
import sbnri.consumer.android.base.activity.BaseActivity;
import sbnri.consumer.android.base.activity.BaseActivityComponent;
import sbnri.consumer.android.base.activity.BaseFragment;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.committees.CommitteesActivity;
import sbnri.consumer.android.constants.Constants;
import sbnri.consumer.android.databinding.HomeFragmentBinding;
import sbnri.consumer.android.dignnitaries.DignitariesActivity;
import sbnri.consumer.android.events.EventsActivity;
import sbnri.consumer.android.gallery.GalleryActivity;
import sbnri.consumer.android.membership.MembershipActivity;
import sbnri.consumer.android.qualifiers.HomeFragmentPresenter;
import sbnri.consumer.android.util.WordUtils;

public class HomeFragmentJ extends BaseFragment implements HomeContract.HomeFragmentView, OnCommonItemClickListener {


    @HomeFragmentPresenter
    @Inject
    HomePresenterImplJ presenter;

    HomeFragmentBinding binding;

    HomeItemsAdapter mAdapter;

    ArrayList<HomeActionItem> homeActionItemArrayList;
    HomeActionItem homeActionItem;

    @Inject
    Picasso picasso;

    RecyclerView rvHomeItems;

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

        binding = HomeFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        rvHomeItems = binding.rvHomeItems;
        tvActions = binding.tvActions;
        return view;
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

        initView();
        setBaseActivityPresenter(presenter);
    }

    @Override
    public void initView() {
        tvActions.setText(WordUtils.fromHtml(String.format(getString(R.string.welcome_vcci),String.format(getString(R.string.vcci)))));
        setUpViewPagerAdapter();
        setUpHomeItemsAdapter();
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
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_evennts,context.getResources().getString(R.string.events),4));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_membership,context.getResources().getString(R.string.membership),5));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_gallery,context.getResources().getString(R.string.gallery),6));
        homeActionItemArrayList.add(new HomeActionItem(R.drawable.ic_memlist,context.getResources().getString(R.string.member_list),7));

        return homeActionItemArrayList;
    }

    @Override
    public void showProgress() {

        ((BaseActivity) getActivity()).rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        ((BaseActivity) getActivity()).rl_progress.setVisibility(View.GONE);
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
                    startActivity(intent);
                    break;

                case 2:
                    //Committies page
                    Intent intentCommittee = new Intent(context, CommitteesActivity.class);
                    startActivity(intentCommittee);
                    break;

                case 3:
                    //Dignitaries msg page
                    Intent intentDignitaries = new Intent(context, DignitariesActivity.class);
                    startActivity(intentDignitaries);
                    break;

                case 4:
                    //Event page
                    Intent intentx = new Intent(context, EventsActivity.class);
                    startActivity(intentx);
                    break;

                case 5:
                    //Membership page
                    Intent member = new Intent(context, MembershipActivity.class);
                    startActivity(member);
                    break;

                case 6:
                    //Gallery page
                    Intent gallery = new Intent(context, GalleryActivity.class);
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
