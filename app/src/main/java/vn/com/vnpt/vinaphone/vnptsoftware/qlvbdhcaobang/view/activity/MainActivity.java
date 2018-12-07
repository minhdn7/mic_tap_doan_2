package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import me.leolin.shortcutbadger.ShortcutBadger;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.ExpandableListAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.RealmDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.builder.ContactBuilder;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Event;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.NumberCountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ContactInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.NotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.contact.ContactPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.contact.IContactPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.countdocument.CountDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.countdocument.ICountDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.logout.ILogoutPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.logout.LogoutPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify.INotifyPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.notify.NotifyPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.userinfo.IUserInfoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.userinfo.UserInfoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ChiDaotEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.EventCheckSMS;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.KhoLoginEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.KiThanhCongEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.NhanWebViewEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.NotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.OnBackEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.UserNameEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.ChiDaoFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.ContactFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.DocumentExpriedFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.DocumentMarkFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.DocumentNotificationFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.DocumentProcessedFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.DocumentWaitingFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.HomeFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.ReportFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment.ScheduleBossFragment;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.DefaultHome;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.ExpandedMenuModel;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.MyExceptionHandler;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.syncevent.IContactSync;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ICountDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILogoutView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.INotifyView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IUserAvatarView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IUserSwitchView;

public class MainActivity extends BaseActivity implements ILogoutView, ILoginView,
        INotifyView, IUserAvatarView, ICountDocumentView,IUserSwitchView,IContactSync {

    private ApplicationSharedPreferences appPrefs;
    private ILogoutPresenter logoutPresenter = new LogoutPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private INotifyPresenter notifyPresenter = new NotifyPresenterImpl(this);
    private IUserInfoPresenter userInfoPresenter = new UserInfoPresenterImpl(this,this);
    private ICountDocumentPresenter iCountDocumentPresenter = new CountDocumentPresenterImpl(this);
    private Realm realm;
    private Toolbar toolbar;
    private ImageView avatarUser;
    private TextView txtName;
    private TextView txtDonVi;
    private ImageView ivSwitchUser;
    private TextView tvTitle;
    private TextView txtTongThongBao;
    private LinearLayout lo_thong_bao;
    private LinearLayout ll_header_menu;
    private FrameLayout layoutThongBao;
    private Handler mHandler = new Handler();
    //    private Timer mTimer = null;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private IContactPresenter contactPresenter = new ContactPresenterImpl(this);
    private DrawerLayout mDrawerLayout;
    private ExpandableListAdapter mMenuAdapter;
    private ExpandableListView expandableList;
    private List<ExpandedMenuModel> listDataHeader;
    private HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listDataChild;
    private ImageView btnLeftMenu;
    private NavigationView navigationView;
    private LoginInfo loginInfo;
    private List<LoginInfo.UserSwitch> listUserSwitch = new ArrayList<>() ;
    private NumberCountDocument.Data dataCountDoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this,
                LoginActivity.class));
        init();
        getCountNotification();

        EventBus.getDefault().postSticky(new NhanWebViewEvent(0));
        EventBus.getDefault().postSticky(new KiThanhCongEvent(0));
//        EventBus.getDefault().postSticky(new CheckAlLEvent(0));


        notifyPresenter.getCountDocument();
//        if (appPrefs.getTimeGetContact() == null || checkTimeGetContact()) {
//            synchronousContact();
//        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void init() {
        appPrefs = Application.getApp().getAppPrefs();
        setupToolbar();
        setupLeftMenu();
        laucherContent();
        //startSync();
        btnLeftMenu = (ImageView) findViewById(R.id.btnLeftMenu);
        btnLeftMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
                iCountDocumentPresenter.getCountDocument();
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void OnMessageEventNotify(ReloadNotifyEvent event) {
        if (event != null && event.isReload()) {
            getCountNotification();
        }
    }
