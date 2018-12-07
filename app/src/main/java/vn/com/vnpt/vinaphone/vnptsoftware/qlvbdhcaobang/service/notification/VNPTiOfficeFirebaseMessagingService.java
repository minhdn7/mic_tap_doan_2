package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.notification;

import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jaredrummler.android.processes.AndroidProcesses;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.Date;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConfigNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.NotificationUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.notify.NotifyDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailChiDaoActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailDocumentNotificationActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailDocumentProcessedActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailDocumentWaitingActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailScheduleActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.LetterActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.ProfileWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.NhanWebViewEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.StepPre;


/**
 * Created by VietNH on 9/29/2016.
 */

public class VNPTiOfficeFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "TagFirebaseMessService";
    private NotificationUtils notificationUtils;
    private ConnectionDetector connectionDetector;
    private ICallFinishedListener callFinishedListener;
    private NotifyDao notifyDao;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null) return;
        //Displaying data in log
        //It is optional
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        //Calling method to generate notification
        // Check if message contains a notification payload.
        String title = "";
        String content = "";
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            content = remoteMessage.getNotification().getBody();
            title = remoteMessage.getNotification().getTitle();
        }
        JSONObject json = null;
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                json = new JSONObject(remoteMessage.getData());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
        try {
            if (title == null || title.equals("")) {
                title = json.getString("title");
            }
            if (content == null || content.equals("")) {
                content = json.getString("body");

                EventBus.getDefault().postSticky(new NhanWebViewEvent(1));
            }
        } catch (Exception ex) {
            title = getString(R.string.NEW_NOTIFICATION);
            content = getString(R.string.NEW_NOTIFICATION);
        }
        if (title == null || title.equals("")) {
            title = getString(R.string.NEW_NOTIFICATION);
        }
        if (content == null || content.equals("")) {
            content = getString(R.string.NEW_NOTIFICATION);
        }
        if (title != null && !title.equals("") && content!= null && !content.equals("") && json != null) {
            handleNotification(title, content, json);
        }
    }

    private void handleNotification(final String title, final String message, final JSONObject json) {
        try {
            final String notificationId = json.getString(ConfigNotification.NOTIFICATION_IOFFICE_ID);
            notifyDao = new NotifyDao();
            int notificationID = 0;
            try {
                notificationID = Integer.parseInt(notificationId);
            } catch (Exception ex) {
                Log.e(TAG, "Parse iOffice ID: " + ex.getMessage());
            }
            callFinishedListener = new ICallFinishedListener() {
                @Override
                public void onCallSuccess(Object object) {
                    if (object instanceof CheckStoreDocRespone) {
                        CheckStoreDocRespone checkStoreDocRespone = (CheckStoreDocRespone) object;
                        if (checkStoreDocRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                            try {
                                EventBus.getDefault().postSticky(new NhanWebViewEvent(1));
                                CheckStoreDocInfo checkStoreDocInfo = ConvertUtils.fromJSON(checkStoreDocRespone.getData(), CheckStoreDocInfo.class);
                                JSONObject jsonData = new JSONObject(json.toString());
                                String link = jsonData.getString("link");
                                String idDoc = link.split("\\|")[1];
                                Intent intent = null;
                                switch (checkStoreDocInfo.getType()) {
                                    case Constants.CONSTANTS_NOTI_CHOXULY:
                                        DocumentWaitingInfo itemWaiting = new DocumentWaitingInfo();
                                        itemWaiting.setId(idDoc);
                                        itemWaiting.setIsChuTri(checkStoreDocInfo.getParamCheckStoreDocInfo().getIsChuTri());
                                        itemWaiting.setIsCheck(checkStoreDocInfo.getParamCheckStoreDocInfo().getIsCheck());
                                        itemWaiting.setProcessDefinitionId(checkStoreDocInfo.getParamCheckStoreDocInfo().getProcessKey());
                                        itemWaiting.setCongVanDenDi(checkStoreDocInfo.getParamCheckStoreDocInfo().getCongVanDenDi());
//                                        EventBus.getDefault().postSticky(itemWaiting);
//                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemWaiting.getId(), Constants.DOCUMENT_WAITING, null));
//                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemWaiting.getId(), itemWaiting.getProcessDefinitionId(), itemWaiting.getCongVanDenDi()));
                                        intent = new Intent(getApplicationContext(), DetailDocumentWaitingActivity.class);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DOC_WATTING_INFOR,itemWaiting);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_TYPE_CHANGE_DOC,
                                                new TypeChangeDocumentRequest(itemWaiting.getId(), itemWaiting.getProcessDefinitionId(),
                                                        itemWaiting.getCongVanDenDi()));
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DETAIL_INFOR,
                                                new DetailDocumentInfo(itemWaiting.getId(), Constants.DOCUMENT_WAITING, null));
                                        break;
                                    case Constants.CONSTANTS_NOTI_TRACUU:
                                        DocumentSearchInfo item = new DocumentSearchInfo();
                                        item.setId(idDoc);
//                                        EventBus.getDefault().postSticky(item);
//                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                                        intent = new Intent(getApplicationContext(), DetailDocumentNotificationActivity.class);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DETAIL_INFOR,
                                                new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DOC_SEARCH_INFOR,item);

                                        break;
                                    case Constants.CONSTANTS_NOTI_DAXULY:
                                        DocumentProcessedInfo itemProcessed = new DocumentProcessedInfo();
                                        itemProcessed.setId(idDoc);
                                        itemProcessed.setIsChuTri(checkStoreDocInfo.getParamCheckStoreDocInfo().getIsChuTri());
                                        itemProcessed.setIsCheck(checkStoreDocInfo.getParamCheckStoreDocInfo().getIsCheck());
                                        itemProcessed.setProcessDefinitionId(checkStoreDocInfo.getParamCheckStoreDocInfo().getProcessKey());
                                        itemProcessed.setCongVanDenDi(checkStoreDocInfo.getParamCheckStoreDocInfo().getCongVanDenDi());
//                                        EventBus.getDefault().postSticky(itemProcessed);
//                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemProcessed.getId(), Constants.DOCUMENT_PROCESSED, null));
//                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemProcessed.getId(), itemProcessed.getProcessDefinitionId(), itemProcessed.getCongVanDenDi()));
                                        intent = new Intent(getApplicationContext(), DetailDocumentProcessedActivity.class);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DOC_PROCESSED_INFOR,itemProcessed);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_TYPE_CHANGE_DOC,
                                                new TypeChangeDocumentRequest(itemProcessed.getId(), itemProcessed.getProcessDefinitionId(), itemProcessed.getCongVanDenDi()));
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DETAIL_INFOR,
                                                new DetailDocumentInfo(itemProcessed.getId(), Constants.DOCUMENT_PROCESSED, null));
                                        break;
                                    case Constants.CONSTANTS_NOTI_THONGBAO:
                                        DocumentNotificationInfo itemNotification = new DocumentNotificationInfo();
                                        itemNotification.setId(idDoc);
                                        itemNotification.setIsChuTri(checkStoreDocInfo.getParamCheckStoreDocInfo().getIsChuTri());
                                        itemNotification.setIsCheck(checkStoreDocInfo.getParamCheckStoreDocInfo().getIsCheck());
                                        itemNotification.setProcessDefinitionId(checkStoreDocInfo.getParamCheckStoreDocInfo().getProcessKey());
                                        itemNotification.setCongVanDenDi(checkStoreDocInfo.getParamCheckStoreDocInfo().getCongVanDenDi());
//                                        EventBus.getDefault().postSticky(itemNotification);
//                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(itemNotification.getId(), Constants.DOCUMENT_NOTIFICATION, null));
//                                        EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemNotification.getId(), itemNotification.getProcessDefinitionId(), itemNotification.getCongVanDenDi()));
                                        intent = new Intent(getApplicationContext(), DetailDocumentNotificationActivity.class);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DOC_NOTIFICATION_INFOR,itemNotification);
                                        intent.putExtra(Constants.CONSTANTS_INTENT_TYPE_CHANGE_DOC,
                                                new TypeChangeDocumentRequest(itemNotification.getId(),
                                                        itemNotification.getProcessDefinitionId(), itemNotification.getCongVanDenDi()));
                                        intent.putExtra(Constants.CONSTANTS_INTENT_DETAIL_INFOR,
                                                new DetailDocumentInfo(itemNotification.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                                        break;
                                    case Constants.CONSTANTS_NOTI_WEB:
                                        AlertDialogManager.showAlertDialog(getApplicationContext(), getString(R.string.TITLE_NOTIFICATION), "Bạn vui lòng truy cập website để xử lý thông báo này", true, AlertDialogManager.INFO);
                                        break;
                                    case Constants.CONSTANTS_NOTI_TTDH:
                                        if(checkStoreDocInfo.getParamCheckStoreDocInfo().getIdThongTin() != null){
                                            Intent intentDieuHanh = new Intent(getApplicationContext(), DetailChiDaoActivity.class);
                                            intentDieuHanh.putExtra("ID_CHIDAO", checkStoreDocInfo.getParamCheckStoreDocInfo().getIdThongTin());
                                            startActivity(intentDieuHanh);
                                        }
                                        break;
                                }
                                if (AndroidProcesses.isMyProcessInTheForeground()) {
                                    // app is in foreground, broadcast the push message
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                                    intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                                    intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                                    showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, Integer.parseInt(notificationId));
                                } else {
                                    // app is in background, show the notification in notification tray
                                    intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                                    showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, Integer.parseInt(notificationId));
                                }
                            } catch (Exception ex) {
                            }
                        } else {
                        }
                    }
                }
                @Override
                public void onCallError(Object object) {

                }
            };
