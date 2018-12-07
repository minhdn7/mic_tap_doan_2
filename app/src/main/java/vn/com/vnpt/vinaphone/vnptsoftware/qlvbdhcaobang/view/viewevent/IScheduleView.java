package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IScheduleView {
    void onSuccess(List<ScheduleInfo> schedules);
    void onSuccessBoss(List<ScheduleBossInfo> schedules);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}