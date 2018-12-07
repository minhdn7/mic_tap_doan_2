package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.CountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IReadedNotifyView {
    void onSuccess(boolean isRead);
    void onError(APIError apiError);
    void onCheckStoreSuccess(CheckStoreDocInfo data);
    void onCountDocumentSuccess(CountDocument data);
}