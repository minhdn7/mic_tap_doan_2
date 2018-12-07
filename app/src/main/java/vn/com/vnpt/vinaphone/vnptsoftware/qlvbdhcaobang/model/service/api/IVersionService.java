package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckVersionResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IVersionService {
    @POST(ServiceUrl.CHECK_VERSION_URL)
    Call<CheckVersionResponse> checkVersion(@HeaderMap Map<String, String> headers, @Body CheckVersionRequest checkVersionRequest);
}