package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.logout;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.logout.LogoutDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogoutRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILogoutView;

/**
 * Created by VietNH on 4/20/2017.
 */

public class LogoutPresenterImpl implements ILogoutPresenter, ICallFinishedListener {
    public ILogoutView logoutView;
    public LogoutDao logoutDao;

    public LogoutPresenterImpl(ILogoutView logoutView) {
        this.logoutView = logoutView;
        this.logoutDao = new LogoutDao();
    }

    @Override
    public void logout() {
        if (logoutView != null) {
            logoutView.showLogoutProgress();
            logoutDao.onSendLogoutDao(this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        logoutView.hideLogoutProgress();
        LogoutRespone logoutRespone = (LogoutRespone) object;
        if (logoutRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            logoutView.onSuccessLogout();
        } else {
            logoutView.onErrorLogout(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
        }
    }

    @Override
    public void onCallError(Object object) {
        logoutView.hideLogoutProgress();
        logoutView.onErrorLogout((APIError) object);
    }

}
