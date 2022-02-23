package sbnri.consumer.android.base.schedulers;

/**
 */

import androidx.annotation.NonNull;
import io.reactivex.Scheduler;


/**
 * Allow providing different types of {@link Scheduler}s.
 */
interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

}