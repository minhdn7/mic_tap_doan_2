package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.notify;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.NotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReadedNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.INotifyService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public class NotifyDao extends BaseDao implements INotifyDao {
    private INotifyService notifyService;

    @Override
    public void onSendGetNotifysDao(ICallFinishedListener iCallFinishedListener) {
        notifyService = BaseService.createService(INotifyService.class);
        Call<NotifyRespone> call = notifyService.getNotifys(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onReadedNotifysDao(ICallFinishedListener iCallFinishedListener, ReadedNotifyRequest readedNotifyRequest) {
        notifyService = BaseService.createService(INotifyService.class);
        Call<ReadedNotifyRespone> call = notifyService.readedNotifys(BaseService.createAuthenHeaders(), readedNotifyRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onCheckStoreDocDao(ICallFinishedListener iCallFinishedListener, int docId) {
        notifyService = BaseService.createService(INotifyService.class);
        Call<CheckStoreDocRespone> call = notifyService.checkStoreDoc(BaseService.createAuthenHeaders(), docId);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetCountDocumentDao(ICallFinishedListener iCallFinishedListener) {
        notifyService = BaseService.createService(INotifyService.class);
        Call<CountDocumentRespone> call = notifyService.getCountDocument(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }
}