//    private void startSync() {
//        if (mTimer != null) {
//            mTimer.cancel();
//        } else {
//            mTimer = new Timer();
//        }
//        SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SYNC);
//        Date currentDate = new Date();
//        if (appPrefs.getTimeSync() != null && !appPrefs.getTimeSync().trim().equals("")) {
//            String timeLatestStr = appPrefs.getTimeSync();
//            try {
//                Date timeLatest = sdf.parse(timeLatestStr);
//                long startTimer = (currentDate.getTime() - timeLatest.getTime()) / 1000;
//                mTimer.scheduleAtFixedRate(new TimeProcessSync(), startTimer, Application.getApp().getTimeSync());
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        } else {
//            mTimer.scheduleAtFixedRate(new TimeProcessSync(), 0, Application.getApp().getTimeSync());
//        }
//        String timeUpdated = sdf.format(currentDate);
//        appPrefs.setTimeSync(timeUpdated);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(EventCheckSMS.class);
        EventBus.getDefault().removeStickyEvent(NotifyEvent.class);
        EventBus.getDefault().unregister(this);
        hideProgress();
    }

    @Override
    public void onSuccessLogout() {
        Application.getApp().getAppPrefs().removeAll();
//        clearDocumentWaiting();
//        clearSchedule();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onErrorLogout(APIError apiError) {
        if (apiError.getCode() != Constants.RESPONE_UNAUTHEN) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.LOGOUT_TITLE_ERROR), apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);
        } else {
            appPrefs = Application.getApp().getAppPrefs();
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(appPrefs.getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void showLogoutProgress() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideLogoutProgress() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        appPrefs.setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            logoutPresenter.logout();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        //clearDocumentWaiting();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSuccessSwitchUser(LoginInfo userInfo) {

        userInfo.setListSwitchUser(loginInfo.getListSwitchUser());
        loginInfo = userInfo;
        appPrefs.setToken(loginInfo.getToken());
        appPrefs.setAccountLogin(loginInfo);

        EventBus.getDefault().postSticky(loginInfo);
        synchronousContact();


    }

    @Override
    public void onErrorSwitchUser(APIError apiError) {
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage(), true, AlertDialogManager.ERROR);
    }

    @Override
    public void showProgress() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void selectItemChild(int groupPosition, int childPostion) {
        Fragment fragment = null;
        String title = null;
        switch (groupPosition) {
            case Constants.DOCUMENT:
                ExpandedMenuModel menuModel = (ExpandedMenuModel) (expandableList.getExpandableListAdapter()
                        .getChild(groupPosition, childPostion));
                switch (menuModel.getIconName()) {
                    case Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY);
                        title = Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY;
                        break;
                    case Constants.CONSTANTS_VAN_BAN_CHO_PHE_DUYET:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_CHO_PHE_DUYET);
                        title = Constants.CONSTANTS_VAN_BAN_CHO_PHE_DUYET;
                        break;
                    case Constants.CONSTANTS_VAN_BAN_DI:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_DI);
                        title = Constants.CONSTANTS_VAN_BAN_DI;
                        break;
                    case Constants.CONSTANTS_VAN_BAN_CHO_TONG_GIAM_DOC_XU_LY:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_CHO_TONG_GIAM_DOC_XU_LY);
                        title = Constants.CONSTANTS_VAN_BAN_CHO_TONG_GIAM_DOC_XU_LY;
                        break;
                    case Constants.CONSTANTS_VAN_BAN_DEN:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_DEN);
                        title = Constants.CONSTANTS_VAN_BAN_DEN;
                        break;
                    case Constants.CONSTANTS_VAN_BAN_DA_XU_LY:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = new DocumentProcessedFragment();
                        title = getString(R.string.DOC_PROCESSED_MENU);
                        break;
                    case Constants.CONSTANTS_VAN_BAN_DEN_HAN_QUA_HAN:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = new DocumentExpriedFragment();
                        title = getString(R.string.DOC_EXPRIED_MENU);
                        break;
                    case Constants.CONSTANTS_VAN_BAN_XEM_DE_BIET:
                        EventBus.getDefault().postSticky(new EventCheckSMS(false));
                        fragment = new DocumentNotificationFragment();
                        title = getString(R.string.DOC_NOTIFICATION_MENU);
                        break;
                    case Constants.CONSTANTS_VAN_BAN_DANH_DAU:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        fragment = DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_DANH_DAU);
                        title = getString(R.string.DOC_MARK_MENU);
                        break;
