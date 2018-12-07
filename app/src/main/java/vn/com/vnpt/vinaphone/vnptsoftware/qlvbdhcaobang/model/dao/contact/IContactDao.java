package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.contact;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IContactDao {
    void onSendGetContactsDao(ICallFinishedListener iCallFinishedListener);
}
