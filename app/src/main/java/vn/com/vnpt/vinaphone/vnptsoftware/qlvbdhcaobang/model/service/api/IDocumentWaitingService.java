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
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendCommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckCommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckFinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ExchangeHandlesRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.GetFileAttachRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SendCommentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 4/10/2017.
 */

public interface IDocumentWaitingService {
    @POST(ServiceUrl.GET_COUNT_DOC_WAIT_URL)
    Call<CountDocumentWaitingRespone> getCount(@HeaderMap Map<String, String> headers, @Body DocumentWaitingRequest documentWaitingRequest);
    @POST(ServiceUrl.GET_DOC_WAIT_URL)
    Call<DocumentWaitingRespone> getAll(@HeaderMap Map<String, String> headers, @Body DocumentWaitingRequest documentWaitingRequest);
    @GET(ServiceUrl.VIEW_DIAGRAM_URL)
    Call<ResponseBody> getBitmapDiagram(@HeaderMap Map<String, String> headers, @Query("insid") String insId, @Query("defid") String defId);
    @GET(ServiceUrl.GET_DETAIL_DOCUMENT_WAITING_URL)
    Call<DetailDocumentWaitingRespone> getDetail(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.GET_LOGS_DOCUMENT_WAITING_URL)
    Call<LogRespone> getLogs(@HeaderMap Map<String, String> headers, @Path("id") int docId);

    @GET(ServiceUrl.GET_ATTACH_FILE_DOCUMENT_WAITING_URL)
    Call<AttachFileRespone> getAttachFiles(@HeaderMap Map<String, String> headers, @Path("id") int docId);

    @GET(ServiceUrl.GET_RELATED_DOCUMENT_WAITING_URL)
    Call<RelatedDocumentRespone> getRelatedDocs(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.GET_RELATED_FILE_WAITING_URL)
    Call<RelatedFileRespone> getRelatedFiles(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.CHECK_MARK_DOC_URL)
    Call<CheckMarkDocumentRespone> checkMark(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.MARK_DOC_URL)
    Call<MarkDocumentRespone> mark(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.CHECK_COMMENT_DOC_URL)
    Call<CheckCommentDocumentRespone> checkComment(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @POST(ServiceUrl.COMMENT_DOC_URL)
    Call<CommentDocumentRespone> comment(@HeaderMap Map<String, String> headers, @Body CommentDocumentRequest commentDocumentRequest);

//    @POST(ServiceUrl.SIGN_DOC_URL)
//    Call<SigningRespone> signDoc(@HeaderMap Map<String, String> headers, @Body SigningDocumentRequest signingDocumentRequest);
    @GET(ServiceUrl.SIGN_DOC_URL)
    Call<SigningRespone> signDoc(@HeaderMap Map<String, String> headers, @Path("docid") int docId);

    @GET(ServiceUrl.FINISH_DOC_URL)
    Call<FinishDocumentRespone> finish(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.CHECK_FINISH_DOC_URL)
    Call<CheckFinishDocumentRespone> checkFinish(@HeaderMap Map<String, String> headers, @Path("id") int docId);

    @GET(ServiceUrl.GET_LIST_COMMENT_URL)
    Call<ExchangeHandlesRespone> getlistcomment(@HeaderMap Map<String, String> headers, @Path("id") int docId,
                                                @Path("pageno") int pageno, @Path("pagesize") int pagesize);

    @POST(ServiceUrl.SEND_LIST_COMMENT_URL)
    Call<SendCommentRespone> sendlistcomment(@HeaderMap Map<String, String> headers, @Body SendCommentRequest sendCommentRequest);

    @GET(ServiceUrl.KY_VAN_BAN_URL)
    Call<SigningRespone> signdocument(@HeaderMap Map<String, String> headers, @Path("docid") int docId, @Path("fileid") int fileid);

}