package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface ILoginView {
    void onSuccessLogin(LoginInfo loginInfo);
    void onErrorLogin(APIError apiError);
    void showProgress();
    void hideProgress();
}