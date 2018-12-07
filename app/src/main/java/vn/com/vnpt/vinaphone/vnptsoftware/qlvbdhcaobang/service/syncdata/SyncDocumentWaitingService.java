package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.realm.Realm;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentwaiting.DocumentWaitingDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.builder.DocumentWaitingBuilder;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.DocumentWaiting;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentWaiting;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.SyncEvent;

/**
 * Created by VietNH on 9/6/2017.
 */

public class SyncDocumentWaitingService extends Service implements HandleSyncService.HandleGetCount, HandleSyncService.HandleGetRecords, ICallFinishedListener {

    private DocumentWaitingDao documentWaitingDao;
    private LoginDao loginDao;
    private Realm realm;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        documentWaitingDao = new DocumentWaitingDao();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (connectionDetector.isConnectingToInternet()) {
            //documentWaitingDao.onCountDocumentWaitingDao(new DocumentWaitingRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE), ""), this);
//            documentWaitingDao.onRecordsDocumentWaitingDao(new DocumentWaitingRequest(Constants.PAGE_NO_ALL, String.valueOf("100000"), ""), this);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSuccessGetCount(Object object) {
        CountDocumentWaitingRespone countDocumentWaitingRespone = (CountDocumentWaitingRespone) object;
        if (countDocumentWaitingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            CountDocumentWaiting countDocumentWaiting = ConvertUtils.fromJSON(countDocumentWaitingRespone.getData(), CountDocumentWaiting.class);
            if (connectionDetector.isConnectingToInternet()) {
//                documentWaitingDao.onRecordsDocumentWaitingDao(new DocumentWaitingRequest(Constants.PAGE_NO_ALL, String.valueOf(countDocumentWaiting.getPageRec()), ""), this);
            }
        }
    }

    @Override
    public void onErrorGetCount(Object object) {
        APIError apiError = (APIError) object;
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            loginDao = new LoginDao();
            if (connectionDetector.isConnectingToInternet()) {
                loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), this);
            }
        }
    }

    @Override
    public void onSuccessGetRecords(Object object) {
        // Đồng bộ vào DB Local
        DocumentWaitingRespone documentWaitingRespone = (DocumentWaitingRespone) object;
        if (documentWaitingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            List<DocumentWaitingInfo> documentWaitingInfoList = ConvertUtils.fromJSONList(documentWaitingRespone.getData(), DocumentWaitingInfo.class);
            realm = RealmDao.with(getApplication()).getRealm();
            // Xóa dữ liệu văn bản chờ xử lý trong DB
            clearDocumentWaitings();
            if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
                // Cập nhật dữ liệu mới
                DocumentWaitingBuilder documentWaitingBuilder = new DocumentWaitingBuilder(this);
                List<DocumentWaiting> documentWaitings = documentWaitingBuilder.convertFromDocumentWaitingInfo(documentWaitingInfoList);
                if (documentWaitings != null && documentWaitings.size() > 0) {
                    saveDocumentWaiting(documentWaitings);
                }
            }
        }
    }

    @Override
    public void onErrorGetRecords(Object object) {
    }

    private void clearDocumentWaitings() {
        realm.beginTransaction();
        realm.delete(DocumentWaiting.class);
        realm.commitTransaction();
    }

    private void saveDocumentWaiting(List<DocumentWaiting> documentWaitings) {
        realm.beginTransaction();
        realm.copyToRealm(documentWaitings);
        realm.commitTransaction();
    }

    @Override
    public void onCallSuccess(Object object) {
        LoginRespone loginRespone = (LoginRespone) object;
        if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
            Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
            if (connectionDetector.isConnectingToInternet()) {
                //documentWaitingDao.onCountDocumentWaitingDao(new DocumentWaitingRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE), ""), this);
//                documentWaitingDao.onRecordsDocumentWaitingDao(new DocumentWaitingRequest(Constants.PAGE_NO_ALL, String.valueOf("100000"), ""), this);
            }
        }
    }

    @Override
    public void onCallError(Object object) {
    }

}
