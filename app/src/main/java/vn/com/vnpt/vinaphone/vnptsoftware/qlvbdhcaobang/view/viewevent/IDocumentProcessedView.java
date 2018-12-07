package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import android.graphics.Bitmap;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentProcessed;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentProcessedInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IDocumentProcessedView {
    void onSuccessRecords(List<DocumentProcessedInfo> documentProcessedInfoList);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentProcessed countDocumentProcessed);
    void showProgress();
    void hideProgress();
}