package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.SelectGroupPersonAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChangeDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changedocument.ChangeDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changedocument.IChangeDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListGroupPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.SelectGroupPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class SelectGroupPersonActivity extends AppCompatActivity implements IChangeDocumentView, ILoginView {

    @BindView(R.id.rcvParent)
    RecyclerView rcvParent;
    @BindView(R.id.layout_process)
    LinearLayout layoutProcess;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.tv_xuly_chinh)
    TextView tvXulyChinh;
    @BindView(R.id.tv_dong_xuly)
    TextView tvDongXuly;
    @BindView(R.id.tv_xem)
    TextView tvXem;
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSelect;
    private TextView tvTitle;
    private String type;
    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_group_person);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        setupToolbar();
        getPersons();
    }

    private void setupToolbar() {
        connectionDetector = new ConnectionDetector(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarSelectPerson);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        setTitle(getString(R.string.SELECT_PERSON));
        tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        tvTitle.setTypeface(Application.getApp().getTypeface());
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnSelect = (ImageView) toolbar.findViewById(R.id.btnSelect);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Person> personList = new ArrayList<>();
               // TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                if (listPersonEvent != null) {
                    personList = listPersonEvent.getPersonGroupList();
                }
                if (personList != null && personList.size() > 0) {
                    EventBus.getDefault().postSticky(new SelectGroupPersonEvent(true));
                    finish();
                } else {
                    AlertDialogManager.showAlertDialog(SelectGroupPersonActivity.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_GROUP), true, AlertDialogManager.INFO);
                }
            }
        });
        if (type.equals("2")) {
            tvXulyChinh.setVisibility(View.GONE);
            tvDongXuly.setVisibility(View.GONE);
            tvXem.setText("Xem để biết");
        }
    }

    private void getPersons() {
        if (type.equals("0") || type.equals("2")) {
            tvTitle.setText("Chọn theo nhóm người nhận");
            changeDocumentPresenter.getPersonGroupCN();
        }
        if (type.equals("1")) {
            tvTitle.setText("Chọn theo nhóm đơn vị nhận");
            changeDocumentPresenter.getPersonGroupDV();
        }
    }

    @Override
    public void onSuccessGroupPerson(List<PersonGroupChangeDocInfo> personGroupChangeDocInfos) {
        if (personGroupChangeDocInfos != null && personGroupChangeDocInfos.size() > 0) {
            List<PersonGroupChangeDocInfo> personGroupChangeDocInfoParents = new ArrayList<>();
            for (PersonGroupChangeDocInfo personGroupChangeDocInfo : personGroupChangeDocInfos) {
                if (personGroupChangeDocInfo.getParentId() == null || personGroupChangeDocInfo.getParentId().trim().equals("")) {
                    personGroupChangeDocInfoParents.add(personGroupChangeDocInfo);
                }
            }
            txtNoData.setVisibility(View.GONE);
            EventBus.getDefault().postSticky(new ListGroupPersonEvent(personGroupChangeDocInfos));
            SelectGroupPersonAdapter adapter = new SelectGroupPersonAdapter(this, personGroupChangeDocInfoParents, type);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
            rcvParent.setLayoutManager(mLayoutManager);
            rcvParent.setItemAnimator(new DefaultItemAnimator());
            rcvParent.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            txtNoData.setVisibility(View.VISIBLE);
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
    public void onSuccessChangeDoc() {

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        if (connectionDetector.isConnectingToInternet()) {
            Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
            getPersons();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(SelectGroupPersonActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        if (!SelectGroupPersonActivity.this.isFinishing()) {
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
        if (!SelectGroupPersonActivity.this.isFinishing()) {
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
