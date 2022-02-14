package sbnri.consumer.android.data.models;

public class TempAuthorization {

    private final String tzU;
    private final String appC;
    private final String appName;
    private final int versionCode;

    public TempAuthorization(String tzU, String appC, String appName, int versionCode) {
        this.tzU = tzU;
        this.appC = appC;
        this.appName = appName;
        this.versionCode = versionCode;
    }

}
