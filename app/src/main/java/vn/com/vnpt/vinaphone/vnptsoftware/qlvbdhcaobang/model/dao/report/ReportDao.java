package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.report;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportWorkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IReportService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/23/2017.
 */

public class ReportDao extends BaseDao implements IReportDao {

    private IReportService reportService;

    @Override
    public void onGetReportDocumentDao(ICallFinishedListener iCallFinishedListener) {
        reportService = BaseService.createService(IReportService.class);
        Call<ReportDocumentRespone> call = reportService.getReportDocument(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetReportWorkDao(int month, ICallFinishedListener iCallFinishedListener) {
        reportService = BaseService.createService(IReportService.class);
        Call<ReportWorkRespone> call = reportService.getReportWork(BaseService.createAuthenHeaders(), month);
        call(call, iCallFinishedListener);
    }

}
