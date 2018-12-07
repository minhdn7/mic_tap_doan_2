package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.userinfo;

/**
 * Created by VietNH on 8/24/2017.
 */

public interface IUserInfoPresenter {
    void getUserInfo();
    void getUserInfo(String id);
    void getAvatar(String userId);
    void getListSwitchUser(String userId);
}
