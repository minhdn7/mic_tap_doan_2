package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changepassword;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.changepassword.ChangePasswordDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangePasswordRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChangePasswordRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChangePasswordView;

/**
 * Created by VietNH on 8/23/2017.
 */

public class ChangePasswordPresenterImpl implements IChangePasswordPresenter, ICallFinishedListener {
    public IChangePasswordView changePasswordView;
    public ChangePasswordDao changePasswordDao;

    public ChangePasswordPresenterImpl(IChangePasswordView changePasswordView) {
        this.changePasswordView = changePasswordView;
        this.changePasswordDao = new ChangePasswordDao();
    }

    @Override
    public void changePasswordPresenter(ChangePasswordRequest changePasswordRequest) {
        if (changePasswordView != null) {
            changePasswordView.showChangePasswordProgress();
            changePasswordDao.onSendChangePasswordDao(changePasswordRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        ChangePasswordRespone changePasswordRespone = (ChangePasswordRespone) object;
        if (changePasswordRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)
                && changePasswordRespone.getData().equals(Constants.CHANGE_PASSWORD_SUCCESS)) {
            changePasswordView.onSuccess();
        } else {
            changePasswordView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
        }
        changePasswordView.hideChangePasswordProgress();
    }

    @Override
    public void onCallError(Object object) {
        changePasswordView.hideChangePasswordProgress();
        changePasswordView.onError((APIError) object);
    }

}
