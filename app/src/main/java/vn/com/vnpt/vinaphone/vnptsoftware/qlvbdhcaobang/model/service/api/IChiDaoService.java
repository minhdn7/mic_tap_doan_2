package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoGuiRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReplyChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChiDaoFlowRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DeleteChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FileChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonInGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonReceiveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReplyChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SaveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SavePersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SendChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IChiDaoService {
    @POST(ServiceUrl.GET_CHIDAO_NHAN_URL)
    Call<ChiDaoRespone> getChiDaoNhan(@HeaderMap Map<String, String> headers, @Body ChiDaoNhanRequest chiDaoNhanRequest);
    @POST(ServiceUrl.GET_CHIDAO_GUI_URL)
    Call<ChiDaoRespone> getChiDaoGui(@HeaderMap Map<String, String> headers, @Body ChiDaoGuiRequest chiDaoGuiRequest);
    @Multipart
    @POST(ServiceUrl.UPLOAD_FILE_URL)
    Call<UploadResponse> uploadFiles(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part[] files);
    @POST(ServiceUrl.CREATE_CHIDAO_URL)
    Call<SaveChiDaoRespone> createChiDao(@HeaderMap Map<String, String> headers, @Body ChiDaoRequest chiDaoRequest);
    @PUT(ServiceUrl.EDIT_CHIDAO_URL)
    Call<SaveChiDaoRespone> editChiDao(@HeaderMap Map<String, String> headers, @Body ChiDaoRequest chiDaoRequest);
    @POST(ServiceUrl.GET_PERSON_CHIDAO_URL)
    Call<PersonChiDaoRespone> getPersons(@HeaderMap Map<String, String> headers, @Body PersonChiDaoRequest personChiDaoRequest);
    @POST(ServiceUrl.SAVE_PERSON_CHIDAO_URL)
    Call<SavePersonChiDaoRespone> savePersons(@HeaderMap Map<String, String> headers, @Body SavePersonChiDaoRequest savePersonChiDaoRequest);
    @POST(ServiceUrl.GET_PERSON_RECEIVE_CHIDAO_URL)
    Call<PersonReceiveChiDaoRespone> getPersonsReceive(@HeaderMap Map<String, String> headers, @Body PersonReceiveChiDaoRequest personReceiveChiDaoRequest);
    @GET(ServiceUrl.GET_PERSON_GROUP_CHIDAO_URL)
    Call<PersonGroupChiDaoRespone> getPersonsGroup(@HeaderMap Map<String, String> headers);
    @POST(ServiceUrl.SEND_CHIDAO_URL)
    Call<SendChiDaoRespone> send(@HeaderMap Map<String, String> headers, @Body SendChiDaoRequest sendChiDaoRequest);
    @GET(ServiceUrl.GET_FLOW_CHIDAO_URL)
    Call<ChiDaoFlowRespone> getFlows(@HeaderMap Map<String, String> headers, @Path("id") String id);

    @GET(ServiceUrl.GET_WEBVIEW_FILE_CHIDAO_URL)
    Call<GetViewFileObj> getWebViewFile(@HeaderMap Map<String, String> headers, @Query("file") String id);
    @GET(ServiceUrl.GET_FILE_CHIDAO_URL)
    Call<FileChiDaoRespone> getFiles(@HeaderMap Map<String, String> headers, @Path("id") String id);
    @DELETE(ServiceUrl.GET_DELETE_CHIDAO_URL)
    Call<DeleteChiDaoRespone> delete(@HeaderMap Map<String, String> headers, @Path("id") String id);
    @GET(ServiceUrl.GET_DETAIL_CHIDAO_URL)
    Call<DetailChiDaoRespone> getDetail(@HeaderMap Map<String, String> headers, @Path("id") String id);
    @POST(ServiceUrl.GET_PERSON_IN_GROUP_CHIDAO_URL)
    Call<PersonInGroupChiDaoRespone> getPersonInGroup(@HeaderMap Map<String, String> headers, @Body PersonInGroupChiDaoRequest personInGroupChiDaoRequest);
    @POST(ServiceUrl.GET_PERSON_REPLY_CHIDAO_URL)
    Call<ReplyChiDaoRespone> getPersonReply(@HeaderMap Map<String, String> headers, @Body ReplyChiDaoRequest replyChiDaoRequest);
    @POST(ServiceUrl.GET_PERSON_RECEIVED_CHIDAO_URL)
    Call<PersonReceiveChiDaoRespone> getPersonsReceived(@HeaderMap Map<String, String> headers, @Body PersonReceiveChiDaoRequest personReceiveChiDaoRequest);
}