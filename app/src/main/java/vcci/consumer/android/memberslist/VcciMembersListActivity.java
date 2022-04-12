package vcci.consumer.android.memberslist;

import android.os.Bundle;

import androidx.annotation.Nullable;
import vcci.consumer.android.DependencyInjectorComponent;
import vcci.consumer.android.R;
import vcci.consumer.android.base.activity.BaseActivity;
import vcci.consumer.android.base.contract.BaseView;

class VcciMembersListActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberslist);
    }

    @Override
    protected BaseView getBaseView() {
        return null;
    }

    @Override
    protected void callDependencyInjector(DependencyInjectorComponent injectorComponent) {

    }
}
