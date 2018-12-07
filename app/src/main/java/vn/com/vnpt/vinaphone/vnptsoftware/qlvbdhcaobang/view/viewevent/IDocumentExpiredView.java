package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import android.graphics.Bitmap;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentExpired;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentExpiredInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IDocumentExpiredView {
    void onSuccessRecords(List<DocumentExpiredInfo> documentExpiredInfos);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentExpired countDocumentExpired);
    void showProgress();
    void hideProgress();
}