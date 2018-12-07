package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration;

/**
 * Created by Ravi Tamada on 28/09/16.
 * www.androidhive.info
 */

public class ConfigNotification {

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String NOTIFICATION_IS_BACKGROUND = "isBackground";
    public static final String NOTIFICATION_IS_FOREGROUND = "isForeground";

    public static final String SHARED_PREF = "ah_firebase";

    public static final String NOTIFICATION_DATA = "data";
    public static final String NOTIFICATION_TITLE = "title";
    public static final String NOTIFICATION_INDEX = "index";
    public static final String NOTIFICATION_MESAGE = "message";
    public static final String NOTIFICATION_IOFFICE_ID = "id";
    public static final String NOTIFICATION_TYPE = "type";
}
