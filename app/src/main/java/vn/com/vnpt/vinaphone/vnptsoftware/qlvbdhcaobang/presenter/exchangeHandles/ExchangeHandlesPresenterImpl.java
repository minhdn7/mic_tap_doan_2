package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.exchangeHandles;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.exchangeHandle.ExchangeHandleDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendCommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ExchangeHandlesInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ExchangeHandlesRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SendCommentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IExchangeHandlesView;

/**
 * Created by VietNH on 9/12/2017.
 */

public class ExchangeHandlesPresenterImpl implements IExchangeHandlesPresenter, ICallFinishedListener {
    public IExchangeHandlesView exchnageHandlesView;
    public ExchangeHandleDao exchangeHandleDao;

    public ExchangeHandlesPresenterImpl(IExchangeHandlesView exchnageHandlesView) {
        this.exchnageHandlesView = exchnageHandlesView;
        this.exchangeHandleDao = new ExchangeHandleDao();
    }


    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof ExchangeHandlesRespone) {
            exchnageHandlesView.hideProgress();
            ExchangeHandlesRespone exchangeHandlesRespone = (ExchangeHandlesRespone) object;
            if (exchangeHandlesRespone != null && exchangeHandlesRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                exchnageHandlesView.onSuccessRecords((exchangeHandlesRespone) );
            } else {
                exchnageHandlesView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }

        if (object instanceof SendCommentRespone) {
            exchnageHandlesView.hideProgress();
            SendCommentRespone sendCommentRespone = (SendCommentRespone) object;
            if (sendCommentRespone != null && sendCommentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                exchnageHandlesView.onSendSuccess(sendCommentRespone);
            } else {
                exchnageHandlesView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }

    }


    @Override
    public void onCallError(Object object) {
        if (exchnageHandlesView != null) {
            exchnageHandlesView.hideProgress();
            exchnageHandlesView.onError((APIError) object);
        }
    }

    @Override
    public void getExchangeHandles(int id, int page, int number) {
        if (exchnageHandlesView != null) {
            exchangeHandleDao.onGetExchangeHandles(id, page, number, this);
        }
    }

    @Override
    public void sendComment(SendCommentRequest sendCommentRequest) {
        if (exchnageHandlesView != null) {
            exchangeHandleDao.onSendExchangeHandles(sendCommentRequest, this);
        }
    }
}
