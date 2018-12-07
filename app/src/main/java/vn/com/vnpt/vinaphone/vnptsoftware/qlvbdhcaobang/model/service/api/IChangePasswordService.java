package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.PUT;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangePasswordRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChangePasswordRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 4/10/2017.
 */

public interface IChangePasswordService {
    @PUT(ServiceUrl.CHANGE_PASSWORD_URL)
    Call<ChangePasswordRespone> changePassword(@HeaderMap Map<String, String> headers, @Body ChangePasswordRequest changePasswordRequest);
}