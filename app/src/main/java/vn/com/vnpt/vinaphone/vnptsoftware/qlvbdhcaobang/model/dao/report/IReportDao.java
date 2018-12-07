package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.report;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/23/2017.
 */

public interface IReportDao {
    void onGetReportDocumentDao(ICallFinishedListener iCallFinishedListener);
    void onGetReportWorkDao(int month, ICallFinishedListener iCallFinishedListener);
}
