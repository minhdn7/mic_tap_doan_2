package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.schedule;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata.HandleSyncService;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IScheduleDao {
    void onSendGetSchedulesDao(HandleSyncService.HandleGetSchedules handleGetSchedules, ScheduleRequest scheduleRequest);
    void onSendGetWeekSchedules(ICallFinishedListener callFinishedListener, ScheduleRequest scheduleRequest);
    void onGetDetailMeeting(int scheduleId, ICallFinishedListener callFinishedListener);
    void onGetDetailBussiness(int scheduleId, ICallFinishedListener callFinishedListener);
    void onSendGetWeekSchedulesBoss(ICallFinishedListener callFinishedListener, ScheduleBossRequest scheduleBossRequest);
}
