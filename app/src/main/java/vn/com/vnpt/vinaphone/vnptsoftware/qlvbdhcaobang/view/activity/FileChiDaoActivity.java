package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.ListFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class FileChiDaoActivity extends AppCompatActivity implements IChiDaoView, ILoginView {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.rvFiles)
    RecyclerView rvFiles;
    private ProgressDialog progressDialog;
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private List<FileChiDao> fileChiDaos;
    private String thongTinId;
    private String event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chi_dao);
        ButterKnife.bind(this);
        thongTinId = getIntent().getStringExtra("id");
        connectionDetector = new ConnectionDetector(this);
        getFiles();
    }

    private void getFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            showProgress();
            event = "GET_FILES";
            chiDaoPresenter.getFileChiDao(thongTinId);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillFiles() {
        ListFileAdapter listFileAdapter = new ListFileAdapter(this, fileChiDaos);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvFiles.setLayoutManager(mLayoutManager);
        rvFiles.setItemAnimator(new DefaultItemAnimator());
        rvFiles.setAdapter(listFileAdapter);
        listFileAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(Object object) {
        hideProgress();
        if (event.equals("GET_FILES")) {
            fileChiDaos = ConvertUtils.fromJSONList(object, FileChiDao.class);
            if (fileChiDaos != null && fileChiDaos.size() > 0) {
                fillFiles();
            }
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
        } else {
            if (event.equals("GET_FILES")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_FILE_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            if (event.equals("GET_FILES")) {
                getFiles();
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

    @OnClick(R.id.btnBack)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSuccess(List<Object> object) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void showProgress() {
        if (FileChiDaoActivity.this.isFinishing()) {
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
        if (!FileChiDaoActivity.this.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

    }
}
