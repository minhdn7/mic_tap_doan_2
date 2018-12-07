package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.changepassword;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangePasswordRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChangePasswordRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IChangePasswordService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/23/2017.
 */

public class ChangePasswordDao extends BaseDao implements IChangePasswordDao {
    private IChangePasswordService changePasswordService;

    @Override
    public void onSendChangePasswordDao(ChangePasswordRequest changePasswordRequest, ICallFinishedListener iCallFinishedListener) {
        changePasswordService = BaseService.createService(IChangePasswordService.class);
        Call<ChangePasswordRespone> call = changePasswordService.changePassword(BaseService.createAuthenHeaders(), changePasswordRequest);
        call(call, iCallFinishedListener);
    }
}
