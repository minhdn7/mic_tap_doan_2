package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.schedule;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleRequest;

/**
 * Created by VietNH on 8/23/2017.
 */

public interface ISchedulePresenter {
    void getWeekSchedules(ScheduleRequest scheduleRequest);
    void getDetailMeeting(int scheduleId);
    void getDetailBussiness(int scheduleId);
    void getWeekSchedulesBoss(ScheduleBossRequest scheduleBossRequest);
}
