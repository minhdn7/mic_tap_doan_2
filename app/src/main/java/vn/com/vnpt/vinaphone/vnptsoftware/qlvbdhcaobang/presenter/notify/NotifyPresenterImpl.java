package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.notify.NotifyDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.CountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.NotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.NotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReadedNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.INotifyView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IReadedNotifyView;

/**
 * Created by VietNH on 8/29/2017.
 */

public class NotifyPresenterImpl implements INotifyPresenter, ICallFinishedListener {
    public INotifyView notifyView;
    public IReadedNotifyView readedNotifyView;
    public NotifyDao notifyDao;

    public NotifyPresenterImpl(INotifyView notifyView) {
        this.notifyView = notifyView;
        this.notifyDao = new NotifyDao();
    }

    public NotifyPresenterImpl(IReadedNotifyView readedNotifyView) {
        this.readedNotifyView = readedNotifyView;
        this.notifyDao = new NotifyDao();
    }

    public NotifyPresenterImpl(INotifyView notifyView, IReadedNotifyView readedNotifyView) {
        this.notifyView = notifyView;
        this.readedNotifyView = readedNotifyView;
        this.notifyDao = new NotifyDao();
    }

    @Override
    public void getNotifys() {
        if (notifyView != null) {
            notifyView.showProgress();
            notifyDao.onSendGetNotifysDao(this);
        }
    }

    @Override
    public void readedNotifys(ReadedNotifyRequest readedNotifyRequest) {
        if (readedNotifyView != null) {
            notifyDao.onReadedNotifysDao(this, readedNotifyRequest);
        }
    }

    @Override
    public void checkStoreDoc(int docId) {
        if (readedNotifyView != null) {
            notifyDao.onCheckStoreDocDao(this, docId);
        }
    }

    @Override
    public void getCountDocument() {
        if (notifyView != null) {
//            notifyView.showProgress();
            notifyDao.onGetCountDocumentDao(this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof NotifyRespone) {
            notifyView.hideProgress();
            NotifyRespone notifyRespone = (NotifyRespone) object;
            if (notifyRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                notifyView.onSuccess(ConvertUtils.fromJSONList(notifyRespone.getData(), NotifyInfo.class));
            } else {
                notifyView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ReadedNotifyRespone) {
            ReadedNotifyRespone readedNotifyRespone = (ReadedNotifyRespone) object;
            if (readedNotifyRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                readedNotifyView.onSuccess(readedNotifyRespone.getData().toUpperCase().equals(Constants.IS_READ));
            } else {
                readedNotifyView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof CheckStoreDocRespone) {
            CheckStoreDocRespone checkStoreDocRespone = (CheckStoreDocRespone) object;
            if (checkStoreDocRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                readedNotifyView.onCheckStoreSuccess(ConvertUtils.fromJSON(checkStoreDocRespone.getData(), CheckStoreDocInfo.class));
            } else {
                readedNotifyView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof CountDocumentRespone) {
            CountDocumentRespone countDocumentRespone = (CountDocumentRespone) object;
            if (countDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
//                readedNotifyView.onCountDocumentSuccess(ConvertUtils.fromJSON(countDocumentRespone.getData(), CountDocument.class));
            } else {
                readedNotifyView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (notifyView != null) {
            notifyView.hideProgress();
            notifyView.onError((APIError) object);
        }
        if (readedNotifyView != null) {
            readedNotifyView.onError((APIError) object);
        }
    }

}
