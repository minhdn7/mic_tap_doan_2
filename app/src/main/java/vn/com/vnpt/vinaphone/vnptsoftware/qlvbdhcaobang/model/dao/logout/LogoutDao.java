package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.logout;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogoutRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.ILogoutService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 4/18/2017.
 */

public class LogoutDao extends BaseDao implements ILogoutDao {
    private ILogoutService logoutService;

    @Override
    public void onSendLogoutDao(ICallFinishedListener iCallFinishedListener) {
        logoutService = BaseService.createService(ILogoutService.class);
        Call<LogoutRespone> call = logoutService.logout(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }
}
