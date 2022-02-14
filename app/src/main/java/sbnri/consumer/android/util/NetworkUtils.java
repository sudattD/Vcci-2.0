package sbnri.consumer.android.util;

import com.orhanobut.logger.Logger;

import java.util.HashMap;

import androidx.annotation.Nullable;
import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;
import sbnri.consumer.android.base.schedulers.SchedulerProvider;
import sbnri.consumer.android.data.local.SBNRIRepository;
import sbnri.consumer.android.webservice.ApiCallTags;
import sbnri.consumer.android.webservice.ApiCallbacks;
import sbnri.consumer.android.webservice.model.SBNRIResponse;

public class NetworkUtils {

    public static Disposable makeNetworkCallWithClearSubscription(@ApiCallTags.ApiCallIdentifiers final String callTag, Flowable flowable, SchedulerProvider schedulerProvider, final ApiCallbacks apiCallbacks, final HashMap<String, Object> extras, CompositeDisposable existingCallSubscription) {
        if (existingCallSubscription != null) {
            existingCallSubscription.clear();
        }
        return makeNetworkCall(callTag, flowable, schedulerProvider, apiCallbacks, extras);
    }

    public static Disposable makeNetworkCallWithClearSubscription(@ApiCallTags.ApiCallIdentifiers final String callTag, Flowable flowable, SchedulerProvider schedulerProvider, final ApiCallbacks apiCallbacks, CompositeDisposable existingCallSubscription) {
        return makeNetworkCallWithClearSubscription(callTag, flowable, schedulerProvider, apiCallbacks, null, existingCallSubscription);
    }

    public static Disposable makeNetworkCall(@ApiCallTags.ApiCallIdentifiers final String callTag, Flowable flowable, SchedulerProvider schedulerProvider, final ApiCallbacks apiCallbacks) {
        return makeNetworkCall(callTag, flowable, schedulerProvider, apiCallbacks, null);
    }

    public static Disposable makeNetworkCall(@ApiCallTags.ApiCallIdentifiers final String callTag, Flowable flowable, SchedulerProvider schedulerProvider, final ApiCallbacks apiCallbacks, final HashMap<String, Object> extras) {

        return (Disposable) flowable.subscribeOn(schedulerProvider.io()).observeOn(schedulerProvider.ui(), true)
                .subscribeWith(new DisposableSubscriber<SBNRIResponse>() {
                    @Override
                    public void onNext(SBNRIResponse response) {

                        if(response.getData() != null)
                        {
                            apiCallbacks.onSuccess(callTag, response, extras);
                        }
                        else
                        {
                            apiCallbacks.onFailure(callTag, response, extras);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            if (httpException.code() == 401) {
                                apiCallbacks.onSessionExpired();
                                return;
                            }
                        }
                        apiCallbacks.onError(callTag, e, extras);
                    }

                    @Override
                    public void onComplete() {
                        apiCallbacks.deleteSubscriberOnComplete(callTag, this);
                    }
                });
    }

    public static abstract class ApiCallBackEmptyImplementer implements ApiCallbacks {

        @Override
        public void onFailure(String callTag, SBNRIResponse response, HashMap<String, Object> extras) {

        }

        @Override
        public void onError(String callTag, Throwable e, HashMap<String, Object> extras) {
            Logger.d(e.getMessage());
        }

        @Override
        public void onSessionExpired() {

        }

        @Override
        public void deleteSubscriberOnComplete(String callTag, @Nullable Disposable disposable) {

        }
    }



}
