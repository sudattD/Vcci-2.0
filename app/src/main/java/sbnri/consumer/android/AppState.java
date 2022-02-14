package sbnri.consumer.android;

import android.content.Context;

public class AppState {
    public static final int STATE_ONLINE = 1;
    public static final int STATE_SYNCING = 2;
    public static final int STATE_OFFLINE = 3;
    private static boolean isOnline = true;
    private static int appState = STATE_ONLINE;

    public static boolean isAppOnline() {
        return isOnline;
    }

    public static void setIsOnline(boolean isOnline) {
        AppState.isOnline = isOnline;
        if (isOnline) {
            AppState.appState = STATE_ONLINE;
        } else {
            AppState.appState = STATE_OFFLINE;
        }
    }

    public static int getAppState() {
        return appState;
    }

    public static void setAppState(int appState) {
        AppState.appState = appState;
    }

    public static void setAppStateOnNetwork() {
        if (isOnline) {
            AppState.appState = STATE_ONLINE;
        } else {
            AppState.appState = STATE_OFFLINE;
        }
    }


    public static String getAppStateMessage(Context context) {
        switch (appState) {
            case STATE_OFFLINE:
                return context.getString(R.string.you_seem_to_be_offline);
            case STATE_SYNCING:
                return context.getString(R.string.refreshing_data);
            default:
                return "";
        }
    }

    //TO-DO
    public static int getStateImage() {
        switch (appState) {
            case STATE_OFFLINE:
                return R.drawable.profile; // TO-DO
            case STATE_SYNCING:
                return 0;
            default:
                return 0;
        }
    }

    public static boolean shouldRefreshOnAppOnline(int previousState) {
        //Logger.i("Previous State: " + previousState);
        return previousState != AppState.STATE_ONLINE;
    }
}