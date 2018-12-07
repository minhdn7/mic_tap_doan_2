package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface INotifyPresenter {
    void getNotifys();
    void readedNotifys(ReadedNotifyRequest readedNotifyRequest);
    void checkStoreDoc(int docId);
    void getCountDocument();
}
