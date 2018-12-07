package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentProcessedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckDocProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckRecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 4/10/2017.
 */

public interface IDocumentProcessedService {
    @POST(ServiceUrl.GET_COUNT_DOC_PROCECSSED_URL)
    Call<CountDocumentProcessedRespone> getCount(@HeaderMap Map<String, String> headers, @Body DocumentProcessedRequest documentProcessedRequest);
    @POST(ServiceUrl.GET_DOC_PROCECSSED_URL)
    Call<DocumentProcessedRespone> getRecords(@HeaderMap Map<String, String> headers, @Body DocumentProcessedRequest documentProcessedRequest);
    @GET(ServiceUrl.CHECK_RECOVER_DOC_RECEIVE_URL)
    Call<CheckRecoverDocumentRespone> checkReceive(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.CHECK_RECOVER_DOC_SEND_URL)
    Call<CheckRecoverDocumentRespone> checkSend(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.RECOVER_DOC_RECEIVE_URL)
    Call<RecoverDocumentRespone> recoverDocumentReceive(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.RECOVER_DOC_SEND_URL)
    Call<RecoverDocumentRespone> recoverDocumentSend(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.GET_DETAIL_DOCUMENT_PROCESSED_URL)
    Call<DetailDocumentProcessedRespone> getDetail(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.GET_LOGS_DOCUMENT_PROCESSED_URL)
    Call<LogRespone> getLogs(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.GET_ATTACH_FILE_DOCUMENT_PROCESSED_URL)
    Call<AttachFileRespone> getAttachFiles(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_DOCUMENT_PROCESSED_URL)
    Call<RelatedDocumentRespone> getRelatedDocs(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_FILE_PROCESSED_URL)
    Call<RelatedFileRespone> getRelatedFiles(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.CHECK_MARK_DOC_URL)
    Call<CheckMarkDocumentRespone> checkMark(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.MARK_DOC_URL)
    Call<MarkDocumentRespone> mark(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.VIEW_DIAGRAM_URL)
    Call<ResponseBody> getBitmapDiagram(@HeaderMap Map<String, String> headers, @Query("insid") String insId, @Query("defid") String defId);
    @GET(ServiceUrl.CHECK_CHANGE_DOC_PROCECSSED_URL)
    Call<CheckDocProcessedRespone> checkChangeDoc(@HeaderMap Map<String, String> headers, @Path("id") int docId);
}