//                    case Constants.CONSTANTS_VAN_BAN_DANH_DAU:
//                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
//                        fragment = new DocumentMarkFragment();
//                        title = getString(R.string.DOC_MARK_MENU);
//                        break;
                    case Constants.CONSTANTS_TRA_CUU_VAN_BAN:
                        EventBus.getDefault().postSticky(new EventCheckSMS(true));
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(this, DocumentSearchActivity.class));
                        break;
                }
                break;
            case Constants.SETTING:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                switch (childPostion) {
                    case Constants.SETTING_PROFILE:
                        startActivity(new Intent(this, UserInfoActivity.class));
                        break;
                    case Constants.SETTING_CHANGE_PASSWORD:
                        startActivity(new Intent(this, ChangePasswordActivity.class));
                        break;
                    case Constants.SETTING_DEFAULT_HOME:
                        startActivity(new Intent(this, DefaultHomeActivity.class));
                        break;
                }
                break;
            case Constants.REPORT_EXT:
                hideNotify();
                switch (childPostion) {
                    case Constants.CHIDAO_NHAN:
                        EventBus.getDefault().postSticky(new ChiDaotEvent(0));
                        fragment = new ChiDaoFragment();
                        title = getString(R.string.CHIDAO_NHAN_MENU);
                        break;
                    case Constants.CHIDAO_GUI:
                        EventBus.getDefault().postSticky(new ChiDaotEvent(1));
                        fragment = new ChiDaoFragment();
                        title = getString(R.string.CHIDAO_GUI_MENU);
                        break;
                }
                break;
        }
        if (title != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            setTitle(title);
        }
    }

    private void selectItemGroup(int position) {
        Fragment fragment = null;
        String title = null;
        ExpandedMenuModel menuModel = (ExpandedMenuModel) (expandableList.getExpandableListAdapter()
                .getGroup(position));
        switch (menuModel.getIconName()) {
            case Constants.CONSTANTS_TEXT_TRANG_CHU:
                fragment = new HomeFragment();
                title = getString(R.string.home);
                break;
            case Constants.CONSTANTS_TEXT_TRANG_TIN_TUC:
                title = getString(R.string.NEWS_MENU);
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.FUNCTION_NO_SUPPORT_INFO), true, AlertDialogManager.INFO);
                break;
            case Constants.CONSTANTS_TEXT_DANH_BA:
                fragment = new ContactFragment();
                title = getString(R.string.CONTACT_MENU);
                break;
            case Constants.CONSTANTS_TEXT_LICH_CONG_TAC_LANH_DAO:
                fragment = new ScheduleBossFragment();
                title = getString(R.string.SCHEDULE_MENU);
                break;
            case Constants.CONSTANTS_TEXT_BAO_CAO_THONG_KE:
                startActivity(new Intent(this, ReportActivity.class));
                break;
            case Constants.CONSTANTS_TEXT_DANG_XUAT:
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                final AlertDialog alertDialog = builder.create();
                alertDialog.setTitle(this.getString(R.string.TITLE_CONFIRM));
                alertDialog.setMessage(this.getString(R.string.CONFIRM_LOGOUT));
                alertDialog.setIcon(R.drawable.ic_confirm);
                alertDialog.setButton2(this.getString(R.string.ACCEPT_BUTTON), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        realm = RealmDao.with(getApplication()).getRealm();
                        if (connectionDetector.isConnectingToInternet()) {
                            logoutPresenter.logout();
                        } else {
                            AlertDialogManager.showAlertDialog(MainActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                        }
                    }
                });
                alertDialog.setButton(this.getString(R.string.CLOSE_BUTTON), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
        if (title != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            setTitle(title);
        }
    }

    private void getCountNotification() {
        if (connectionDetector.isConnectingToInternet()) {
            notifyPresenter.getNotifys();


        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

//    private void clearDocumentWaiting() {
//        RealmResults<DocumentWaiting> results = RealmDao.with(this).findAll(DocumentWaiting.class);
//        if (results != null && results.size() > 0) {
//            realm.beginTransaction();
//            results.deleteAllFromRealm();
//            realm.commitTransaction();
//        }
//    }
//
//    private void clearSchedule() {
//        RealmResults<Schedule> results = RealmDao.with(this).findAll(Schedule.class);
//        if (results != null && results.size() > 0) {
//            realm.beginTransaction();
//            results.deleteAllFromRealm();
//            realm.commitTransaction();
//        }
//    }

    private void laucherContent() {
        OnBackEvent onBackEvent = EventBus.getDefault().getStickyEvent(OnBackEvent.class);
        if (onBackEvent != null) {
            EventBus.getDefault().removeStickyEvent(OnBackEvent.class);
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (onBackEvent.getName().equals(Constants.HOME_VANBANCHOXULY)) {
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentWaitingFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[2]);
            }
        } else {
            try {
                String defaultHome = getDefaultHome(Application.getApp().getAppPrefs().getAccount().getUsername());
                if (defaultHome != null && !defaultHome.trim().equals("")) {
                    goDefault(defaultHome);
                } else {
                    goHome();
                }
                StepPre stepPre = EventBus.getDefault().getStickyEvent(StepPre.class);
                if (stepPre != null && stepPre.getCall().equals("Notify")) {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.FUNCTION_NO_SUPPORT_INFO), true, AlertDialogManager.INFO);
                }
            } catch (Exception e) {
                e.printStackTrace();
                goHome();
            }
        }
    }

    private String getDefaultHome(String userId) {
        DefaultHome results = RealmDao.with(Application.getApp()).findByKey(DefaultHome.class, userId, "userId");
        if (results != null && results.getName() != null && !results.getName().equals("")) {
            return results.getName();
        } else {
            return null;
        }
    }

    private void goDefault(String defaultHome) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (defaultHome) {
            case Constants.HOME_TRANGCHU:
                goHome();
                break;
            case Constants. HOME_VANBANDENCANXULY:
                EventBus.getDefault().postSticky(new EventCheckSMS(true));
                fragmentManager.beginTransaction().replace(R.id.content_frame, DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY)).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[1]);
                break;
