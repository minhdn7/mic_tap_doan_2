package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UserInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IUserSwitchView {
    void onSuccessSwitchUser(LoginInfo userInfo);
    void onErrorSwitchUser(APIError apiError);
    void showProgress();
    void hideProgress();
}