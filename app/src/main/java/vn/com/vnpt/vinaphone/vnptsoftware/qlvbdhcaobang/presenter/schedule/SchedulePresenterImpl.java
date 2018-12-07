package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.schedule;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.schedule.ScheduleDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.BussinessInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MeetingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBossRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBussinessRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleMeetingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IScheduleDetailView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IScheduleView;

/**
 * Created by VietNH on 8/23/2017.
 */

public class SchedulePresenterImpl implements ISchedulePresenter, ICallFinishedListener {
    public IScheduleView scheduleView;
    public IScheduleDetailView scheduleDetailView;
    private ScheduleDao scheduleDao;

    public SchedulePresenterImpl(IScheduleView scheduleView) {
        this.scheduleView = scheduleView;
        this.scheduleDao = new ScheduleDao();
    }

    public SchedulePresenterImpl(IScheduleDetailView scheduleDetailView) {
        this.scheduleDetailView = scheduleDetailView;
        this.scheduleDao = new ScheduleDao();
    }

    @Override
    public void getWeekSchedules(ScheduleRequest scheduleRequest) {
        if (scheduleView != null) {
            scheduleView.showProgress();
            scheduleDao.onSendGetWeekSchedules(this, scheduleRequest);
        }
    }

    @Override
    public void getDetailMeeting(int scheduleId) {
        if (scheduleDetailView != null) {
            scheduleDao.onGetDetailMeeting(scheduleId, this);
        }
    }

    @Override
    public void getDetailBussiness(int scheduleId) {
        if (scheduleDetailView != null) {
            scheduleDao.onGetDetailBussiness(scheduleId, this);
        }
    }

    @Override
    public void getWeekSchedulesBoss(ScheduleBossRequest scheduleBossRequest) {
        if (scheduleView != null) {
            scheduleView.showProgress();
            scheduleDao.onSendGetWeekSchedulesBoss(this, scheduleBossRequest);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof ScheduleRespone) {
            scheduleView.hideProgress();
            ScheduleRespone scheduleRespone = (ScheduleRespone) object;
            if (scheduleRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleView.onSuccess(ConvertUtils.fromJSONList(scheduleRespone.getData(), ScheduleInfo.class));
            } else {
                scheduleView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ScheduleMeetingRespone) {
            ScheduleMeetingRespone scheduleMeetingRespone = (ScheduleMeetingRespone) object;
            if (scheduleMeetingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleDetailView.onSuccess(ConvertUtils.fromJSON(scheduleMeetingRespone.getData(), MeetingInfo.class));
            } else {
                scheduleDetailView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ScheduleBussinessRespone) {
            ScheduleBussinessRespone scheduleBussinessRespone = (ScheduleBussinessRespone) object;
            if (scheduleBussinessRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleDetailView.onSuccess(ConvertUtils.fromJSON(scheduleBussinessRespone.getData(), BussinessInfo.class));
            } else {
                scheduleDetailView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ScheduleBossRespone) {
            scheduleView.hideProgress();
            ScheduleBossRespone scheduleRespone = (ScheduleBossRespone) object;
            if (scheduleRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                scheduleView.onSuccessBoss(ConvertUtils.fromJSONList(scheduleRespone.getData(), ScheduleBossInfo.class));
            } else {
                scheduleView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (scheduleView != null) {
            scheduleView.hideProgress();
            scheduleView.onError((APIError) object);
        }
        if (scheduleDetailView != null) {
            scheduleDetailView.onError((APIError) object);
        }
    }

}