//            case Constants.HOME_VANBANCHOXULY:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentWaitingFragment()).commit();
//                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[2]);
//                break;
            case Constants.HOME_VANBANDAXULY:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentProcessedFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[2]);
                break;
//            case Constants.HOME_VANBANDENHAN:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentExpriedFragment()).commit();
//                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[4]);
//                break;
//            case Constants.HOME_VANBANTHONGBAO:
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentNotificationFragment()).commit();
//                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[4]);
//                break;
            case Constants.HOME_VANBANDANHDAU:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentMarkFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[4]);
                break;
            case Constants.HOME_DANHBA:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ContactFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[5]);
                break;
            case Constants.HOME_QUANLYLICHHOP:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduleBossFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[6]);
                break;
//            case Constants.HOME_BAOCAO:
//                goHome();
//                break;
            case Constants.HOME_VANABANXEMDEBIET:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new DocumentNotificationFragment()).commit();
                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[2]);
                break;
//            case Constants.HOME_THONGTINCHIDAO:
//                hideNotify();
//                EventBus.getDefault().postSticky(new ChiDaotEvent(0));
//                fragmentManager.beginTransaction().replace(R.id.content_frame, new ChiDaoFragment()).commit();
//                setTitle(getResources().getStringArray(R.array.navigation_drawer_items_left_menu)[10]);
//                break;
        }
    }

    private void goHome() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtTongThongBao = (TextView) toolbar.findViewById(R.id.tong_thong_bao);
        lo_thong_bao = (LinearLayout) toolbar.findViewById(R.id.lo_thong_bao);
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        lo_thong_bao.setVisibility(View.GONE);
        txtTongThongBao.setText("");
        txtTongThongBao.setTypeface(Application.getApp().getTypeface());
        tvTitle.setTypeface(Application.getApp().getTypeface());
        layoutThongBao = (FrameLayout) toolbar.findViewById(R.id.layoutThongBao);
        layoutThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NotifyActivity.class));
                //finish();
            }
        });
        setTitle(getString(R.string.home));
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void hideNotify() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        txtTongThongBao = (TextView) toolbar.findViewById(R.id.tong_thong_bao);
//        lo_thong_bao = (LinearLayout) toolbar.findViewById(R.id.lo_thong_bao);
//        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
//        lo_thong_bao.setVisibility(View.GONE);
//        txtTongThongBao.setText("");
//        txtTongThongBao.setTypeface(Application.getApp().getTypeface());
//        tvTitle.setTypeface(Application.getApp().getTypeface());
//        layoutThongBao = (FrameLayout) toolbar.findViewById(R.id.layoutThongBao);
        layoutThongBao.setVisibility(View.GONE);
    }

    public void showNotify() {
        layoutThongBao.setVisibility(View.VISIBLE);
    }

    private void setupLeftMenu() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        createHeader();
        //Load count menu

        createMenu();
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);
        expandableList.setAdapter(mMenuAdapter);
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                selectItemChild(i, i1);
                return false;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                selectItemGroup(i);
                return false;
            }
        });
    }

    private void createHeader() {
        loginInfo = appPrefs.getAccountLogin();
        if (loginInfo == null) {
           return;
        }
        View hView = navigationView.getHeaderView(0);
        txtName = (TextView) hView.findViewById(R.id.txtName);
        txtDonVi = (TextView) hView.findViewById(R.id.txtDonVi);
        ll_header_menu = (LinearLayout) hView.findViewById(R.id.ll_header_menu);
        ivSwitchUser = (ImageView) hView.findViewById(R.id.ivSwitchUser);
        avatarUser = (ImageView) hView.findViewById(R.id.avatarUser);
        txtName.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtDonVi.setTypeface(Application.getApp().getTypeface());
        txtName.setText(loginInfo.getFullName());
        txtDonVi.setText(loginInfo.getUnitName());
        userInfoPresenter.getAvatar(loginInfo.getUsername());
        if (loginInfo.getListSwitchUser() != null && loginInfo.getListSwitchUser().size() > 0) {
            listUserSwitch = loginInfo.getListSwitchUser();
            ivSwitchUser.setVisibility(View.VISIBLE);
        } else {
            ivSwitchUser.setVisibility(View.GONE);
        }
        ivSwitchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialogSwitchUser();

            }
        });

        EventBus.getDefault().postSticky(new UserNameEvent(loginInfo.getUsername()));
        EventBus.getDefault().postSticky(new KhoLoginEvent(loginInfo.getListKhoVanBan()));
