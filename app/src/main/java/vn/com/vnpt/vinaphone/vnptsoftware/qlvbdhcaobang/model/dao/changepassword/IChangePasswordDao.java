package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.changepassword;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangePasswordRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/23/2017.
 */

public interface IChangePasswordDao {
    void onSendChangePasswordDao(ChangePasswordRequest changePasswordRequest, ICallFinishedListener iCallFinishedListener);
}
