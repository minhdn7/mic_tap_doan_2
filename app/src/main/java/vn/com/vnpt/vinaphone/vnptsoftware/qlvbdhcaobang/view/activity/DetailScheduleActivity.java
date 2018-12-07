package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.ThanhPhanAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.CountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.BussinessInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MeetingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify.INotifyPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify.NotifyPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.schedule.ISchedulePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.schedule.SchedulePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IReadedNotifyView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IScheduleDetailView;

public class DetailScheduleActivity extends BaseActivity implements IScheduleDetailView, ILoginView, IReadedNotifyView {

    @BindView(R.id.tv_title_label) TextView tv_title_label;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_chutri_label) TextView tv_chutri_label;
    @BindView(R.id.tv_chutri) TextView tv_chutri;
    @BindView(R.id.tv_thoigian) TextView tv_thoigian;
    @BindView(R.id.tv_todate_label) TextView tv_todate_label;
    @BindView(R.id.tv_todate) TextView tv_todate;
    @BindView(R.id.tv_fromdate) TextView tv_fromdate;
    @BindView(R.id.tv_fromdate_label) TextView tv_fromdate_label;
    @BindView(R.id.tv_diadiem_label) TextView tv_diadiem_label;
    @BindView(R.id.tv_diadiem) TextView tv_diadiem;
    @BindView(R.id.tv_note_label) TextView tv_note_label;
    @BindView(R.id.tv_note) TextView tv_note;
    @BindView(R.id.tv_thanhphan) TextView tv_thanhphan;
    @BindView(R.id.tv_thanhphan_tong) TextView tv_thanhphan_tong;
    @BindView(R.id.tv_nguoitao_label) TextView tv_nguoitao_label;
    @BindView(R.id.tv_nguoitao) TextView tv_nguoitao;
    @BindView(R.id.tv_content) TextView tv_content;
    @BindView(R.id.tv_content_label) TextView tv_content_label;
    @BindView(R.id.layout_schedule) LinearLayout layout_schedule;
    @BindView(R.id.divThanhPhan) View divThanhPhan;
    @BindView(R.id.layoutDisplay) LinearLayout layoutDisplay;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ISchedulePresenter schedulePresenter = new SchedulePresenterImpl(this);
    private WeekViewEvent event;
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;
    private INotifyPresenter notifyPresenter = new NotifyPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schedule);
        init();
        getDetail();
        String stepPre = EventBus.getDefault().getStickyEvent(StepPre.class).getCall();
        if (stepPre.equals(Constants.SCHEDULE_NOTIFY)) {
            readedNotify();
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView actionBarTitleView = (TextView) toolbar.findViewById(R.id.title);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        if (event.getType().equals(Constants.SCHEDULE_BUSSINESS)) {
            actionBarTitleView.setText(getString(R.string.DETAIL_SCHEDULE_BUSSINESS_TITLE));
        }
        if (event.getType().equals(Constants.SCHEDULE_MEETING)) {
            actionBarTitleView.setText(getString(R.string.DETAIL_SCHEDULE_TITLE));
        }
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stepPre = EventBus.getDefault().getStickyEvent(StepPre.class).getCall();
                if (stepPre.equals(Constants.SCHEDULE_LIST)) {
                    onBackPressed();
                }
                if (stepPre.equals(Constants.SCHEDULE_NOTIFY)) {
                    startActivity(new Intent(DetailScheduleActivity.this, NotifyActivity.class));
                    finish();
                }
            }
        });
    }

    private void init() {
        tv_title.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_title_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_chutri_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_chutri.setTypeface(Application.getApp().getTypeface());
        tv_thoigian.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_fromdate_label.setTypeface(Application.getApp().getTypeface(), Typeface.ITALIC);
        tv_todate_label.setTypeface(Application.getApp().getTypeface(), Typeface.ITALIC);
        tv_fromdate.setTypeface(Application.getApp().getTypeface(), Typeface.ITALIC);
        tv_todate.setTypeface(Application.getApp().getTypeface(), Typeface.ITALIC);
        tv_diadiem_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_diadiem.setTypeface(Application.getApp().getTypeface());
        tv_note_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_note.setTypeface(Application.getApp().getTypeface());
        tv_thanhphan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_thanhphan_tong.setTypeface(Application.getApp().getTypeface());
        tv_nguoitao_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD_ITALIC);
        tv_nguoitao.setTypeface(Application.getApp().getTypeface(), Typeface.ITALIC);
        tv_content_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_content.setTypeface(Application.getApp().getTypeface());
        event = EventBus.getDefault().getStickyEvent(WeekViewEvent.class);
        connectionDetector = new ConnectionDetector(this);
        setupToolbar();
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            if (event.getType().equals(Constants.SCHEDULE_MEETING)) {
                schedulePresenter.getDetailMeeting((int) event.getId());
            }
            if (event.getType().equals(Constants.SCHEDULE_BUSSINESS)) {
                schedulePresenter.getDetailBussiness((int) event.getId());
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void readedNotify() {
        if (connectionDetector.isConnectingToInternet()) {
            notifyPresenter.readedNotifys(new ReadedNotifyRequest(EventBus.getDefault().getStickyEvent(DetailNotifyEvent.class).getIdNotify()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccess(Object schedule) {
        if (schedule instanceof MeetingInfo) {
            MeetingInfo meetingInfo = (MeetingInfo) schedule;
            tv_title.setText(meetingInfo.getTitle());
            tv_chutri.setText(meetingInfo.getChuTri());
            tv_fromdate.setText(meetingInfo.getTimeStart());
            if (meetingInfo.getEndStart() != null && !meetingInfo.getEndStart().trim().equals("") && !meetingInfo.getEndStart().contains(" ")
                    && meetingInfo.getTimeStart() != null && !meetingInfo.getTimeStart().trim().equals("")) {
                String[] str = meetingInfo.getTimeStart().split(" ");
                tv_fromdate.setText(str[1] + " " + str[0]);
                tv_todate.setText(meetingInfo.getEndStart() + " " + str[0]);
            } else {
                tv_todate.setText(meetingInfo.getEndStart());
            }
            tv_diadiem.setText(meetingInfo.getTenPhongHop());
            tv_note.setText(meetingInfo.getNote());
            tv_content.setText(meetingInfo.getContent());
            tv_nguoitao.setText(meetingInfo.getFullName());
            int soNguoi = Integer.parseInt(meetingInfo.getSoNguoi());
            tv_thanhphan_tong.setText(" (" + soNguoi + ")");
            if (meetingInfo.getThanhPhan() != null && !meetingInfo.getThanhPhan().equals("")) {
                String[] arrThanhPhan = meetingInfo.getThanhPhan().split(",");
                ThanhPhanAdapter thanhPhanAdapter = new ThanhPhanAdapter(this, R.layout.item_schedule_detail, arrThanhPhan);
                int adapterCount = thanhPhanAdapter.getCount();
                for (int i = 0; i < adapterCount; i++) {
                    View item = thanhPhanAdapter.getView(i, null, null);
                    layout_schedule.addView(item);
                }
            }
        }
        if (schedule instanceof BussinessInfo) {
            BussinessInfo bussinessInfo = (BussinessInfo) schedule;
            tv_thanhphan_tong.setText("");
            tv_thanhphan.setText("");
            divThanhPhan.setVisibility(View.GONE);
            tv_title.setText(bussinessInfo.getTitle());
            tv_chutri.setText(bussinessInfo.getThanhPhan());
            if (bussinessInfo.getStartTime() != null && !bussinessInfo.getStartTime().trim().equals("") && bussinessInfo.getStartTime().trim().contains(" ")) {
                String[] timeStr = bussinessInfo.getStartTime().trim().split(" ");
                tv_fromdate.setText(timeStr[1] + " " + timeStr[0]);
            } else {
                tv_fromdate.setText("");
            }
            if (bussinessInfo.getEndTime() != null && !bussinessInfo.getEndTime().trim().equals("") && bussinessInfo.getEndTime().trim().contains(" ")) {
                String[] timeStr = bussinessInfo.getEndTime().trim().split(" ");
                tv_todate.setText(timeStr[1] + " " + timeStr[0]);
            } else {
                tv_todate.setText("");
            }
            tv_diadiem.setText(bussinessInfo.getPosition());
            tv_note.setText(bussinessInfo.getNote());
            tv_content.setText(bussinessInfo.getContent());
            tv_nguoitao.setText(bussinessInfo.getFullName());
        }
        layoutDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(boolean isRead) {

    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onCheckStoreSuccess(CheckStoreDocInfo data) {

    }

    @Override
    public void onCountDocumentSuccess(CountDocument data) {

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            if (event.getType().equals(Constants.SCHEDULE_MEETING)) {
                schedulePresenter.getDetailMeeting((int) event.getId());
            }
            if (event.getType().equals(Constants.SCHEDULE_BUSSINESS)) {
                schedulePresenter.getDetailBussiness((int) event.getId());
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        if (DetailScheduleActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.PLEASE_WAIT));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }

        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (!DetailScheduleActivity.this.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
     @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }

}
