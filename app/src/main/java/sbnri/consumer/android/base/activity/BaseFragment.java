package sbnri.consumer.android.base.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import sbnri.consumer.android.AppState;
import sbnri.consumer.android.DaggerDependencyInjectorComponent;
import sbnri.consumer.android.DependencyInjectorComponent;
import sbnri.consumer.android.base.contract.BasePresenterImp;
import sbnri.consumer.android.base.contract.BaseView;
import sbnri.consumer.android.qualifiers.ActivityContext;
import sbnri.consumer.android.qualifiers.ApplicationContext;
import sbnri.consumer.android.util.ActivityUtils;

public abstract class BaseFragment extends Fragment {

    @ActivityContext
    @Inject
    protected Context context;

    @Inject
    @ApplicationContext
    protected Context appContext;

    @Inject
    protected Activity activity;
    protected boolean isOfflineActive = false;
    //StickyBottomSheetDialog stickyBottomSheetDialog;
    protected String flow;
    private FragmentTransacListener fragmentTransacListener;
    private FragmentAttachListener fragmentAttachListener;
    private BaseFragmentContract baseFragmentContract;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = onCreateKnifeView(inflater, container, savedInstanceState);
        if (baseFragmentContract != null)
            initialiseDaggerDependencies(baseFragmentContract.getBaseActivityComponent());
        ButterKnife.bind(this, view);
//        view.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        return view;
    }


    protected void initialiseDaggerDependencies(BaseActivityComponent activityComponent) {
        callDependencyInjector(DaggerDependencyInjectorComponent.builder().baseActivityComponent(activityComponent)
                .baseViewModule(new BaseViewModule(getBaseView())).build());
    }


    protected abstract BaseView getBaseView();

    protected abstract void callDependencyInjector(DependencyInjectorComponent injectorComponent);

    public abstract View onCreateKnifeView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected String getScreenName() {
        return this.getClass().getName();
    }

    protected String getBrand() {
        return null;
    }

    protected String getCategory() {
        return null;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (activity instanceof FragmentTransacListener) {
            fragmentTransacListener = (FragmentTransacListener) activity;
        }

        if (activity instanceof FragmentAttachListener) {
            fragmentAttachListener = (FragmentAttachListener) activity;
            fragmentAttachListener.onAttached(this);

        }

        if (activity instanceof BaseFragmentContract) {
            baseFragmentContract = (BaseFragmentContract) activity;
        } else {
            throw new RuntimeException("Implement BaseFragmentContract to use BaseFragment");
        }
    }

    protected void replaceFragment(BaseFragment baseFragment) {
        if (fragmentTransacListener != null) {
            fragmentTransacListener.requestTransaction(baseFragment, true);
        }
    }

    protected void initToolbar(String title, int titleColor, int toolbarColor, int navigationIcon) {
        ActivityUtils.initToolbar((BaseActivity) getActivity(), title, titleColor, toolbarColor, navigationIcon);
    }

    protected void hideToolbar() {
        ((BaseActivity) getActivity()).baseToolbar.setVisibility(View.GONE);
    }

    protected void showNetworkBottomSheet() {
        if (getActivity() != null) {
           // ActivityUtils.showNetworkBottomSheet(getActivity());
        }
    }

    protected boolean isNetworkOnline() {
        return ActivityUtils.isNetworkOnline(getActivity());
    }

    protected void hideKeyboard() {
        if (getActivity() != null) {
            View view = getActivity().getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }

    protected void showKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            view.requestFocus();
            if (inputMethodManager != null) {
                inputMethodManager.showSoftInput(view, 0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!AppState.isAppOnline()) {
            showNetworkBottomSheet();
        }
    }

    public void overridePendingTransition(int enterAnim, int exitAnim) {
        if (getActivity() != null) {
            getActivity().overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void setResult(int resultCode, Intent data) {
        if (getActivity() != null) {
            getActivity().setResult(resultCode, data);
        }
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // RxBus.unregisterSubject(RxBus.APP_ONLINE, this);
    }

    public void accessTokenExpired() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
           // ((BaseActivity) getActivity()).accessTokenExpired();
        }
    }

    protected void setBaseActivityPresenter(BasePresenterImp basePresenterImp) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setBasePresenterImp(basePresenterImp);
        } else {
        }
    }

    protected void runNetworkDependentTask(Runnable onNetworkAvailable, Runnable onNetworkUnavailable) {
        if (AppState.isAppOnline()) {
            onNetworkAvailable.run();
        } else {
            if (onNetworkUnavailable != null) {
                onNetworkAvailable.run();
            } else {
            }
        }
    }

    protected int getPreviousState() {
        if (getActivity() instanceof BaseActivity) {
            return ((BaseActivity) getActivity()).previousState;
        } else return AppState.getAppState();
    }

    public interface FragmentTransacListener {
        void requestTransaction(BaseFragment baseFragment, boolean shouldReplace);
    }

    public interface FragmentAttachListener {
        void onAttached(BaseFragment fragment);
    }


    public interface BaseFragmentContract {
        BaseActivityComponent getBaseActivityComponent();
    }

}


