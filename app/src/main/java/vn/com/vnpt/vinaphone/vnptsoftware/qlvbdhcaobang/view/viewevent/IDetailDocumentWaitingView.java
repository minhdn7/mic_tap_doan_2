package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IDetailDocumentWaitingView {
    void onSuccess(Object object);
    void onComment();
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
    void onMark();
    void onFinish();
    void onCheckFinish(boolean isFinish);
}