//        if (loginInfo.getAvatar() == null) {
//            Glide.with(this).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar).bitmapTransform(new CircleTransform(this)).into(avatarUser);
//        } else {
//            Glide.with(this).load(loginInfo.getAvatar()).error(R.drawable.ic_avatar).bitmapTransform(new CircleTransform(this)).into(avatarUser);
//        }
    }

    private void createMenu() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<ExpandedMenuModel>>();

        // Adding header data
        listDataHeader.add(new ExpandedMenuModel(R.drawable.ic_home, getString(R.string.HOME_MENU)));
//        listDataHeader.add(new ExpandedMenuModel(R.drawable.ic_news, getString(R.string.NEWS_MENU)));
        listDataHeader.add(new ExpandedMenuModel(R.drawable.ic_document, getString(R.string.DOCUMENT_MENU)));
        listDataHeader.add(new ExpandedMenuModel(R.drawable.contact, getString(R.string.CONTACT_MENU)));
        listDataHeader.add(new ExpandedMenuModel(R.drawable.schedule, getString(R.string.SCHEDULE_MENU)));
        listDataHeader.add(new ExpandedMenuModel(R.drawable.ic_boss, getString(R.string.CHIDAO_MENU)));
//        listDataHeader.add(new ExpandedMenuModel(R.drawable.ic_report, getString(R.string.REPORT_MENU)));
        listDataHeader.add(new ExpandedMenuModel(R.drawable.ic_default_home, getString(R.string.SETTING_MENU)));
        listDataHeader.add(new ExpandedMenuModel(R.drawable.logout, getString(R.string.LOG_OUT_MENU)));

        // Adding child data
        List<ExpandedMenuModel> heading = new ArrayList<ExpandedMenuModel>();
