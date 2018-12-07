package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.notify;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface INotifyDao {
    void onSendGetNotifysDao(ICallFinishedListener iCallFinishedListener);
    void onReadedNotifysDao(ICallFinishedListener iCallFinishedListener, ReadedNotifyRequest readedNotifyRequest);
    void onCheckStoreDocDao(ICallFinishedListener iCallFinishedListener, int docId);
    void onGetCountDocumentDao(ICallFinishedListener iCallFinishedListener);
}
