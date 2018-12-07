package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.schedule;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBossRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBussinessRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleMeetingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IScheduleService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata.HandleSyncService;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ScheduleDao extends BaseDao implements IScheduleDao {
    private IScheduleService scheduleService;

    @Override
    public void onSendGetSchedulesDao(HandleSyncService.HandleGetSchedules handleGetSchedules, ScheduleRequest scheduleRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleRespone> call = scheduleService.getSchedules(BaseService.createAuthenHeaders(), scheduleRequest);
        call(call, handleGetSchedules);
    }

    @Override
    public void onSendGetWeekSchedules(ICallFinishedListener callFinishedListener, ScheduleRequest scheduleRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleRespone> call = scheduleService.getSchedules(BaseService.createAuthenHeaders(), scheduleRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetDetailMeeting(int scheduleId, ICallFinishedListener callFinishedListener) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleMeetingRespone> call = scheduleService.getDetailMeeting(BaseService.createAuthenHeaders(), scheduleId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetDetailBussiness(int scheduleId, ICallFinishedListener callFinishedListener) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleBussinessRespone> call = scheduleService.getDetailBussiness(BaseService.createAuthenHeaders(), scheduleId);
        call(call, callFinishedListener);
    }

    @Override
    public void onSendGetWeekSchedulesBoss(ICallFinishedListener callFinishedListener, ScheduleBossRequest scheduleBossRequest) {
        scheduleService = BaseService.createService(IScheduleService.class);
        Call<ScheduleBossRespone> call = scheduleService.getSchedulesBoss(BaseService.createAuthenHeaders(), scheduleBossRequest);
        call(call, callFinishedListener);
    }
}
