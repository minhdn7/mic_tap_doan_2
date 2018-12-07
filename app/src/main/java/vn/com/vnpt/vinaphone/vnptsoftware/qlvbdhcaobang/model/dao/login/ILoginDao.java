package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.login;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 4/18/2017.
 */

public interface ILoginDao {
    void onSendLoginDao(LoginRequest loginRequest, ICallFinishedListener iCallFinishedListener);
}
