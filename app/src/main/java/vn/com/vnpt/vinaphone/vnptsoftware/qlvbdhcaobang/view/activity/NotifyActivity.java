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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.NotifyAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.CountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Event;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.NotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify.INotifyPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify.NotifyPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.NotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.Notify;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.INotifyView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IReadedNotifyView;

public class NotifyActivity extends BaseActivity implements INotifyView, IReadedNotifyView, ILoginView {

    private INotifyPresenter notifyPresenter = new NotifyPresenterImpl(this, this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    @BindView(R.id.tickAll)
    TextView tickAll;
    @BindView(R.id.tickReaded)
    TextView tickReaded;
    @BindView(R.id.layout_notify)
    LinearLayout layout_notify;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.layoutHeader)
    LinearLayout layoutHeader;
    private boolean tickedAll;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private List<NotifyInfo> notifyInfoList;
    private int step;
    private ProgressDialog progressDialog;
    private String idDoc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        init();
    }

    private void init() {
        setupToolbar();
        tickAll.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tickReaded.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        EventBus.getDefault().postSticky(new NotifyEvent(new ArrayList<Notify>()));
        tickedAll = false;
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_notify);
        setSupportActionBar(toolbar);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(NotifyActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @OnClick({R.id.tickReaded, R.id.tickAll})
    public void tickEvent(View view) {
        switch (view.getId()) {
            case R.id.tickAll:
                layout_notify.removeAllViewsInLayout();
                NotifyAdapter notifyAdapter = null;
                if (notifyInfoList != null && notifyInfoList.size() > 0) {
                    NotifyEvent notifyEvent = EventBus.getDefault().getStickyEvent(NotifyEvent.class);
                    if (tickedAll) {
                        tickedAll = false;
                        notifyAdapter = new NotifyAdapter(this, R.layout.item_notify, notifyInfoList, false);
                        notifyEvent.setNotifyList(new ArrayList<Notify>());
                    } else {
                        tickedAll = true;
                        notifyAdapter = new NotifyAdapter(this, R.layout.item_notify, notifyInfoList, true);
                        List<Notify> notifyList = new ArrayList<>();
                        for (NotifyInfo notifyInfo : notifyInfoList) {
                            Notify notify = new Notify(notifyInfo.getId());
                            notifyList.add(notify);
                        }
                        notifyEvent.setNotifyList(notifyList);
                    }
                    EventBus.getDefault().postSticky(notifyEvent);
                    int adapterCount = notifyAdapter.getCount();
                    for (int i = 0; i < adapterCount; i++) {
                        View item = notifyAdapter.getView(i, null, null);
                        layout_notify.addView(item);
                    }
                }
                break;
            case R.id.tickReaded:
                readedNotify();
                break;
        }
    }

    private void readedNotify() {
        NotifyEvent notifyEvent = EventBus.getDefault().getStickyEvent(NotifyEvent.class);
        List<Notify> notifyList = notifyEvent.getNotifyList();
        String notifyIdStr = "";
        for (int i = 0; i < notifyList.size(); i++) {
            if (i < notifyList.size() - 1) {
                notifyIdStr += notifyList.get(i).getId() + ",";
            } else {
                notifyIdStr += notifyList.get(i).getId();
            }
        }
        if (connectionDetector.isConnectingToInternet()) {
            if (notifyIdStr.equals("")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_READED_ERROR), getString(R.string.UNCHECK_READED), true, AlertDialogManager.ERROR);
            } else {
                step = 2;
                notifyPresenter.readedNotifys(new ReadedNotifyRequest(notifyIdStr));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getNotifys() {
        //restart notification
        EventBus.getDefault().postSticky(new ReloadNotifyEvent(true));
        if (connectionDetector.isConnectingToInternet()) {
            step = 1;
            notifyPresenter.getNotifys();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_READED_ERROR), getString(R.string.READED_FAIL), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotifys();
    }

    @Override
    public void onSuccess(boolean isRead) {
        if (isRead) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_SUCCESS), getString(R.string.MARK_READ_SUCCESS), true, AlertDialogManager.SUCCESS);
            //getNotifys
            getNotifys();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccess(List<NotifyInfo> notifyInfos) {
        layout_notify.removeAllViewsInLayout();
        if (notifyInfos != null && notifyInfos.size() > 0) {
            txtNoData.setVisibility(View.GONE);
            layoutHeader.setVisibility(View.VISIBLE);
            notifyInfoList = new ArrayList<>();
            notifyInfoList.addAll(notifyInfos);
            NotifyAdapter notifyAdapter = new NotifyAdapter(this, R.layout.item_notify, notifyInfos, false);
            int adapterCount = notifyAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = notifyAdapter.getView(i, null, null);
                layout_notify.addView(item);
            }
        } else {
            txtNoData.setVisibility(View.VISIBLE);
            layoutHeader.setVisibility(View.GONE);
        }
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
        hideProgress();

        if (data != null && data.getType() != null && !data.getType().trim().equals("")) {
            switch (data.getType()) {
                case Constants.CONSTANTS_NOTI_CHOXULY:
                    DocumentWaitingInfo itemWaiting = new DocumentWaitingInfo();
                    itemWaiting.setId(idDoc);
                    itemWaiting.setIsChuTri(data.getParamCheckStoreDocInfo().getIsChuTri());
                    itemWaiting.setIsCheck(data.getParamCheckStoreDocInfo().getIsCheck());
                    itemWaiting.setProcessDefinitionId(data.getParamCheckStoreDocInfo().getProcessKey());
                    itemWaiting.setCongVanDenDi(data.getParamCheckStoreDocInfo().getCongVanDenDi());
                    EventBus.getDefault().postSticky(itemWaiting);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(itemWaiting.getId(), Constants.DOCUMENT_WAITING, null));
                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemWaiting.getId(), itemWaiting.getProcessDefinitionId(), itemWaiting.getCongVanDenDi()));
                    startActivity(new Intent(this, DetailDocumentWaitingActivity.class));

                    break;
                case Constants.CONSTANTS_NOTI_TRACUU:
                    DocumentSearchInfo item = new DocumentSearchInfo();
                    item.setId(idDoc);
                    EventBus.getDefault().postSticky(item);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                    startActivity(new Intent(this, DetailDocumentNotificationActivity.class));
                    break;
                case Constants.CONSTANTS_NOTI_DAXULY:
                    DocumentProcessedInfo itemProcessed = new DocumentProcessedInfo();
                    itemProcessed.setId(idDoc);
                    itemProcessed.setIsChuTri(data.getParamCheckStoreDocInfo().getIsChuTri());
                    itemProcessed.setIsCheck(data.getParamCheckStoreDocInfo().getIsCheck());
                    itemProcessed.setProcessDefinitionId(data.getParamCheckStoreDocInfo().getProcessKey());
                    itemProcessed.setCongVanDenDi(data.getParamCheckStoreDocInfo().getCongVanDenDi());
                    EventBus.getDefault().postSticky(itemProcessed);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(itemProcessed.getId(), Constants.DOCUMENT_PROCESSED, null));
                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemProcessed.getId(), itemProcessed.getProcessDefinitionId(), itemProcessed.getCongVanDenDi()));
                    startActivity(new Intent(this, DetailDocumentProcessedActivity.class));

                    break;
                case Constants.CONSTANTS_NOTI_THONGBAO:
                    DocumentNotificationInfo itemNotification = new DocumentNotificationInfo();
                    itemNotification.setId(idDoc);
                    itemNotification.setIsChuTri(data.getParamCheckStoreDocInfo().getIsChuTri());
                    itemNotification.setIsCheck(data.getParamCheckStoreDocInfo().getIsCheck());
                    itemNotification.setProcessDefinitionId(data.getParamCheckStoreDocInfo().getProcessKey());
                    itemNotification.setCongVanDenDi(data.getParamCheckStoreDocInfo().getCongVanDenDi());
                    EventBus.getDefault().postSticky(itemNotification);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(itemNotification.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(itemNotification.getId(), itemNotification.getProcessDefinitionId(), itemNotification.getCongVanDenDi()));
                    startActivity(new Intent(this, DetailDocumentNotificationActivity.class));

                    break;
                case Constants.CONSTANTS_NOTI_WEB:
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), "Bạn vui lòng truy cập website để xử lý thông báo này", true, AlertDialogManager.INFO);
                    break;
                case Constants.CONSTANTS_NOTI_TTDH:
                    if (data.getParamCheckStoreDocInfo().getIdThongTin() != null) {
                        Intent intentDieuHanh = new Intent(this, DetailChiDaoActivity.class);
                        intentDieuHanh.putExtra("ID_CHIDAO", data.getParamCheckStoreDocInfo().getIdThongTin());
                        startActivity(intentDieuHanh);

                    }
                    break;
            }
        }
    }

    @Override
    public void onCountDocumentSuccess(CountDocument data) {

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        if (connectionDetector.isConnectingToInternet()) {
            if (step == 1) {
                getNotifys();
            } else {
                readedNotify();
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(NotifyActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        if (!NotifyActivity.this.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
            }

            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (!NotifyActivity.this.isFinishing()) {
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

    public void getNotify(String id, String idDoc) {
        showProgress();
        this.idDoc = idDoc;
        notifyPresenter.checkStoreDoc(Integer.parseInt(id));
    }
}
