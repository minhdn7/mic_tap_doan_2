package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.notification;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConfigNotification;


/**
 * Created by VietNH on 9/29/2016.
 */

public class VNPTiOfficeFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "TagFirebaseIDService";
    private ApplicationSharedPreferences appPrefs;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token ID: " + refreshedToken);
        appPrefs = Application.getApp().getAppPrefs();
        storeRegIdInPref(refreshedToken);
        // Notify UI that registration has completed, so the progress indicator can be hidden.

        //c6NXqaKHjnM:APA91bH0gZoJN5wy_OMIin5H7N-_E_fzqEC02Vpbyk2nhjJoxzMrCO7GfUhmo7QANISswvE9wtGs5SszcCAWuLclKXjqp6lB_P_grZL6p2EzimIk3U0HMzctGH04QOO3_jpzSGO50oMt

        //eaTf2N7zBwo:APA91bF8_4FsSZ8PQ2WlS8pzOO9xSObHh937VmoGlWltEcR3L79Oeer4lnD8vVCEVpwpTe7Noj7JehhEIfgQOcamefqoXQcovJClL0e_OeZsg3_2HQUfW8q8olHZ4-bL3c2Ti19ZiFyA
        Intent registrationComplete = new Intent(ConfigNotification.REGISTRATION_COMPLETE);
        registrationComplete.putExtra(StringDef.TOKEN_DEVICE_ID, refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void storeRegIdInPref(String token) {
        appPrefs.setFirebaseToken(token);
    }

}