//        heading.add(new ExpandedMenuModel(R.drawable.document, getString(R.string.WAITING_FOR_PROGRESSING_MENU)));
        if (loginInfo != null && loginInfo.getListKhoVanBan() != null
                && loginInfo.getListKhoVanBan().size() > 0) {
            for (String typeVanBan : loginInfo.getListKhoVanBan()) {

                switch (typeVanBan) {
                    case Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY:
                        if (dataCountDoc != null && dataCountDoc.getVanBanDenChoXuLy() > 0) {
                            heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan, dataCountDoc.getVanBanDenChoXuLy()));
                        } else {
                            heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan));
                        }
                        break;
                    case Constants.CONSTANTS_VAN_BAN_DEN:
                        if (dataCountDoc != null && dataCountDoc.getVanBanDen() > 0) {
                            heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan, dataCountDoc.getVanBanDen()));
                        } else {
                            heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan));
                        }
                        break;
                    case Constants.CONSTANTS_VAN_BAN_DI:
                        if (dataCountDoc != null && dataCountDoc.getVanBanDi() > 0) {
                            heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan, dataCountDoc.getVanBanDi()));
                        } else {
                            heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan));
                        }
                        break;
                    case Constants.CONSTANTS_VAN_BAN_CHO_PHE_DUYET:
                        if (dataCountDoc != null && dataCountDoc.getVanBanChoPheDuyet() > 0) {
                            heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan, dataCountDoc.getVanBanChoPheDuyet()));
                        } else {
                            heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan));
                        }
                        break;
                    case Constants.CONSTANTS_VAN_BAN_DANH_DAU:
                        if (dataCountDoc != null && dataCountDoc.getDanhDau() > 0) {
                            heading.add(new ExpandedMenuModel(R.drawable.ic_doc_mark, typeVanBan, dataCountDoc.getDanhDau()));
                        } else {
                            heading.add(new ExpandedMenuModel(R.drawable.ic_doc_mark, typeVanBan));
                        }
                        break;
                    default:
                        heading.add(new ExpandedMenuModel(R.drawable.document, typeVanBan));
                        break;
                }

            }
        }
        heading.add(new ExpandedMenuModel(R.drawable.ic_doc_processed, getString(R.string.DOC_PROCESSED_MENU)));
        //heading.add(new ExpandedMenuModel(R.drawable.ic_doc_expired, getString(R.string.DOC_EXPRIED_MENU)));
        if (dataCountDoc != null && dataCountDoc.getXemDeBiet() > 0) {
            heading.add(new ExpandedMenuModel(R.drawable.ic_doc_notification, getString(R.string.DOC_NOTIFICATION_MENU)
                    , dataCountDoc.getXemDeBiet()));
        } else {
            heading.add(new ExpandedMenuModel(R.drawable.ic_doc_notification, getString(R.string.DOC_NOTIFICATION_MENU)));
        }
//        if (dataCountDoc != null && dataCountDoc.getDanhDau() > 0) {
//            heading.add(new ExpandedMenuModel(R.drawable.ic_doc_mark,
//                    getString(R.string.DOC_MARK_MENU), dataCountDoc.getDanhDau()));
//        } else {
//            heading.add(new ExpandedMenuModel(R.drawable.ic_doc_mark, getString(R.string.DOC_MARK_MENU)));
//        }

        heading.add(new ExpandedMenuModel(R.drawable.ic_document_search, getString(R.string.SEARCH_DOCUMENT_LABEL)));
        listDataChild.put(listDataHeader.get(1), heading);
        heading = new ArrayList<ExpandedMenuModel>();
        heading.add(new ExpandedMenuModel(R.drawable.ic_boss, getString(R.string.CHIDAO_NHAN_MENU)));
        heading.add(new ExpandedMenuModel(R.drawable.ic_chidao_gui, getString(R.string.CHIDAO_GUI_MENU)));
        listDataChild.put(listDataHeader.get(4), heading);
        heading = new ArrayList<ExpandedMenuModel>();
        heading.add(new ExpandedMenuModel(R.drawable.profile, getString(R.string.PROFILE_MENU)));
        heading.add(new ExpandedMenuModel(R.drawable.password, getString(R.string.CHANGE_PASSWORD_MENU)));
        heading.add(new ExpandedMenuModel(R.drawable.ic_default_setting, getString(R.string.DEFAULT_HOME_MENU)));
        listDataChild.put(listDataHeader.get(5), heading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }

