package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportWorkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 4/10/2017.
 */

public interface IReportService {
    @GET(ServiceUrl.GET_REPORT_DOCUMENT_URL)
    Call<ReportDocumentRespone> getReportDocument(@HeaderMap Map<String, String> headers);
    @GET(ServiceUrl.GET_REPORT_WORK_URL)
    Call<ReportWorkRespone> getReportWork(@HeaderMap Map<String, String> headers, @Path("month") int month);
}