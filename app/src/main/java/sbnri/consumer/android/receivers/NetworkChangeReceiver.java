package sbnri.consumer.android.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private SharedListeners.NetworkChangeListener mNetworkChangeListenrs;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mNetworkChangeListenrs != null) {
            mNetworkChangeListenrs.onNetworkChanged();
        }
    }

    public void setSharedListener(SharedListeners.NetworkChangeListener networkChangeListener) {
        this.mNetworkChangeListenrs = networkChangeListener;
    }



}
