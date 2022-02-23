package sbnri.consumer.android.base.contract;

import android.content.Context;

public interface BaseView {

    void initView();

    void showProgress();

    void hideProgress();

    void showToastMessage(String toastMessage, boolean isErrortoast);

    void accessTokenExpired();

    interface UploadImage {
        void updateImageAfterUpload(String path);

        Context getAppContext();
    }

}