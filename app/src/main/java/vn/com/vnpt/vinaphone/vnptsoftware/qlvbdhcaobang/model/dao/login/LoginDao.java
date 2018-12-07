package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.login;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.ILoginService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 4/18/2017.
 */

public class LoginDao extends BaseDao implements ILoginDao {
    private ILoginService loginService;

    @Override
    public void onSendLoginDao(LoginRequest loginRequest, ICallFinishedListener iCallFinishedListener) {
        loginService = BaseService.createService(ILoginService.class);
        Call<LoginRespone> call = loginService.getUserLogin(loginRequest);
        call(call, iCallFinishedListener);
    }
}
