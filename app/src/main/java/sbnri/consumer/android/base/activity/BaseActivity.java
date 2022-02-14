package sbnri.consumer.android.base.activity;

import android.content.Context;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;

import javax.inject.Inject;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sbnri.consumer.android.AppState;
import sbnri.consumer.android.DaggerDependencyInjectorComponent;
import sbnri.consumer.android.DependencyInjectorComponent;
import sbnri.consumer.android.R;
import sbnri.consumer.android.SBNRIApp;
import sbnri.consumer.android.base.contract.BasePresenterImp;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.data.local.SBNRIPref;
import sbnri.consumer.android.qualifiers.ActivityContext;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.receivers.NetworkChangeReceiver;
import sbnri.consumer.android.receivers.SharedListeners;
import sbnri.consumer.android.util.ActivityUtils;

public abstract class BaseActivity extends AppCompatActivity implements BaseFragment.BaseFragmentContract, SharedListeners.NetworkChangeListener {


    @BindView(R.id.tbBaseToolbar)
    public Toolbar baseToolbar;

    @BindView(R.id.tvToolbarTitle)
    public TextView tvToolbarTitle;

    @BindView(R.id.no_internet_constraint)
    View no_internet_view;

    @BindView(R.id.btn_retry)
    Button btn_retry;

    @BindView(R.id.rl_progress)
    public View rl_progress;

    private NetworkChangeReceiver networkChangeReceiver;

    private RelativeLayout baseLayout;

    public int previousState = AppState.getAppState();

    @ApplicationContext
    @Inject
    public Context appContext;

    @ActivityContext
    @Inject
    protected Context context;

    @Inject
    public BottomSheetLayout bottomSheet;

    @Inject
    public SBNRIPref sbnriPref;



    protected BaseActivityComponent activityComponent;
    private BasePresenterImp basePresenterImp;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        baseLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.base_layout, null);
        setContentView(baseLayout);
        stubLayoutRes(layoutResID);
        ButterKnife.bind(this);
        initialiseBaseActivityComponent();
        initialiseDaggerDependencies();
        initActionBar();
    }

    private void initActionBar()
    {
        setSupportActionBar(baseToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

    }
    protected void initialiseDaggerDependencies() {
        callDependencyInjector(initialiseDaggerInjector());
    }

    private DependencyInjectorComponent initialiseDaggerInjector() {
        return DaggerDependencyInjectorComponent.builder().baseActivityComponent(activityComponent).
                baseViewModule(new BaseViewModule(getBaseView())).build();
    }

    private void initialiseBaseActivityComponent() {

        activityComponent = DaggerBaseActivityComponent.builder()
                .sBNRIAppComponent(((SBNRIApp) getApplicationContext()).getComponent())
                .baseActivityModule(new BaseActivityModule(this))
                .build();

    }

    private void stubLayoutRes(@LayoutRes int layoutResID) {
        ViewStub stub = baseLayout.findViewById(R.id.container);
        stub.setLayoutResource(layoutResID);
        stub.setFilterTouchesWhenObscured(true);
        stub.inflate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void initToolbar(String title, int titleColor, int toolbarColor, int navigationIcon) {
        ActivityUtils.initToolbar(this, title, titleColor, toolbarColor, navigationIcon);
    }

    public void setBasePresenterImp(BasePresenterImp basePresenterImp) {
        this.basePresenterImp = basePresenterImp;
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    private void registerReceiver() {
        networkChangeReceiver = new NetworkChangeReceiver();
        networkChangeReceiver.setSharedListener(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public BaseActivityComponent getBaseActivityComponent() {
        return activityComponent;
    }

    protected abstract BaseView getBaseView();
    protected abstract void callDependencyInjector(DependencyInjectorComponent injectorComponent);


    protected boolean isNetworkOnline() {
        return ActivityUtils.isNetworkOnline(context);
    }
    @Override
    public void onNetworkChanged() {
        AppState.setIsOnline(isNetworkOnline());

        if (!AppState.isAppOnline()) {
           // BottomSheetUtil.Companion.showNetworkBottomSheet(context,bottomSheet);

         //   no_internet_view.setVisibility(View.VISIBLE);
        }
        if (AppState.isAppOnline()) {
           //BottomSheetUtil.Companion.dismissNetworkBottomSheet(bottomSheet);
          //  no_internet_view.setVisibility(View.GONE);

        }
    }

    public void runNetworkDependentTask(Runnable onNetworkAvailable, Runnable onNetworkUnavailable) {
        switch (AppState.getAppState()) {
            case AppState.STATE_ONLINE:
                onNetworkAvailable.run();
                break;
            case AppState.STATE_OFFLINE:
                if (onNetworkUnavailable != null) {
                    onNetworkUnavailable.run();
                } else {
                    //servifyToast(getString(R.string.please_connect_to_internet_to_contiue), Toast.LENGTH_SHORT, true);
                }
                break;
            case AppState.STATE_SYNCING:
                //servifyToast(getString(R.string.refreshing_data_wait), Toast.LENGTH_SHORT, true);
                break;
        }
    }

    @OnClick(R.id.btn_retry)
    public void setBtn_retry()
    {
        no_internet_view.setVisibility(View.GONE);

    }

    public void sbnriToast(CharSequence message, int duration, boolean isErrorToast) {
        ActivityUtils.sbnriToast(context, message, duration, isErrorToast);
    }


}
