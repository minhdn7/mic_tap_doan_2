package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.userinfo;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UserInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UserInfoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IUserAvatarView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IUserInfoView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IUserSwitchView;

/**
 * Created by VietNH on 8/24/2017.
 */

public class UserInfoPresenterImpl implements IUserInfoPresenter, ICallFinishedListener {
    public IUserInfoView userInfoView;
    public IUserAvatarView userAvatarView;
    public IUserSwitchView userSwitchView;
    public UserInfoDao userInfoDao;
    private int typeApi = -1;


    public UserInfoPresenterImpl(IUserInfoView userInfoView) {
        this.userInfoView = userInfoView;
        this.userInfoDao = new UserInfoDao();
    }

    public UserInfoPresenterImpl(IUserAvatarView userAvatarView) {
        typeApi = -1;
        this.userAvatarView = userAvatarView;
        this.userInfoDao = new UserInfoDao();
    }

    public UserInfoPresenterImpl(IUserAvatarView userAvatarView,IUserSwitchView userSwitchView) {
        typeApi = -1;
        this.userSwitchView = userSwitchView;
        this.userAvatarView = userAvatarView;
        this.userInfoDao = new UserInfoDao();
    }

    public UserInfoPresenterImpl(IUserInfoView userInfoView, IUserAvatarView userAvatarView) {
        typeApi = -1;
        this.userAvatarView = userAvatarView;
        this.userInfoView = userInfoView;
        this.userInfoDao = new UserInfoDao();
    }

    @Override
    public void getUserInfo() {
        typeApi = 1;
        if (userInfoView != null) {
            userInfoView.showProgress();
            userInfoDao.onGetUserInfoDao(this);
        }
    }

    @Override
    public void getUserInfo(String id) {
        typeApi = 1;
        if (userInfoView != null) {
            userInfoView.showProgress();
            userInfoDao.onGetUserInfoDao(this, id);
        }
    }

    @Override
    public void getAvatar(String userId) {
        typeApi = 2;
        if (userAvatarView != null) {
            userInfoDao.onGetAvatarDao(this, userId);
        }
    }

    @Override
    public void getListSwitchUser(String userId) {
        typeApi = 3;
        if (userSwitchView != null) {
            userSwitchView.showProgress();
            userInfoDao.onGetListSwitchUserDao(this, userId);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof UserInfoRespone) {
            UserInfoRespone userInfoRespone = (UserInfoRespone) object;
            if (userInfoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                userInfoView.onSuccessGetUserInfo(ConvertUtils.fromJSON(userInfoRespone.getData(), UserInfo.class));
            } else {
                userInfoView.onErrorGetUserInfo(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
            userInfoView.hideProgress();
        }
        if (object instanceof LoginRespone) {
            userSwitchView.hideProgress();
            LoginRespone loginRespone = (LoginRespone) object;
            if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                userSwitchView.onSuccessSwitchUser(loginInfo);
            } else {
                userSwitchView.onErrorSwitchUser(new APIError(0, "Thay đổi account thất bại!"));
            }
        }
        if (object instanceof ResponseBody) {
            ResponseBody responseBody = (ResponseBody) object;
            if (responseBody != null) {
                try {
                    userAvatarView.onSuccessAvatar(responseBody.bytes());
                } catch (Exception ex) {
                    userAvatarView.onErrorAvatar(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
                }
            } else {
                userAvatarView.onErrorAvatar(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        switch (typeApi) {
            case 1:
                if (userInfoView != null) {
                    userInfoView.hideProgress();
                    userInfoView.onErrorGetUserInfo((APIError) object);
                }
                break;
            case 2:
                if (userAvatarView != null) {
                    userAvatarView.onErrorAvatar(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
                }
                break;
                case 3:
                if (userSwitchView != null) {
                    userSwitchView.hideProgress();
                    userSwitchView.onErrorSwitchUser(new APIError(0, "Thay đổi account thất bại!"));
                }
                break;
            default:
                if (userInfoView != null) {
                    userInfoView.hideProgress();
                    userInfoView.onErrorGetUserInfo((APIError) object);
                }
                break;
        }

    }

}
