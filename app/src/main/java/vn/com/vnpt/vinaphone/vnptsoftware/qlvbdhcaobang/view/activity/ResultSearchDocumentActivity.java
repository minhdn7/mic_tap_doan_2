package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.DocumentAdvanceSearchAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.DocumentAdvanceSearchParameter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentAdvanceSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentAdvanceSearch;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentAdvanceSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentsearch.DocumentSearchPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentsearch.IDocumentSearchPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocAdvanceSearchEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDocumentResultSearchView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class ResultSearchDocumentActivity extends BaseActivity implements IDocumentResultSearchView, ILoginView {

    private Toolbar toolbar;
    private ImageView btnBack;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rcvDanhSach) RecyclerView rcvDanhSach;
    @BindView(R.id.txtNoData) TextView txtNoData;
    private DocumentAdvanceSearchAdapter documentAdvanceSearchAdapter;
    private List<DocumentAdvanceSearchInfo> documentAdvanceSearchInfoList;
    private int page = 1;
    private int totalPage;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private IDocumentSearchPresenter documentSearchPresenter = new DocumentSearchPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_search_document);
        init();
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
        setTitle(getString(R.string.RESULT_SEARCH));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void init() {
        setupToolbar();
        connectionDetector = new ConnectionDetector(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRefresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        documentAdvanceSearchInfoList = new ArrayList<DocumentAdvanceSearchInfo>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
//            documentSearchPresenter.getCountAdvance(new DocumentAdvanceSearchRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE),
//                    EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchParameter.class)));
            documentSearchPresenter.getRecordsAdvance(new DocumentAdvanceSearchRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchParameter.class)));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void loadRefresh() {
        page = 1;
        documentAdvanceSearchInfoList = new ArrayList<DocumentAdvanceSearchInfo>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
//            documentSearchPresenter.getCountAdvance(new DocumentAdvanceSearchRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE),
//                    EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchParameter.class)));
            documentSearchPresenter.getRecordsAdvance(new DocumentAdvanceSearchRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchParameter.class)));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void prepareNewData() {
        documentAdvanceSearchAdapter = new DocumentAdvanceSearchAdapter(this, documentAdvanceSearchInfoList);
        documentAdvanceSearchAdapter.setLoadMoreListener(new DocumentAdvanceSearchAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvDanhSach.post(new Runnable() {
                    @Override
                    public void run() {
                        page ++;
//                        if (page <= totalPage + 1) {
//                            loadMore(page);
//                        }
                        if (documentAdvanceSearchInfoList.size() % Constants.DISPLAY_RECORD_SIZE == 0) {
                            loadMore(page);
                        }
                    }
                });
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvDanhSach.setLayoutManager(mLayoutManager);
        rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
        rcvDanhSach.setAdapter(documentAdvanceSearchAdapter);
        documentAdvanceSearchAdapter.notifyDataSetChanged();
        if (documentAdvanceSearchInfoList != null && documentAdvanceSearchInfoList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void loadMore(int page) {
        if (connectionDetector.isConnectingToInternet()) {
            documentSearchPresenter.getRecordsAdvance(new DocumentAdvanceSearchRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchParameter.class)));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
//            documentSearchPresenter.getCountAdvance(new DocumentAdvanceSearchRequest(Constants.PAGE_NO_DEFAULT, String.valueOf(Constants.DISPLAY_RECORD_SIZE),
//                    EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchParameter.class)));
            documentSearchPresenter.getRecordsAdvance(new DocumentAdvanceSearchRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchParameter.class)));
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
    public void onSuccessRecords(List<DocumentAdvanceSearchInfo> documentAdvanceSearchInfos) {
        if (EventBus.getDefault().getStickyEvent(InitEvent.class).isInit()) {
            if (documentAdvanceSearchInfos != null && documentAdvanceSearchInfos.size() > 0) {
                documentAdvanceSearchInfoList.addAll(documentAdvanceSearchInfos);
            }
            prepareNewData();
            EventBus.getDefault().postSticky(new InitEvent(false));
        } else {
            documentAdvanceSearchInfoList.add(new DocumentAdvanceSearchInfo());
            documentAdvanceSearchAdapter.notifyItemInserted(documentAdvanceSearchInfoList.size() - 1);
            documentAdvanceSearchInfoList.remove(documentAdvanceSearchInfoList.size() - 1);
            if (documentAdvanceSearchInfos != null && documentAdvanceSearchInfos.size() > 0) {
                documentAdvanceSearchInfoList.addAll(documentAdvanceSearchInfos);
            } else {
                documentAdvanceSearchAdapter.setMoreDataAvailable(false);
            }
            documentAdvanceSearchAdapter.notifyDataChanged();
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
    public void onSuccessCount(CountDocumentAdvanceSearch countDocumentAdvanceSearch) {
        totalPage = countDocumentAdvanceSearch.getPageNo();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentSearchPresenter.getRecordsAdvance(new DocumentAdvanceSearchRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE),
                    EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchParameter.class)));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void showProgress() {
        if (ResultSearchDocumentActivity.this.isFinishing()) {
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
        if (!ResultSearchDocumentActivity.this.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ReloadDocAdvanceSearchEvent reloadDocSearchEvent) {
        if (reloadDocSearchEvent != null && reloadDocSearchEvent.isLoad()) {
            loadRefresh();
        }
        EventBus.getDefault().removeStickyEvent(ReloadDocAdvanceSearchEvent.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideProgress();
    }
}
