package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.report;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.report.ReportDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportWorkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportWorkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IReportView;

/**
 * Created by VietNH on 8/23/2017.
 */

public class ReportPresenterImpl implements IReportPresenter, ICallFinishedListener {
    public IReportView reportView;
    public ReportDao reportDao;

    public ReportPresenterImpl(IReportView reportView) {
        this.reportView = reportView;
        this.reportDao = new ReportDao();
    }

    @Override
    public void getReportDocument() {
        if (reportView != null) {
            reportDao.onGetReportDocumentDao(this);
        }
    }

    @Override
    public void getReportWork(int month) {
        if (reportView != null) {
            reportDao.onGetReportWorkDao(month, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof ReportDocumentRespone) {
            ReportDocumentRespone reportDocumentRespone = (ReportDocumentRespone) object;
            if (reportDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                reportView.onSuccessDoc(ConvertUtils.fromJSON(reportDocumentRespone.getData(), ReportDocumentInfo.class));
            } else {
                reportView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ReportWorkRespone) {
            ReportWorkRespone reportWorkRespone = (ReportWorkRespone) object;
            if (reportWorkRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                reportView.onSuccessWork(ConvertUtils.fromJSON(reportWorkRespone.getData(), ReportWorkInfo.class));
            } else {
                reportView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (reportView != null) {
            reportView.onError((APIError) object);
        }
    }

}
