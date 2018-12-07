package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.CountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.NumberCountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.JobPositionRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LienThongRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChangeDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonListRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 4/10/2017.
 */

public interface IChangeDocumentService {
    @POST(ServiceUrl.GET_LIST_TYPE_CHANGE_DOC_URL)
    Call<TypeChangeDocumentRespone> getTypeChange(@HeaderMap Map<String, String> headers, @Body TypeChangeDocRequest typeChangeDocumentRequest);
    @POST(ServiceUrl.GET_PERSONS_PROCESS_URL)
    Call<PersonListRespone> getPersonProcess(@HeaderMap Map<String, String> headers, @Body ListProcessRequest listProcessRequest);
    @POST(ServiceUrl.GET_PERSONS_SEND_URL)
    Call<PersonListRespone> getPersonSend(@HeaderMap Map<String, String> headers, @Body SearchPersonRequest searchPersonRequest);
    @GET(ServiceUrl.GET_PERSONS_NOTIFY_URL)
    Call<PersonListRespone> getPersonNotify(@HeaderMap Map<String, String> headers);
    @POST(ServiceUrl.CHANGE_DOC_SEND_URL)
    Call<ChangeDocumentRespone> changeSend(@HeaderMap Map<String, String> headers, @Body ChangeDocumentSendRequest changeDocumentSendRequest);
    @POST(ServiceUrl.CHANGE_DOC_DIRECT_URL)
    Call<ChangeDocumentRespone> changeDirect(@HeaderMap Map<String, String> headers, @Body ChangeDocumentDirectRequest changeDocumentDirectRequest);
    @POST(ServiceUrl.CHANGE_DOC_RECEIVE_URL)
    Call<ChangeDocumentRespone> changeReceive(@HeaderMap Map<String, String> headers, @Body ChangeDocumentReceiveRequest changeDocumentReceiveRequest);
    @POST(ServiceUrl.CHANGE_DOC_PROCESS_URL)
    Call<ChangeDocumentRespone> changeProcess(@HeaderMap Map<String, String> headers, @Body ChangeProcessRequest changeProcessRequest);
    @POST(ServiceUrl.CHANGE_DOC_NOTIFY_URL)
    Call<ChangeDocumentRespone> changeNotify(@HeaderMap Map<String, String> headers, @Body ChangeNotifyRequest changeNotifyRequest);
    @GET(ServiceUrl.GET_JOB_POSSITION_URL)
    Call<JobPositionRespone> getJobPossitions(@HeaderMap Map<String, String> headers);
    @GET(ServiceUrl.GET_UNIT_URL)
    Call<UnitRespone> getUnits(@HeaderMap Map<String, String> headers);
    @GET(ServiceUrl.GET_LIEN_THONG_XL_URL)
    Call<LienThongRespone> getLienThongXL(@HeaderMap Map<String, String> headers);
    @GET(ServiceUrl.GET_LIEN_THONG_BH_URL)
    Call<LienThongRespone> getLienThongBH(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @POST(ServiceUrl.CHANGE_DOC_NOTIFY_XEMDB_URL)
    Call<ChangeDocumentRespone> changeNotify(@HeaderMap Map<String, String> headers, @Body ChangeDocumentNotifyRequest changeDocumentNotifyRequest);
    @GET(ServiceUrl.GET_GROUP_PERSON_CN_URL)
    Call<PersonGroupChangeDocRespone> getGroupPersonCN(@HeaderMap Map<String, String> headers);
    @GET(ServiceUrl.GET_GROUP_PERSON_DV_URL)
    Call<PersonGroupChangeDocRespone> getGroupPersonDV(@HeaderMap Map<String, String> headers);
    @POST(ServiceUrl.GET_PERSONS_SEND_PROCESS_URL)
    Call<PersonListRespone> getPersonSendProcess(@HeaderMap Map<String, String> headers, @Body SearchPersonRequest searchPersonRequest);
    @GET(ServiceUrl.GET_UNIT_CLERK_URL)
    Call<UnitRespone> getUnitClerks(@HeaderMap Map<String, String> headers);

    @GET(ServiceUrl.GET_COUNT_DOCUMENT)
    Call<NumberCountDocument> getCountDocument(@HeaderMap Map<String, String> headers);
}