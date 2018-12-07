package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.NewHistoryAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.historydocument.HistoryDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.historydocument.IHistoryDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IHistoryDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class NewHistoryDocumentActivity extends BaseActivity implements IHistoryDocumentView, ILoginView {

    private IHistoryDocumentPresenter historyDocumentPresenter = new HistoryDocumentPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private int id;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnFilter;
    @BindView(R.id.rcvDanhSach)
    RecyclerView rcvDanhSach;
    private String status;
    private List<UnitLogInfo> unitLogInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_history_document);
        init();
        ButterKnife.bind(this);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        setTitle(getString(R.string.DOCUMENT_HISTORY));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnFilter = (ImageView) toolbar.findViewById(R.id.btnFilter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> labels = new ArrayList<String>();
                labels.add("Chờ xử lý");
                labels.add("Đang xử lý");
                labels.add("Đã xử lý");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewHistoryDocumentActivity.this, R.layout.weight_table_menu, R.id.textViewTableMenuItem, labels);
                final ListPopupWindow listPopupWindow = new ListPopupWindow(NewHistoryDocumentActivity.this);
                listPopupWindow.setAdapter(adapter);
                listPopupWindow.setAnchorView(btnFilter);
                listPopupWindow.setWidth(420);
                listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
                listPopupWindow.setHorizontalOffset(-402); // margin left
                listPopupWindow.setVerticalOffset(-20);
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        switch (i) {
                            case 0:
                                status = Constants.NEW_HISTORY;
                                break;
                            case 1:
                                status = Constants.PROCESSING_HISTORY;
                                break;
                            case 2:
                                status = Constants.PROCESSED_HISTORY;
                                break;
                        }
                        fillData();
                        listPopupWindow.dismiss();
                    }
                });
                listPopupWindow.show();
            }
        });
        status = Constants.PROCESSED_HISTORY;
    }

    private void init() {
        setupToolbar();
        id = Integer.parseInt(EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class).getId());
        if (connectionDetector.isConnectingToInternet()) {
            historyDocumentPresenter.getLogs(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccess(List<UnitLogInfo> unitLogInfoList) {
        this.unitLogInfoList = unitLogInfoList;
        fillData();
    }

    private void fillData() {
        NewHistoryAdapter adapter = new NewHistoryAdapter(this, unitLogInfoList, status);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvDanhSach.setLayoutManager(mLayoutManager);
        rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
        rcvDanhSach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN && connectionDetector.isConnectingToInternet()) {
            loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            historyDocumentPresenter.getLogs(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(NewHistoryDocumentActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        if (NewHistoryDocumentActivity.this.isFinishing()) {
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
        if (NewHistoryDocumentActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }
}