//            notifyDao.onReadedNotifysDao(callFinishedListener, new ReadedNotifyRequest(notificationId));
            try {
                JSONObject jsonData = new JSONObject(json.toString());
                if (Application.getApp().getAppPrefs().isLogined()) {
                    String link = jsonData.getString("link");
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_DOCUMENT.contains(jsonData.getString("type"))) {
                        notifyDao.onCheckStoreDocDao(callFinishedListener, notificationID);
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_SCHEDULE.contains(jsonData.getString("type"))) {
                        try {
                            WeekViewEvent weekViewEvent = new WeekViewEvent();
                            weekViewEvent.setId(Long.parseLong(link));
                            weekViewEvent.setType(Constants.SCHEDULE_MEETING);
                            EventBus.getDefault().postSticky(weekViewEvent);
                            EventBus.getDefault().postSticky(new StepPre(Constants.SCHEDULE_LIST));
                            Intent intent = new Intent(getApplicationContext(), DetailScheduleActivity.class);
                            if (AndroidProcesses.isMyProcessInTheForeground()) {
                                // app is in foreground, broadcast the push message
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                                intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                                intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                                showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                            } else {
                                // app is in background, show the notification in notification tray
                                intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                                showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_WORK.contains(jsonData.getString("type"))) {
                        Intent intent = new Intent(getApplicationContext(), DetailWorkActivity.class);
                        if (AndroidProcesses.isMyProcessInTheForeground()) {
                            // app is in foreground, broadcast the push message
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                            intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                            showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                        } else {
                            // app is in background, show the notification in notification tray
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                        }
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_PROFILE.contains(jsonData.getString("type"))) {
                        Intent intent = new Intent(getApplicationContext(), ProfileWorkActivity.class);
                        if (AndroidProcesses.isMyProcessInTheForeground()) {
                            // app is in foreground, broadcast the push message
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                            intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                            showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                        } else {
                            // app is in background, show the notification in notification tray
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                        }
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_MAIL.contains(jsonData.getString("type"))) {
                        Intent intent = new Intent(getApplicationContext(), LetterActivity.class);
                        if (AndroidProcesses.isMyProcessInTheForeground()) {
                            // app is in foreground, broadcast the push message
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                            intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                            showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                        } else {
                            // app is in background, show the notification in notification tray
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                        }
                    }
                    if (jsonData.getString("type") != null && Constants.TYPE_NOTIFY_CHIDAO.contains(jsonData.getString("type"))) {
                        String id = link.split("\\|")[1];
                        Intent intent = new Intent(getApplicationContext(), DetailChiDaoActivity.class);
                        intent.putExtra("ID_CHIDAO", id);
                        if (AndroidProcesses.isMyProcessInTheForeground()) {
                            // app is in foreground, broadcast the push message
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            intent.putExtra(ConfigNotification.NOTIFICATION_MESAGE, message);
                            intent.putExtra(ConfigNotification.NOTIFICATION_TITLE, title);
                            showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                        } else {
                            // app is in background, show the notification in notification tray
                            intent.putExtra(ConfigNotification.NOTIFICATION_DATA, json.toString());
                            showNotificationMessage(getApplicationContext(), title, message, new Date().toString(), intent, notificationID);
                        }
                    }
                }
            } catch(Exception ex) {
            }
        } catch (Exception ex) {

        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent, int notificationID) {
        notificationUtils = new NotificationUtils(context);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, defaultSoundUri, notificationID);
    }

}
