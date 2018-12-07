package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.ListPersonReceiveAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class ListReceiveActivity extends AppCompatActivity implements IChiDaoView, ILoginView {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.txtSearch)
    EditText txtSearch;
    @BindView(R.id.rcvDanhSach)
    RecyclerView rcvDanhSach;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    private List<PersonReceiveChiDao> list;
    private ListPersonReceiveAdapter adapter;
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private int page = 1;
    private String thongTinId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_receive);
        ButterKnife.bind(this);
        thongTinId = getIntent().getStringExtra("id");
        connectionDetector = new ConnectionDetector(this);
        page = 1;
        list = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            chiDaoPresenter.getPersonReceivedChiDao(new PersonReceiveChiDaoRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE), thongTinId, txtSearch.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRefresh();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        txtSearch.addTextChangedListener(
                new TextWatcher() {
                    private Timer timer = new Timer();

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideSoftInput();
                                        search();
                                    }
                                });
                            }
                        }, Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
    }

    private void search() {
        page = 1;
        list = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            chiDaoPresenter.getPersonReceivedChiDao(new PersonReceiveChiDaoRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE), thongTinId, txtSearch.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void loadRefresh() {
        page = 1;
        list = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            chiDaoPresenter.getPersonReceivedChiDao(new PersonReceiveChiDaoRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE), thongTinId, txtSearch.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void prepareNewData() {
        adapter = new ListPersonReceiveAdapter(this, list);
        adapter.setLoadMoreListener(new ListPersonReceiveAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvDanhSach.post(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        if (list.size() % Constants.DISPLAY_RECORD_SIZE == 0) {
                            loadMore(page);
                        }
                    }
                });
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvDanhSach.setLayoutManager(mLayoutManager);
        rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
        rcvDanhSach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (list != null && list.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void loadMore(int page) {
        if (connectionDetector.isConnectingToInternet()) {
            chiDaoPresenter.getPersonReceivedChiDao(new PersonReceiveChiDaoRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE), thongTinId, txtSearch.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccess(Object object) {
        List<PersonReceiveChiDao> personReceiveChiDaos = ConvertUtils.fromJSONList(object, PersonReceiveChiDao.class);
        if (EventBus.getDefault().getStickyEvent(InitEvent.class).isInit()) {
            if (personReceiveChiDaos != null && personReceiveChiDaos.size() > 0) {
                list.addAll(personReceiveChiDaos);
            }
            prepareNewData();
            EventBus.getDefault().postSticky(new InitEvent(false));
        } else {
            list.add(new PersonReceiveChiDao());
            adapter.notifyItemInserted(list.size() - 1);
            list.remove(list.size() - 1);
            if (personReceiveChiDaos != null && personReceiveChiDaos.size() > 0) {
                list.addAll(personReceiveChiDaos);
            } else {
                adapter.setMoreDataAvailable(false);
            }
            adapter.notifyDataChanged();
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
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            chiDaoPresenter.getPersonReceivedChiDao(new PersonReceiveChiDaoRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE), thongTinId, txtSearch.getText().toString().trim()));
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
        if (!ListReceiveActivity.this.isFinishing()) {
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
        if (!ListReceiveActivity.this.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(List<Object> object) {

    }

    @OnClick(R.id.btnBack)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
