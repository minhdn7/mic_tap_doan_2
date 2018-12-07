package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.logout;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 4/18/2017.
 */

public interface ILogoutDao {
    void onSendLogoutDao(ICallFinishedListener iCallFinishedListener);
}
