package sbnri.consumer.android.webservice;

import java.util.HashMap;

import androidx.annotation.Nullable;
import io.reactivex.disposables.Disposable;
import sbnri.consumer.android.SBNRIApp;
import sbnri.consumer.android.webservice.model.SBNRIResponse;

public interface ApiCallbacks {

    void onSessionExpired();

    void onSuccess(@ApiCallTags.ApiCallIdentifiers String callTag, SBNRIResponse response, HashMap<String, Object> extras);

    void onFailure(@ApiCallTags.ApiCallIdentifiers String callTag, SBNRIResponse response, HashMap<String, Object> extras);

    void onError(@ApiCallTags.ApiCallIdentifiers String callTag, Throwable e, HashMap<String, Object> extras);

    void deleteSubscriberOnComplete(@ApiCallTags.ApiCallIdentifiers String callTag, @Nullable Disposable disposable);
}
