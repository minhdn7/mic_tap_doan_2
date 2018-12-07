package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportWorkInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IReportView {
    void onSuccessDoc(ReportDocumentInfo object);
    void onSuccessWork(ReportWorkInfo object);
    void onError(APIError apiError);
}