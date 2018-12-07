package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alamkanak.weekview.WeekViewEvent;
import com.github.lguipeng.library.animcheckbox.AnimCheckBox;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.BuildConfig;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConfigNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.NotificationUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.builder.ContactBuilder;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.CountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.LoginRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReadedNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckStoreDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ContactInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.contact.ContactPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.contact.IContactPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify.INotifyPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify.NotifyPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.version.IVersionPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.version.VersionPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.KhoLoginEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.UserNameEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.syncevent.IContactSync;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ICheckVersionView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IReadedNotifyView;

public class LoginActivity extends BaseActivity implements ILoginView, Validator.ValidationListener, IContactSync, IReadedNotifyView, ICheckVersionView {

    private ApplicationSharedPreferences appPrefs;
    private boolean isValidateLogin;
    private Validator validator;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IContactPresenter contactPresenter = new ContactPresenterImpl(this);
    private INotifyPresenter notifyPresenter = new NotifyPresenterImpl(this);
    private Realm realm;
    @BindView(R.id.txtDangKy)
    TextView txtDangKy;
    @BindView(R.id.btnDangNhap)
    RelativeLayout btnLogin;
    @BindView(R.id.ckGhiNhoTaiKhoan)
    AnimCheckBox ckGhiNhoTaiKhoan;
    @NotEmpty(messageResId = R.string.USERNAME_REQUIRED)
    @Length(max = 30, messageResId = R.string.USERNAME_INVALID_LENGTH)
    @BindView(R.id.txtUserName)
    //@Pattern(regex = StringDef.USERNAME_PATTERN, messageResId = R.string.USERNAME_INVALID_STRENGTH)
            EditText txtUsername;
    @NotEmpty(messageResId = R.string.PASSWORD_REQUIRED)
    @Length(min = 8, messageResId = R.string.PASSWORD_INVALID_LENGTH)
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.etUserLayout)
    TextInputLayout etUserLayout;
    @BindView(R.id.etPasswordLayout)
    TextInputLayout etPasswordLayout;
    @BindView(R.id.txtGhiNhoTaiKhoan)
    TextView txtGhiNhoTaiKhoan;
    @BindView(R.id.txtDangNhap)
    TextView txtDangNhap;
    @BindView(R.id.txtname2)
    TextView txtname2;
    @BindView(R.id.txtname1)
    TextView txtname1;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    @BindView(R.id.layoutDisplay)
    RelativeLayout layoutDisplay;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private IVersionPresenter versionPresenter = new VersionPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPrefs = Application.getApp().getAppPrefs();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);



        try {
            if (appPrefs.isSaveAccount() && appPrefs.getAccount() != null) {
                txtUsername.setText(appPrefs.getAccount().getUsername());
                txtPassword.setText(appPrefs.getAccount().getPassword());
            }
        } catch (Exception ex) {
        }
        callLoginApp();
        validator = new Validator(this);
        validator.setValidationListener(this);
        addControls();
    }

    private void checkVersionApp() {
        //Check VerisonApp
        if (connectionDetector.isConnectingToInternet()) {
            versionPresenter.checkVersion(new CheckVersionRequest("android", BuildConfig.VERSION_NAME));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }
    private void callLoginApp() {

        if (appPrefs.isSaveAccount()) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.loginPresenter(appPrefs.getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    private void nextStepAfterLogin() {

        handleNotification();
        if (!notification) {
            if (appPrefs.getTimeGetContact() == null || checkTimeGetContact()) {
                synchronousContact();
            } else {
                navigateToHome();
            }
        }
    }
    private void setFont() {

        txtname1.setTypeface(Application.getApp().getTypeface());
        txtname2.setTypeface(Application.getApp().getTypeface());
        etUserLayout.setTypeface(Application.getApp().getTypeface());
        etPasswordLayout.setTypeface(Application.getApp().getTypeface());
        txtDangKy.setTypeface(Application.getApp().getTypeface());
        txtUsername.setTypeface(Application.getApp().getTypeface());
        txtPassword.setTypeface(Application.getApp().getTypeface());
        txtGhiNhoTaiKhoan.setTypeface(Application.getApp().getTypeface());
        txtDangNhap.setTypeface(Application.getApp().getTypeface());
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        });
    }

    private void addControls() {
        setFont();
        ckGhiNhoTaiKhoan.setChecked(appPrefs.isSaveAccount());
        txtDangKy.setPaintFlags(txtDangKy.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txtDangKy.setText(getString(R.string.dangKy));
        try {
            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // checking for type intent filter
                    if (intent.getAction().equals(ConfigNotification.REGISTRATION_COMPLETE)) {
                        // gcm successfully registered
                        // now subscribe to `global` topic to receive app wide notifications
                        FirebaseMessaging.getInstance().subscribeToTopic(ConfigNotification.TOPIC_GLOBAL);
                    } else if (intent.getAction().equals(ConfigNotification.PUSH_NOTIFICATION)) {
                        // new push notification is received
                    }
                }
            };
        } catch (Exception ex) {

        }
    }

    private boolean notification = false;

    private void handleNotification() {
        if (getIntent().getExtras() != null) {
            String data = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_DATA);
            String type = null;
            String link = null;
            String id = null;
            if (data != null) {
                try {
                    JSONObject json = new JSONObject(data);
                    type = json.getString("type");
                    link = json.getString("link");
                    id = json.getString("id");
                } catch (Exception ex) {
                }
            } else {
                type = getIntent().getStringExtra("type");
                link = getIntent().getStringExtra("link");
                id = getIntent().getStringExtra("id");
            }
            if (type != null && !type.equals("") && link != null && !link.equals("") && id != null && !id.equals("")) {
                notification = true;
                redirectDisplay(type, link, id);
            }
        }
    }

    private String idDoc;

    private void redirectDisplay(String type, String link, String id) {
        try {
            if (type != null && Constants.TYPE_NOTIFY_DOCUMENT.contains(type)) {
                notification = true;
                idDoc = link.split("\\|")[1];
                notifyPresenter.checkStoreDoc(Integer.parseInt(id));
            } else {
                if (type != null && Constants.TYPE_NOTIFY_SCHEDULE.contains(type)) {
                    try {
                        notification = true;
                        WeekViewEvent weekViewEvent = new WeekViewEvent();
                        weekViewEvent.setId(Long.parseLong(link));
                        weekViewEvent.setType(Constants.SCHEDULE_MEETING);
                        EventBus.getDefault().postSticky(weekViewEvent);
                        EventBus.getDefault().postSticky(new StepPre(Constants.SCHEDULE_LIST));
                        startActivity(new Intent(this, DetailScheduleActivity.class));
                        finish();
                    } catch (Exception ex) {
                    }
                } else {
                    if (type != null && Constants.TYPE_NOTIFY_CHIDAO.contains(type)) {
                        notification = true;
                        String[] links = link.split("\\|");
                        String idTT = null;
                        if (links.length > 1) {
                            idTT = link.split("\\|")[1];
                        } else {
                            idTT = link.split("\\|")[0];
                        }
                        Intent intent = new Intent(getApplicationContext(), DetailChiDaoActivity.class);
                        intent.putExtra("ID_CHIDAO", idTT);
                        startActivity(intent);
                        finish();
                    }
                    if (type != null && Constants.TYPE_NOTIFY_WORK.contains(type)) {
                        startActivity(new Intent(this, DetailWorkActivity.class));
                        finish();
                    }
                    if (type != null && Constants.TYPE_NOTIFY_PROFILE.contains(type)) {
                        startActivity(new Intent(this, ProfileWorkActivity.class));
                        finish();
                    }
                    if (type != null && Constants.TYPE_NOTIFY_MAIL.contains(type)) {
                        startActivity(new Intent(this, LetterActivity.class));
                        finish();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        notifyPresenter.readedNotifys(new ReadedNotifyRequest(id));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String data = getIntent().getStringExtra(ConfigNotification.NOTIFICATION_DATA);
        String type = null;
        String link = null;
        String id = null;
        if (data != null) {
            try {
                JSONObject json = new JSONObject(data);
                type = json.getString("type");
                link = json.getString("link");
                id = json.getString("id");
            } catch (Exception ex) {
            }
        } else {
            type = getIntent().getStringExtra("type");
            link = getIntent().getStringExtra("link");
            id = getIntent().getStringExtra("id");
        }
        if (type != null && !type.equals("") && link != null && !link.equals("") && id != null && !id.equals("")) {
            redirectDisplay(type, link, id);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            // register GCM registration complete receiver
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(ConfigNotification.REGISTRATION_COMPLETE));
            // register new push message receiver
            // by doing this, the activity will be notified each time a new message arrives
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver, new IntentFilter(ConfigNotification.PUSH_NOTIFICATION));
            // clear the notification area when the app is opened
            NotificationUtils.clearNotifications(getApplicationContext());
        } catch (Exception ex) {

        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void showProgress() {
        if (!LoginActivity.this.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage(getString(R.string.LOGINING_REQUEST));
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
            }

            progressDialog.show();
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
//        if (ckGhiNhoTaiKhoan.isChecked()) {
//            appPrefs.setAccount(new LoginRequest(txtUsername.getText().toString().trim(), txtPassword.getText().toString().trim(),
//                    appPrefs.getFirebaseToken()));
//        }
        appPrefs.setAccount(new LoginRequest(txtUsername.getText().toString().trim(), txtPassword.getText().toString().trim(),
                appPrefs.getFirebaseToken()));

        appPrefs.setLogined(true);
        appPrefs.setSaveAccount(ckGhiNhoTaiKhoan.isChecked());
        appPrefs.setToken(loginInfo.getToken());
        appPrefs.setAccountLogin(loginInfo);

        EventBus.getDefault().postSticky(loginInfo);

        checkVersionApp();

    }

    private void synchronousContact() {
        if (connectionDetector.isConnectingToInternet()) {
            contactPresenter.getContacts();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private boolean checkTimeGetContact() {
        String timeSync = appPrefs.getTimeGetContact();
        SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_CHECK);
        String now = sdf.format(new Date());
        if (!timeSync.equals(now)) {
            return true;
        }
        return false;
    }

    @Override
    public void onSuccessSync(List<ContactInfo> contactInfos) {
        realm = RealmDao.with(this).getRealm();
        SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_CHECK);
        String now = sdf.format(new Date());
        appPrefs.setTimeGetContact(now);
        clearContacts();
        ContactBuilder contactBuilder = new ContactBuilder(this);
        saveContacts(contactBuilder.convertFromContactInfos(contactInfos));
    }

    @Override
    public void onErrorSync(APIError apiError) {
        navigateToHome();
    }

    @Override
    public void showProgressSync() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage(getString(R.string.LOGINING_REQUEST));
//        progressDialog.setIndeterminate(true);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
    }

    @Override
    public void hideProgressSync() {
        if (LoginActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    private void clearContacts() {
        realm.beginTransaction();
        realm.delete(Contact.class);
        realm.commitTransaction();
    }

    private void saveContacts(final List<Contact> contacts) {
        realm.beginTransaction();
        realm.copyToRealm(contacts);
        realm.commitTransaction();
        navigateToHome();
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        AlertDialogManager.showAlertDialog(this, getString(R.string.LOGIN_TITLE_ERROR), getString(R.string.LOGIN_INVALID), true, AlertDialogManager.ERROR);
    }

    @Override
    public void hideProgress() {
        if (!LoginActivity.this.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }


    @OnClick({R.id.btnDangNhap, R.id.txtDangKy})
    public void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.btnDangNhap:
                if (connectionDetector.isConnectingToInternet()) {
                    validator.validate();
                    if (isValidateLogin) {
                        loginPresenter.loginPresenter(new LoginRequest(txtUsername.getText().toString().trim(), txtPassword.getText().toString().trim(), appPrefs.getFirebaseToken()));
                    }
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
                break;
            case R.id.txtDangKy:
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.FUNCTION_NO_SUPPORT_INFO), true, AlertDialogManager.INFO);
                break;
        }
    }

    private void navigateToHome() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onValidationSucceeded() {
        isValidateLogin = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        isValidateLogin = false;
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.LOGIN_TITLE_ERROR), message, true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccess(boolean isRead) {

    }

    @Override
    public void onSuccess() {
        nextStepAfterLogin();
    }

    @Override
    public void onGoToUpdateStore(String message) {
        AlertDialogManager.showAlertDialog(this, getString(R.string.NOTIFY_MENU),message,
                true, AlertDialogManager.UPDATE_STORE);
    }

    @Override
    public void onError(APIError apiError) {
        AlertDialogManager.showAlertDialog(this, getString(R.string.LOGIN_TITLE_ERROR),
                apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);

    }

    @Override
    public void onCheckStoreSuccess(CheckStoreDocInfo data) {
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
                    finish();
                    break;
                case Constants.CONSTANTS_NOTI_TRACUU:
                    DocumentSearchInfo item = new DocumentSearchInfo();
                    item.setId(idDoc);
                    EventBus.getDefault().postSticky(item);
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(item.getId(), Constants.DOCUMENT_NOTIFICATION, null));
                    startActivity(new Intent(this, DetailDocumentNotificationActivity.class));
                    finish();
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
                    finish();
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
                    finish();
                    break;
                case Constants.CONSTANTS_NOTI_WEB:
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), "Bạn vui lòng truy cập website để xử lý thông báo này", true, AlertDialogManager.INFO);
                    break;
                case Constants.CONSTANTS_NOTI_TTDH:
                    if (data.getParamCheckStoreDocInfo().getIdThongTin() != null) {
                        Intent intentDieuHanh = new Intent(this, DetailChiDaoActivity.class);
                        intentDieuHanh.putExtra("ID_CHIDAO", data.getParamCheckStoreDocInfo().getIdThongTin());
                        startActivity(intentDieuHanh);
                        finish();
                    }
                    break;
            }
        }
    }

    @Override
    public void onCountDocumentSuccess(CountDocument data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }
}
