package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.NotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReadedNotifyRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface INotifyService {
    @GET(ServiceUrl.GET_NOTIFY_URL)
    Call<NotifyRespone> getNotifys(@HeaderMap Map<String, String> headers);
    @POST(ServiceUrl.READED_NOTIFY_URL)
    Call<ReadedNotifyRespone> readedNotifys(@HeaderMap Map<String, String> headers, @Body ReadedNotifyRequest readedNotifyRequest);
    @GET(ServiceUrl.CHECK_STORE_DOC_URL)
    Call<CheckStoreDocRespone> checkStoreDoc(@HeaderMap Map<String, String> headers, @Path("id") int docId);
    @GET(ServiceUrl.GET_COUNT_COMMENT_URL)
    Call<CountDocumentRespone> getCountDocument(@HeaderMap Map<String, String> headers);
}