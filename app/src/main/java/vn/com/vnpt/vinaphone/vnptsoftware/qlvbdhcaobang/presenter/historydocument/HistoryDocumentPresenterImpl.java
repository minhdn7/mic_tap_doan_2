package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.historydocument;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentwaiting.DocumentWaitingDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IHistoryDocumentView;

/**
 * Created by VietNH on 9/12/2017.
 */

public class HistoryDocumentPresenterImpl implements IHistoryDocumentPresenter, ICallFinishedListener {
    public IHistoryDocumentView historyDocumentView;
    public DocumentWaitingDao documentWaitingDao;

    public HistoryDocumentPresenterImpl(IHistoryDocumentView historyDocumentView) {
        this.historyDocumentView = historyDocumentView;
        this.documentWaitingDao = new DocumentWaitingDao();
    }

    @Override
    public void getLogs(int id) {
        if (historyDocumentView != null) {
            documentWaitingDao.onGetLogs(id, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof LogRespone) {
            historyDocumentView.hideProgress();
            LogRespone logRespone = (LogRespone) object;
            if (logRespone != null && logRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                historyDocumentView.onSuccess(ConvertUtils.fromJSONList(logRespone.getData(), UnitLogInfo.class));
            } else {
                historyDocumentView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        historyDocumentView.hideProgress();
        historyDocumentView.onError((APIError) object);
    }

}
