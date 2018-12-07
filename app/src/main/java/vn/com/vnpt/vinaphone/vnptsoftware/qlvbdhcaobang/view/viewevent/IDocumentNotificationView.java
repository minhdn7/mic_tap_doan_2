package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import android.graphics.Bitmap;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentNotificationInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IDocumentNotificationView {
    void onSuccessRecords(List<DocumentNotificationInfo> documentNotificationInfoList);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentNotification countDocumentNotification);
    void showProgress();
    void hideProgress();
}