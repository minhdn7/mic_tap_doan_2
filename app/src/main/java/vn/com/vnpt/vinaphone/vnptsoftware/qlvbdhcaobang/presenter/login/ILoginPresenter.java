package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.LoginRequest;

/**
 * Created by VietNH on 4/20/2017.
 */

public interface ILoginPresenter {
    void getUserLoginPresenter(LoginRequest loginRequest);
    void loginPresenter(LoginRequest loginRequest);
}
