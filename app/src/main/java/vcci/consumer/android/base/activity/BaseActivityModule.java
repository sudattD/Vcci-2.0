package vcci.consumer.android.base.activity;

import android.app.Activity;
import android.content.Context;

import com.flipboard.bottomsheet.BottomSheetLayout;

import dagger.Module;
import dagger.Provides;
import vcci.consumer.android.R;
import vcci.consumer.android.qualifiers.ActivityContext;
import vcci.consumer.android.scopes.BaseActivityScope;

@Module
public class BaseActivityModule {

    private final Activity activity;

    BaseActivityModule(Activity activity) {
        this.activity = activity;
    }

    @BaseActivityScope
    @Provides
    public Activity activity() {
        return activity;
    }

    @ActivityContext
    @Provides
    Context activityContext() {
        return activity;
    }

    @BaseActivityScope
    @Provides
    BottomSheetLayout getBaseBottomSheetLayout() {
        return activity.findViewById(R.id.bottomsheet);
    }


}
