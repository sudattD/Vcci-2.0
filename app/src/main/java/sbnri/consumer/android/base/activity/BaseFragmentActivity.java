package sbnri.consumer.android.base.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.CallSuper;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import sbnri.consumer.android.DependencyInjectorComponent;
import sbnri.consumer.android.R;
import sbnri.consumer.android.base.contract.BaseView;

public abstract class BaseFragmentActivity extends BaseActivity implements BaseFragment.FragmentTransacListener,
        BaseFragment.FragmentAttachListener{

    private BaseFragment currentFragment;
    protected boolean isPushed = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @CallSuper
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
            if(isPushed) {
                overridePendingTransition(R.anim.stay, R.anim.exit_to_right);
            } else {
                overridePendingTransition(R.anim.stay, R.anim.slide_down_bottom);
            }
            return;
        }

        if (currentFragment != null) {
            if (!currentFragment.onBackPressed()) {
                super.onBackPressed();
                return;
            }
        }

        super.onBackPressed();
    }

    private void transact(BaseFragment baseFragment, boolean shouldReplace) {
        if (getContainerId() == -1) {
           // Log.d("Specify container");
            return;
        }

        if (shouldReplace) {
           // Logger.d("inside replace");
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(getContainerId(), baseFragment).addToBackStack(null);
        transaction.commit();
    }

    public void addFragment(BaseFragment baseFragment) {
        transact(baseFragment, false);
    }

    protected void replaceFragment(BaseFragment baseFragment) {
        transact(baseFragment, true);
    }

    @Override
    public void requestTransaction(BaseFragment baseFragment, boolean shouldReplace) {
        transact(baseFragment, shouldReplace);
    }

    @Override
    public void onAttached(BaseFragment fragment) {
        currentFragment = fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                onBackPressed();
                return true;
            }
            getSupportFragmentManager().popBackStack();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }


    protected abstract int getContainerId();



}