//    private void getDocumentWaiting() {
//        Intent startIntent = new Intent(getApplicationContext(), SyncDocumentWaitingService.class);
//        startService(startIntent);
//    }
//
//    private void getSchedule() {
//        Intent startIntent = new Intent(getApplicationContext(), SyncScheduleService.class);
//        startService(startIntent);
//    }

    @Override
    public void onSuccess(List<NotifyInfo> notifyInfos) {
        if (notifyInfos != null && notifyInfos.size() > 0) {
            lo_thong_bao.setVisibility(View.VISIBLE);
            txtTongThongBao.setTypeface(Application.getApp().getTypeface());
            txtTongThongBao.setText(String.valueOf(notifyInfos.size()));
            ShortcutBadger.applyCount(MainActivity.this, notifyInfos.size());
        } else {
            lo_thong_bao.setVisibility(View.GONE);
            txtTongThongBao.setText("");
            ShortcutBadger.applyCount(MainActivity.this, 0);
        }
    }

    @Override
    public void onSuccessCountDoc(NumberCountDocument countDocument) {
        if (countDocument != null && countDocument.getStatus().getCode()
                .equalsIgnoreCase(Constants.RESPONE_SUCCESS)) {
            dataCountDoc = countDocument.getData();
            createMenu();
            mMenuAdapter.updateDataEx(listDataHeader, listDataChild);
        }
    }

    @Override
    public void onError(APIError apiError) {
    }

    @Override
    public void onErrorAvatar(APIError apiError) {
        Glide.with(getApplicationContext()).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar).bitmapTransform(new CircleTransform(this)).into(avatarUser);
    }

    @Override
    public void onSuccessAvatar(byte[] bitmap) {
        Glide.with(this).load(bitmap).error(R.drawable.ic_avatar).bitmapTransform(new CircleTransform(this)).into(avatarUser);
    }

    private void createDialogSwitchUser() {
        final List<String> labels = new ArrayList<String>();
        for (int i = 0; i < listUserSwitch.size(); i++) {
           labels.add(listUserSwitch.get(i).getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.weight_table_menu, R.id.textViewTableMenuItem, labels);
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(ll_header_menu);
        listPopupWindow.setContentWidth(measureContentWidth(adapter));
        listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setHorizontalOffset(5); // margin left
        listPopupWindow.setVerticalOffset(-100);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userInfoPresenter.getListSwitchUser(listUserSwitch.get(position).getUserid());
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }
    private int measureContentWidth(ListAdapter listAdapter) {
        ViewGroup mMeasureParent = null;
        int maxWidth = 0;
        View itemView = null;
        int itemType = 0;

        final ListAdapter adapter = listAdapter;
        final int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            final int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }

            if (mMeasureParent == null) {
                mMeasureParent = new FrameLayout(this);
            }

            itemView = adapter.getView(i, itemView, mMeasureParent);
            itemView.measure(widthMeasureSpec, heightMeasureSpec);

            final int itemWidth = itemView.getMeasuredWidth();

            if (itemWidth > maxWidth) {
                maxWidth = itemWidth;
            }
        }

        return maxWidth;
    }
    private void synchronousContact() {
        if (connectionDetector.isConnectingToInternet()) {
            contactPresenter.getContacts();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @Override
    public void onSuccessSync(List<ContactInfo> contactInfos) {
        realm = RealmDao.with(this).getRealm();
        appPrefs.removeTimeGetContact();
        clearContacts();
        ContactBuilder contactBuilder = new ContactBuilder(this);
        saveContacts(contactBuilder.convertFromContactInfos(contactInfos));
    }

    @Override
    public void onErrorSync(APIError apiError) {
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION),
                apiError.getMessage() != null ? apiError.getMessage() : "Có lỗi khi đồng bộ danh bạ", true, AlertDialogManager.ERROR);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showProgressSync() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgressSync() {
        if (MainActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
