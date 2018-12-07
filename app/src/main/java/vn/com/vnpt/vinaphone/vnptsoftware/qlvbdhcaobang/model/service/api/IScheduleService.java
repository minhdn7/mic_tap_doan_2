package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBossRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBussinessRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleMeetingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IScheduleService {
    @POST(ServiceUrl.GET_SCHEDULES_URL)
    Call<ScheduleRespone> getSchedules(@HeaderMap Map<String, String> headers, @Body ScheduleRequest scheduleRequest);
    @GET(ServiceUrl.GET_DETAIL_SCHEDULE_MEETING_URL)
    Call<ScheduleMeetingRespone> getDetailMeeting(@HeaderMap Map<String, String> headers, @Path("id") int scheduleId);
    @GET(ServiceUrl.GET_DETAIL_SCHEDULE_BUSSINESS_URL)
    Call<ScheduleBussinessRespone> getDetailBussiness(@HeaderMap Map<String, String> headers, @Path("id") int scheduleId);
    @POST(ServiceUrl.GET_SCHEDULES_BOSS_URL)
    Call<ScheduleBossRespone> getSchedulesBoss(@HeaderMap Map<String, String> headers, @Body ScheduleBossRequest scheduleBossRequest);
}