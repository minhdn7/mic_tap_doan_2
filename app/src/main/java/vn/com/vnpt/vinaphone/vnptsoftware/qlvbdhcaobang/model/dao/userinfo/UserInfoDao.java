package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.userinfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UserInfoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IUserInfoService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/24/2017.
 */

public class UserInfoDao extends BaseDao implements IUserInfoDao {
    private IUserInfoService userInfoService;

    @Override
    public void onGetUserInfoDao(ICallFinishedListener iCallFinishedListener) {
        userInfoService = BaseService.createService(IUserInfoService.class);
        Call<UserInfoRespone> call = userInfoService.getUserInfo(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetUserInfoDao(ICallFinishedListener iCallFinishedListener, String id) {
        userInfoService = BaseService.createService(IUserInfoService.class);
        Call<UserInfoRespone> call = userInfoService.getUserInfo(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetAvatarDao(ICallFinishedListener iCallFinishedListener, String id) {
        userInfoService = BaseService.createService(IUserInfoService.class);
        Call<ResponseBody> call = userInfoService.getAvatar(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetListSwitchUserDao(ICallFinishedListener iCallFinishedListener, String id) {
        userInfoService = BaseService.createService(IUserInfoService.class);
        Call<LoginRespone> call = userInfoService.getListSwitchUser(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }
}
