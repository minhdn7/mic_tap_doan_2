package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface ICheckVersionView {
    void onSuccess();
    void onGoToUpdateStore(String message);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}