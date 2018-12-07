package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IChiDaoView {
    void onSuccess(List<Object> object);
    void onSuccess(Object object);
    void onSuccess();
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}