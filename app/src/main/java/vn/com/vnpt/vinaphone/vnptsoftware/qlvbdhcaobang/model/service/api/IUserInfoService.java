package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UserInfoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 4/10/2017.
 */

public interface IUserInfoService {
    @GET(ServiceUrl.GET_USER_INFO_URL)
    Call<UserInfoRespone> getUserInfo(@HeaderMap Map<String, String> headers);
    @GET(ServiceUrl.GET_CONTACT_INFO_URL)
    Call<UserInfoRespone> getUserInfo(@HeaderMap Map<String, String> headers, @Path("userid") String id);
    @GET(ServiceUrl.GET_AVATAR_URL)
    Call<ResponseBody> getAvatar(@HeaderMap Map<String, String> headers, @Path("userid") String id);

    @GET(ServiceUrl.GET_LIST_SWITCH_USER_URL)
    Call<LoginRespone> getListSwitchUser(@HeaderMap Map<String, String> headers, @Path("userid") String id);
}