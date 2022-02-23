package sbnri.consumer.android.constants;

import android.Manifest;

import java.util.regex.Pattern;

public interface Constants {

    int GOOGLE_PLAY_SERVICES = 1;
    String CONSUMER_MOBILE_NUMBER = "MobileNumber";
    String CONSUMER_ID = "ConsumerID";
    String BASE_URL = "BaseURL";
    String ACCESS_TOKEN = "AccessToken";

    int COMPRESSED_IMAGE_WIDTH = 720;

    int REQUEST_PERMISSION_INDIVIDUAL = 71;

    String FRAG_ID = "fragId";

    String PREFERRED_BANKS_META_DATA = "mPreferredBanksMetaData";
    String OTHERS_BANKS_META_DATA = "mOthersaBanksMetaData";
    String PREFERRED_BANK_LIST = "mPreferredBanksList";
    String OTHERS_BANK_LIST = "mOthersBanksList";
    String ALL_BANKS_LIST = "allBanksList";
    String CLICKED_BANK = "clickedBank";
    String  SBNRI_USER_OBJ = "UserDetails";

    // PERMISSIONS
    String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    String ID_TOKEN = "idToken";
    String IS_PUSHED = "isPushed";

    String IS_PROFILE_COMPLETED = "profile_completed";

    // Network Constants
    int TIMEOUT = 90;

    public static final String EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    ).pattern();


    public static final String version = "2.1";
    public static final String device_type = "";
    public static final String IP_ADDRESS = "123456";
    public static final String PAGE_1 = "1";
    public static final String LIMIT_100 = "100";

    //service list
    public static final String get_home_data = "get-home-data";
    public static final String get_news_bulletin = "get-news-bulletin";
    public static final String get_categories = "get-categories";
    public static final String get_news = "get-news";
    public static final String get_circular_details = "get-circular-details";
    public static final String get_event_details = "get-event-details";
    public static final String get_gallery_details = "get-gallery-details";
    public static final String get_news_details = "get-news-details";
    public static final String get_about_us = "get-about-us";
    public static final String get_cm_message = "get-cm-message";
    public static final String get_committees_page = "get-committees-page";
    public static final String get_contact = "get-contact";
    public static final String get_dignitary = "get-dignitary";
    public static final String get_dignitary_ios = "get-dignitary-ios";
    public static final String get_gallery = "get-gallery";
    public static final String member_login = "member-login";
    public static final String get_membership = "get-membership";
    public static final String get_circulars = "get-circulars";

    public static String IP = "";
    public static String DEVICE_TYPE = "2";


    //Intent Contants
    public static String ID = "ID";
    public static String SCREEN_TITLE = "screenTitle";
}
