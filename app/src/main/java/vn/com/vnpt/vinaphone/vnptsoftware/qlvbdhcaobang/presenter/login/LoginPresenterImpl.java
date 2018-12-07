package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

/**
 * Created by VietNH on 4/20/2017.
 */

public class LoginPresenterImpl implements ILoginPresenter, ICallFinishedListener {
    public ILoginView loginView;
    public LoginDao loginDao;

    public LoginPresenterImpl(ILoginView loginView) {
        this.loginView = loginView;
        this.loginDao = new LoginDao();
    }

    @Override
    public void getUserLoginPresenter(LoginRequest loginRequest) {
        if (loginView != null) {
            loginDao.onSendLoginDao(loginRequest, this);
        }
    }

    @Override
    public void loginPresenter(LoginRequest loginRequest) {
        if (loginView != null) {
            loginView.showProgress();
            loginDao.onSendLoginDao(loginRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (loginView != null) {
            loginView.hideProgress();
        }
        LoginRespone loginRespone = (LoginRespone) object;
        if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
            loginView.onSuccessLogin(loginInfo);
        } else {
            loginView.onErrorLogin(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
        }
    }

    @Override
    public void onCallError(Object object) {
        loginView.onErrorLogin((APIError) object);
        loginView.hideProgress();
    }

}
