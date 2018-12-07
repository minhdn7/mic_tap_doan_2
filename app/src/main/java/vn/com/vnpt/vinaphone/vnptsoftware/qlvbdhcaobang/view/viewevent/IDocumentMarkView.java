package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import android.graphics.Bitmap;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentMark;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentMarkInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IDocumentMarkView {
    void onSuccessRecords(List<DocumentMarkInfo> documentMarkInfoList);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentMark countDocumentMark);
    void showProgress();
    void hideProgress();
}