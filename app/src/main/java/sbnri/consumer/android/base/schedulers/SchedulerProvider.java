package sbnri.consumer.android.base.schedulers;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import sbnri.consumer.android.scopes.ApplicationScope;

/**
 */
@ApplicationScope
public class SchedulerProvider implements BaseSchedulerProvider {

    @Inject
    public SchedulerProvider() {
    }

    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

}
