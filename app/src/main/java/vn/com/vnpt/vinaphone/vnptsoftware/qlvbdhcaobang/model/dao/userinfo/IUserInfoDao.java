package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.userinfo;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/24/2017.
 */

public interface IUserInfoDao {
    void onGetUserInfoDao(ICallFinishedListener iCallFinishedListener);
    void onGetUserInfoDao(ICallFinishedListener iCallFinishedListener, String id);
    void onGetAvatarDao(ICallFinishedListener iCallFinishedListener, String id);
    void onGetListSwitchUserDao(ICallFinishedListener iCallFinishedListener, String id);
}
