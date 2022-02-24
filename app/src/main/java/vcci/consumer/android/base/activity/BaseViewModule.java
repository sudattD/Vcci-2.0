package vcci.consumer.android.base.activity;

import dagger.Module;
import dagger.Provides;
import vcci.consumer.android.base.contract.BaseView;

@Module
public class BaseViewModule {

    private final BaseView baseView;

    public BaseViewModule(BaseView baseView) {
        this.baseView = baseView;
    }

    @Provides
    public BaseView getBaseView() {
        return baseView;
    }

    @Provides
    BaseViewModule getBaseViewModule() {
        return this;
    }
}
