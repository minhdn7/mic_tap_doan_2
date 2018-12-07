package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import android.net.wifi.aware.SubscribeConfig;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IDownloadService {
    @GET(ServiceUrl.DOWNLOAD_FILE_URL)
    Call<ResponseBody> download(@HeaderMap Map<String, String> headers, @Path("id") int docId);

    @GET(ServiceUrl.GET_FILE_URL_DOC)
    Call<GetViewFileObj> getUrlFileDoc(@HeaderMap Map<String, String> headers, @Path("id") int docId);

    @POST(ServiceUrl.DOWNLOAD_FILE_CHIDAO_URL)
    Call<ResponseBody> download(@HeaderMap Map<String, String> headers, @Body DownloadChiDaoRequest downloadChiDaoRequest);

}