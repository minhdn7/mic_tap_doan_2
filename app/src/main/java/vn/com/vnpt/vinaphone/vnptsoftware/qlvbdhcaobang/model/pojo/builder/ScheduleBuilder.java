package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.builder;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Schedule;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleInfo;

/**
 * Created by VietNH on 9/23/2017.
 */

public class ScheduleBuilder {

    private Context context;

    public ScheduleBuilder(Context context) {
        this.context = context;
    }

    public List<Schedule> convertFromScheduleInfo(List<ScheduleInfo> scheduleInfos) {
        List<Schedule> schedules = new ArrayList<>();
        for (ScheduleInfo scheduleInfo : scheduleInfos) {
            Schedule schedule = new Schedule();
            schedule.setId(scheduleInfo.getId());
            schedule.setTitle(scheduleInfo.getTitle());
            schedule.setChuTri(scheduleInfo.getChuTri());
            schedule.setPosition(scheduleInfo.getPosition());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SCHEDULE_REALM);
                schedule.setStartTime(sdf.parse(scheduleInfo.getStartTime()));
                schedule.setEndTime(sdf.parse(scheduleInfo.getEndTime()));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            schedule.setType(scheduleInfo.getType());
            schedules.add(schedule);
        }
        return schedules.size() > 0 ? schedules : null;
    }

}
