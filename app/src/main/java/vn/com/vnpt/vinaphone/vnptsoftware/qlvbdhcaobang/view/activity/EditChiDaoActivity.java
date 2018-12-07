package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aditya.filebrowser.Constants;
import com.aditya.filebrowser.FileChooser;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.FileChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.PersonChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.PersonReceiveChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.SpinnerCheckboxAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.ChiDaoFlow;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.GroupPerson;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ChoiseGroupEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.KeywordEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonChiDaoAddEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonChiDaoCheckEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonChiDaoDeleteEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonChiDaoOldEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonChiDaoResultEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonEditChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonSendChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ShowProgressEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.PersonChiDaoCheck;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.StateVO;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class EditChiDaoActivity extends AppCompatActivity implements IChiDaoView, ILoginView {

    @BindView(R.id.lineStep1)
    View lineStep1;
    @BindView(R.id.imgStep1)
    ImageView imgStep1;
    @BindView(R.id.txtStep1)
    TextView txtStep1;
    @BindView(R.id.step1)
    LinearLayout step1;
    @BindView(R.id.lineStep2)
    View lineStep2;
    @BindView(R.id.imgStep2)
    ImageView imgStep2;
    @BindView(R.id.txtStep2)
    TextView txtStep2;
    @BindView(R.id.step2)
    LinearLayout step2;
    @BindView(R.id.lineStep3)
    View lineStep3;
    @BindView(R.id.imgStep3)
    ImageView imgStep3;
    @BindView(R.id.txtStep3)
    TextView txtStep3;
    @BindView(R.id.step3)
    LinearLayout step3;
    @BindView(R.id.tv_tieude)
    EditText tvTieude;
    @BindView(R.id.tv_noidung)
    EditText tvNoidung;
    @BindView(R.id.tv_filedinhkem_label)
    TextView tvFiledinhkemLabel;
    @BindView(R.id.layoutStep1)
    LinearLayout layoutStep1;
    @BindView(R.id.layoutStep2)
    LinearLayout layoutStep2;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.btnSaveDraft)
    Button btnSaveDraft;
    @BindView(R.id.btnSendAll)
    Button btnSendAll;
    @BindView(R.id.layoutStep3)
    LinearLayout layoutStep3;
    @BindView(R.id.layoutDisplay)
    LinearLayout layoutDisplay;
    @BindView(R.id.btnSelectFile)
    TextView btnSelectFile;
    @BindView(R.id.layoutFile)
    LinearLayout layoutFile;
    private static final int SELECT_FILE_RESULT_SUCCESS = 1;
    @BindView(R.id.edtKeywordName)
    EditText edtKeywordName;
    @BindView(R.id.edtKeywordNameReceive)
    EditText edtKeywordNameReceive;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.txtNoDataReceive)
    TextView txtNoDataReceive;
    @BindView(R.id.layout_contact)
    LinearLayout layoutContact;
    @BindView(R.id.layoutDisplayStep)
    LinearLayout layoutDisplayStep;
    @BindView(R.id.layout_receive)
    LinearLayout layout_receive;
    @BindView(R.id.layoutSave)
    LinearLayout layoutSave;
    @BindView(R.id.sNhom)
    Spinner sNhom;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.checkSMS)
    CheckBox checkSMS;
    @BindView(R.id.btnSavePerson)
    Button btnSavePerson;
    @BindView(R.id.btnBackStep1)
    Button btnBackStep1;
    @BindView(R.id.layoutSavePerson)
    LinearLayout layoutSavePerson;
    @BindView(R.id.btnBackStep2)
    Button btnBackStep2;
    @BindView(R.id.layoutSend)
    LinearLayout layoutSend;
    private ArrayList<Uri> selectedFiles;
    private int stepCurrent;
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private List<String> fileNames;
    private String event;
    private List<PersonChiDao> personChiDaos;
    private String thongTinId;
    private List<PersonReceiveChiDao> personReceiveChiDaos;
    private List<GroupPerson> groupPersonList;
    private ChiDaoFlow chiDaoFlow;
    private List<vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.FileChiDao> fileChiDaos;
    private List<FileChiDao> fileChiDaoList;
    private List<String> deleteFiles;
    private String groups;
    private List<String> personInGroup;
    private List<String> personInGroupPre;
    private List<PersonReceiveChiDao> personEditChiDaos;
    private int nextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chi_dao);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    private void init() {
        ButterKnife.bind(this);
        thongTinId = getIntent().getStringExtra("id");
        selectedFiles = new ArrayList<>();
        stepCurrent = 1;
        deleteFiles = new ArrayList<>();
        step1.performClick();
        checkSMS.setChecked(false);
        connectionDetector = new ConnectionDetector(this);
        edtKeywordName.setTypeface(Application.getApp().getTypeface());
        edtKeywordNameReceive.setTypeface(Application.getApp().getTypeface());
        edtKeywordName.addTextChangedListener(
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
                                        getPersons();
                                    }
                                });
                            }
                        }, vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
        edtKeywordNameReceive.addTextChangedListener(
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
                                        getPersonsReceive();
                                    }
                                });
                            }
                        }, vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
        getDetail();
    }

    private void getPersonsReceive() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_REVEICE";
            chiDaoPresenter.getPersonReceiveChiDao(new PersonReceiveChiDaoRequest("", "", thongTinId, edtKeywordNameReceive.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPersonsEdit() {
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new PersonChiDaoAddEvent(new ArrayList<String>()));
            personInGroupPre = new ArrayList<>();
            event = "GET_PERSON_EDIT";
            chiDaoPresenter.getPersonReceiveChiDao(new PersonReceiveChiDaoRequest("", "", thongTinId, ""));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPersons() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON";
            chiDaoPresenter.getPersonChiDao(new PersonChiDaoRequest(edtKeywordName.getText().toString().trim()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getGroupUser() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_GROUP";
            chiDaoPresenter.getPersonGroupChiDao();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_DETAIL";
            chiDaoPresenter.getDetailChiDao(thongTinId);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void send() {
        if (connectionDetector.isConnectingToInternet()) {
            boolean check_send = false;
            List<PersonReceiveChiDao> personReceiveChiDaos = EventBus.getDefault().getStickyEvent(PersonSendChiDaoEvent.class).getPersonReceiveChiDaos();
            for (int i = 0; i < personReceiveChiDaos.size(); i++) {
                if (personReceiveChiDaos.get(i).getNgayNhan() == null) {
                    check_send = true;
                    break;
                }
            }
            if (check_send) {
                event = "SEND_CHIDAO";
                chiDaoPresenter.send(new SendChiDaoRequest(thongTinId, checkSMS.isChecked() ? "1" : "0", ""));
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_SENT_ALL), true, AlertDialogManager.ERROR);
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getPersonInGroup() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_IN_GROUP";
            chiDaoPresenter.getPersonIngroup(new PersonInGroupChiDaoRequest(groups));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void refreshAfterSendAll() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_PERSON_REVEICE";
            chiDaoPresenter.getPersonReceiveChiDao(new PersonReceiveChiDaoRequest("", "", thongTinId, ""));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_FILE";
            chiDaoPresenter.getFileChiDao(thongTinId);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void saveInfo() {
        if (connectionDetector.isConnectingToInternet()) {
            switch (stepCurrent) {
                case 1:
                    event = "SAVE_CHIDAO";
                    fileNames = new ArrayList<>();
                    if (tvTieude.getText() != null && !tvTieude.getText().toString().trim().equals("")) {
                        if (selectedFiles != null && selectedFiles.size() > 0) {
                            MultipartBody.Part[] parts = new MultipartBody.Part[selectedFiles.size()];
                            int i = -1;
                            for (Uri uri : selectedFiles) {
                                i++;
                                parts[i] = prepareFilePart("fileupload", uri);
                                String[] uriStr = uri.getPath().split("/");
                                fileNames.add(uriStr[uriStr.length - 1]);
                            }
                            chiDaoPresenter.uploadFiles(parts);
                        } else {
                            EventBus.getDefault().postSticky(new ShowProgressEvent(true));
                            ChiDaoRequest chiDaoRequest = new ChiDaoRequest(chiDaoFlow.getId(), tvTieude.getText().toString().trim(),
                                    tvNoidung.getText().toString().trim(), fileNames, deleteFiles, "", 0);
                            chiDaoPresenter.saveChiDao(chiDaoRequest);
                        }
                    } else {
                        tvTieude.setError(getString(R.string.TIEUDE_REQUIERD));
                        //AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.TIEUDE_REQUIERD), true, AlertDialogManager.ERROR);
                    }
                    break;
                case 2:
                    event = "SAVE_PERSON";
                    List<String> empEmail = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class).getEmails();
                    List<String> empDelete = new ArrayList<>();
                    List<String> empAdd = new ArrayList<>();
                    if (empEmail != null && empEmail.size() > 0) {
                        PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
                        List<String> chons = emails.getEmails();
                        if (personEditChiDaos != null && personEditChiDaos.size() > 0) {
                            for (int i = 0; i < personEditChiDaos.size(); i++) {
                                if (!chons.contains(personEditChiDaos.get(i).getId())) {
                                    empDelete.add(personEditChiDaos.get(i).getId());
                                }
                            }
                            for (int i = 0; i < chons.size(); i++) {
                                boolean check = false;
                                for (int j = 0; j < personEditChiDaos.size(); j++) {
                                    if (chons.get(i).equals(personEditChiDaos.get(j).getId())) {
                                        check = true;
                                        break;
                                    }
                                }
                                if (!check) {
                                    empAdd.add(chons.get(i));
                                }
                            }
                        } else {
                            empAdd = empEmail;
                        }
                        if (empAdd.size() > 0 || empDelete.size() > 0) {
                            chiDaoPresenter.savePersonChiDao(new SavePersonChiDaoRequest(thongTinId, empAdd, empDelete));
                        } else {
                            nextStep3();
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.PERSON_REQUIERD), true, AlertDialogManager.ERROR);
                    }
                    break;
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @OnClick({R.id.step1, R.id.step2, R.id.step3})
    public void onStep(View view) {
        switch (view.getId()) {
            case R.id.step1:
                nextStep = 1;
                stepCurrent = 1;
                layoutStep1.setVisibility(View.VISIBLE);
                layoutStep2.setVisibility(View.GONE);
                layoutStep3.setVisibility(View.GONE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
                txtStep1.setTextColor(getResources().getColor(R.color.colorTextBlue));
                txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp);
                layoutSave.setVisibility(View.VISIBLE);
                layoutSavePerson.setVisibility(View.GONE);
                layoutSend.setVisibility(View.GONE);
                break;
            case R.id.step2:
                stepCurrent = 2;
                layoutStep1.setVisibility(View.GONE);
                layoutStep2.setVisibility(View.VISIBLE);
                layoutStep3.setVisibility(View.GONE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
                txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep2.setTextColor(getResources().getColor(R.color.colorTextBlue));
                txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
                layoutSave.setVisibility(View.GONE);
                layoutSavePerson.setVisibility(View.VISIBLE);
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                layoutSend.setVisibility(View.GONE);
                getPersonsEdit();
                break;
            case R.id.step3:
                stepCurrent = 3;
                layoutStep1.setVisibility(View.GONE);
                layoutStep2.setVisibility(View.GONE);
                layoutStep3.setVisibility(View.VISIBLE);
                lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
                lineStep3.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
                imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
                imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
                imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3));
                txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
                txtStep3.setTextColor(getResources().getColor(R.color.colorTextBlue));
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                layoutSave.setVisibility(View.GONE);
                layoutSavePerson.setVisibility(View.GONE);
                layoutSend.setVisibility(View.VISIBLE);
                layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
                getPersonsReceive();
                break;
        }
    }

    private void fillData() {
        tvTieude.setText(chiDaoFlow.getTieuDe());
        if (chiDaoFlow.getNoiDung() != null && !chiDaoFlow.getNoiDung().trim().equals("")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                tvNoidung.setText(Html.fromHtml(chiDaoFlow.getNoiDung()));
            } else {
                tvNoidung.setText(Html.fromHtml(chiDaoFlow.getNoiDung(), Html.FROM_HTML_MODE_COMPACT));
            }
        } else {
            tvNoidung.setText("");
        }
    }

    private void fillFiles() {
        fileChiDaoList = new ArrayList<>();
        for (int i = 0; i < fileChiDaos.size(); i++) {
            FileChiDao fileChiDao = new FileChiDao(fileChiDaos.get(i).getName());
            fileChiDaoList.add(fileChiDao);
        }
        FileChiDaoAdapter fileChiDaoAdapter = new FileChiDaoAdapter(this, R.layout.item_file_chidao_list, fileChiDaoList, "EDIT");
        int adapterCount = fileChiDaoAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = fileChiDaoAdapter.getView(i, null, null);
            layoutFile.addView(item);
        }
    }

    @OnClick({R.id.btnSave, R.id.btnSaveDraft, R.id.btnSelectFile, R.id.btnSendAll, R.id.btnBack, R.id.btnBackStep1, R.id.btnBackStep2, R.id.btnSavePerson})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSavePerson:
                saveInfo();
                break;
            case R.id.btnBackStep1:
                backStep1();
                break;
            case R.id.btnBackStep2:
                backStep2();
                break;
            case R.id.btnSave:
                nextStep = 2;
                saveInfo();
                break;
            case R.id.btnSaveDraft:
                saveInfo();
                break;
            case R.id.btnSelectFile:
                Intent i2 = new Intent(this, FileChooser.class);
                i2.putExtra(Constants.SELECTION_MODE, Constants.SELECTION_MODES.MULTIPLE_SELECTION.ordinal());
                startActivityForResult(i2, SELECT_FILE_RESULT_SUCCESS);
                break;
            case R.id.btnSendAll:
                send();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

    private void backStep1() {
        nextStep = 1;
        stepCurrent = 1;
        layoutStep1.setVisibility(View.VISIBLE);
        layoutStep2.setVisibility(View.GONE);
        layoutStep3.setVisibility(View.GONE);
        lineStep1.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
        lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1));
        imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
        imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
        txtStep1.setTextColor(getResources().getColor(R.color.colorTextBlue));
        txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
        txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp);
        layoutSave.setVisibility(View.VISIBLE);
        layoutSavePerson.setVisibility(View.GONE);
        layoutSend.setVisibility(View.GONE);
    }

    private boolean isLoad = true;

    private void backStep2() {
        stepCurrent = 2;
        layoutStep1.setVisibility(View.GONE);
        layoutStep2.setVisibility(View.VISIBLE);
        layoutStep3.setVisibility(View.GONE);
        lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        lineStep2.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
        lineStep3.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
        imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2));
        imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3_disable));
        txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
        txtStep2.setTextColor(getResources().getColor(R.color.colorTextBlue));
        txtStep3.setTextColor(getResources().getColor(R.color.md_grey_600));
        layoutSave.setVisibility(View.GONE);
        layoutSavePerson.setVisibility(View.VISIBLE);
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        layoutSend.setVisibility(View.GONE);
        isLoad = false;
        getPersonsEdit();
        checkDelete();
    }

    private void nextStep3() {
        stepCurrent = 3;
        layoutStep1.setVisibility(View.GONE);
        layoutStep2.setVisibility(View.GONE);
        layoutStep3.setVisibility(View.VISIBLE);
        lineStep1.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        lineStep2.setBackgroundColor(getResources().getColor(R.color.colorLineGrey));
        lineStep3.setBackgroundColor(getResources().getColor(R.color.colorTextBlue));
        imgStep1.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_1_disable));
        imgStep2.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_2_disable));
        imgStep3.setImageDrawable(getResources().getDrawable(R.drawable.ic_step_3));
        txtStep1.setTextColor(getResources().getColor(R.color.md_grey_600));
        txtStep2.setTextColor(getResources().getColor(R.color.md_grey_600));
        txtStep3.setTextColor(getResources().getColor(R.color.colorTextBlue));
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        layoutSave.setVisibility(View.GONE);
        layoutSavePerson.setVisibility(View.GONE);
        layoutSend.setVisibility(View.VISIBLE);
        layoutDisplayStep.setBackgroundResource(R.drawable.background_border_1dp_grey);
        getPersonsReceive();
    }

    private void checkDelete() {
        PersonChiDaoDeleteEvent personChiDaoDeleteEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoDeleteEvent.class);
        PersonChiDaoOldEvent personChiDaoOldEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoOldEvent.class);
        if (personChiDaoDeleteEvent != null) {
            List<String> deletes = personChiDaoDeleteEvent.getEmails();
            List<String> olds = personChiDaoOldEvent.getMails();
            if (deletes != null && deletes.size() > 0) {
                for (String delete : deletes) {
                    if (olds.contains(delete)) {
                        olds.remove(delete);
                        PersonChiDaoCheckEvent personChiDaoCheckEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoCheckEvent.class);
                        List<PersonChiDaoCheck> personChiDaoChecks = personChiDaoCheckEvent.getPersonChiDaoCheckList();
                        for (int i = 0; i < personChiDaoChecks.size(); i++) {
                            if (personChiDaoChecks.get(i).getId().equals(delete)) {
                                View view1 = personChiDaoChecks.get(i).getView();
                                CheckBox check = (CheckBox) view1.findViewById(R.id.checkChon);
                                check.setChecked(false);
                                personChiDaoChecks.get(i).setView(view1);
                                personChiDaoCheckEvent.setPersonChiDaoCheckList(personChiDaoChecks);
                                EventBus.getDefault().postSticky(personChiDaoCheckEvent);
                                break;
                            }
                        }
                    }
                }
            }
            personChiDaoOldEvent.setMails(olds);
            EventBus.getDefault().postSticky(personChiDaoOldEvent);
            EventBus.getDefault().removeStickyEvent(PersonChiDaoDeleteEvent.class);
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (event.equals("GET_FILE")) {
            fileChiDaos = ConvertUtils.fromJSONList(object, vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.FileChiDao.class);
            if (fileChiDaos != null && fileChiDaos.size() > 0) {
                fillFiles();
            }
        }
        if (event != null && event.equals("GET_DETAIL")) {
            chiDaoFlow = ConvertUtils.fromJSON(object, ChiDaoFlow.class);
            fillData();
            getFiles();
        }
        if (event != null && event.equals("GET_PERSON")) {
            EventBus.getDefault().postSticky(new KeywordEvent(edtKeywordName.getText().toString().trim()));
            personChiDaos = ConvertUtils.fromJSONList(object, PersonChiDao.class);
            displayPerson();
        }
        if (event != null && event.equals("GET_GROUP")) {
            groupPersonList = ConvertUtils.fromJSONList(object, GroupPerson.class);
            displayPersonGroup();
            getPersons();
        }
        if (event != null && event.equals("GET_PERSON_EDIT")) {
            personEditChiDaos = ConvertUtils.fromJSONList(object, PersonReceiveChiDao.class);
            EventBus.getDefault().postSticky(new PersonEditChiDaoEvent(personEditChiDaos));
            PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
            List<String> chons = emails.getEmails();
            if (chons == null) {
                chons = new ArrayList<>();
            }
            if (personEditChiDaos != null) {
                for (int i = 0; i < personEditChiDaos.size(); i++) {
                    if (!chons.contains(personEditChiDaos.get(i).getId())) {
                        chons.add(personEditChiDaos.get(i).getId());
                    }
                }
                emails.setEmails(chons);
            }
            EventBus.getDefault().postSticky(emails);
            if (isLoad) {
                getGroupUser();
            }
        }
        if (event != null && event.equals("SAVE_CHIDAO")) {
            //thongTinId = (String) object;
            //AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
            Toast.makeText(this, getString(R.string.SAVE_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            hideSoftInput();
            if (nextStep == 2) {
                step1.setEnabled(false);
                step2.setEnabled(true);
                step2.performClick();
            }
        }
        if (event != null && event.equals("GET_PERSON_REVEICE")) {
            personReceiveChiDaos = ConvertUtils.fromJSONList(object, PersonReceiveChiDao.class);
            displayPersonReceive();
        }
        if (event != null && event.equals("SAVE_PERSON")) {
            //AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_PERSON_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
            Toast.makeText(this, getString(R.string.SAVE_PERSON_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            step3.performClick();
        }
        if (event != null && event.equals("SEND_CHIDAO")) {
            Toast.makeText(this, getString(R.string.SEND_CHIDAO_SUCCESS), Toast.LENGTH_LONG).show();
            //AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SEND_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
            //refreshAfterSendAll();
            onBackPressed();
        }
    }

    @Override
    public void onSuccess() {
        ChiDaoRequest chiDaoRequest = new ChiDaoRequest(chiDaoFlow.getId(), tvTieude.getText().toString().trim(),
                tvNoidung.getText().toString().trim(), fileNames, deleteFiles, "", 0);
        chiDaoPresenter.saveChiDao(chiDaoRequest);
    }

    @Override
    public void onSuccess(List<Object> object) {
        personInGroup = ConvertUtils.fromJSONList(object, String.class);
        if (personInGroup == null) {
            personInGroup = new ArrayList<>();
        }
        displayPerson();
    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            if (event != null && event.equals("SAVE_CHIDAO")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("SAVE_PERSON")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SAVE_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON_REVEICE")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_GROUP")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_GROUP_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("SEND_CHIDAO")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.SEND_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_DETAIL")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_DETAIL_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_FILE")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_FILE_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON_IN_GROUP")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_IN_GROUP_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event != null && event.equals("GET_PERSON_EDIT")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_PERSON_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            if (event != null && event.equals("SAVE_CHIDAO")) {
                saveInfo();
            }
            if (event != null && event.equals("GET_PERSON")) {
                getPersons();
            }
            if (event != null && event.equals("SAVE_PERSON")) {
                saveInfo();
            }
            if (event != null && event.equals("GET_PERSON_REVEICE")) {
                getPersonsReceive();
            }
            if (event != null && event.equals("GET_GROUP")) {
                getGroupUser();
            }
            if (event != null && event.equals("SEND_CHIDAO")) {
                send();
            }
            if (event != null && event.equals("GET_DETAIL")) {
                getDetail();
            }
            if (event != null && event.equals("GET_PERSON_IN_GROUP")) {
                getPersonInGroup();
            }
            if (event != null && event.equals("GET_PERSON_EDIT")) {
                getPersonsEdit();
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case SELECT_FILE_RESULT_SUCCESS:
                if (resultCode == RESULT_OK) {
                    ArrayList<Uri> sessionSelectedFiles = data.getParcelableArrayListExtra(Constants.SELECTED_ITEMS);
                    if (sessionSelectedFiles != null && sessionSelectedFiles.size() > 0) {
                        List<FileChiDao> fileChiDaos = new ArrayList<>();
                        for (Uri uri : sessionSelectedFiles) {
                            String[] uriStr = uri.getPath().split("/");
                            if (!checkDuplicate(uri)) {
                                if (validateExtension(uriStr[uriStr.length - 1])) {
                                    File file = new File(uri.getPath());
                                    if (validateSize(file)) {
                                        fileChiDaos.add(new FileChiDao(uriStr[uriStr.length - 1]));
                                        selectedFiles.add(uri);
                                    } else {
                                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_SIZE), true, AlertDialogManager.ERROR);
                                    }
                                } else {
                                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.INVALID_FILE_EXT), true, AlertDialogManager.ERROR);
                                }
                            } else {
                                Toast.makeText(this, "Có file trùng", Toast.LENGTH_LONG).show();
                            }
                        }
                        FileChiDaoAdapter fileChiDaoAdapter = new FileChiDaoAdapter(this, R.layout.item_file_chidao_list, fileChiDaos, "EDIT");
                        int adapterCount = fileChiDaoAdapter.getCount();
                        for (int i = 0; i < adapterCount; i++) {
                            View item = fileChiDaoAdapter.getView(i, null, null);
                            layoutFile.addView(item);
                        }
                    }
                }
                break;
        }
    }

    public void removeFile(String filename) {
        for (int i = 0; i < fileChiDaos.size(); i++) {
            if (fileChiDaos.get(i).getName().toUpperCase().equals(filename.toUpperCase())) {
                if (fileChiDaos.get(i).getHdd() != null && !fileChiDaos.get(i).getHdd().trim().equals("")) {
                    deleteFiles.add(fileChiDaos.get(i).getHdd());
                }
                fileChiDaos.remove(i);
                return;
            }
        }
        for (int i = 0; i < selectedFiles.size(); i++) {
            Uri uri = selectedFiles.get(i);
            String[] uriStr = uri.getPath().split("/");
            if (filename.equals(uriStr[uriStr.length - 1])) {
                selectedFiles.remove(i);
                return;
            }
        }
    }

    private boolean checkDuplicate(Uri uri) {
        String[] uriStr = uri.getPath().split("/");
        if (selectedFiles.contains(uri)) {
            return true;
        } else {
            if (fileChiDaos != null && fileChiDaos.size() > 0) {
                for (int i = 0; i < fileChiDaos.size(); i++) {
                    if (fileChiDaos.get(i).getName().toUpperCase().equals(uriStr[uriStr.length - 1].toUpperCase())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<StateVO> lst;

    private void displayPersonGroup() {
        lst = new ArrayList<>();
        lst.add(new StateVO(getString(R.string.DEFAULT_GROUP), false, null));
        EventBus.getDefault().postSticky(new ChoiseGroupEvent(groups, "EDIT"));
        if (groupPersonList != null && groupPersonList.size() > 0) {
            lst.add(new StateVO(getString(R.string.ALL_GROUP), false, null));
            for (int i = 0; i < groupPersonList.size(); i++) {
                lst.add(new StateVO(groupPersonList.get(i).getName(), false, groupPersonList.get(i).getId()));
            }
        }
        setupSpinnerNhom();
    }


    private void setupSpinnerNhom() {
        SpinnerCheckboxAdapter myAdapter = new SpinnerCheckboxAdapter(this, 0, lst);
        sNhom.setAdapter(myAdapter);
    }

    private void displayPersonReceive() {
        layout_receive.removeAllViewsInLayout();
        if (personReceiveChiDaos != null && personReceiveChiDaos.size() > 0) {
            EventBus.getDefault().postSticky(new PersonSendChiDaoEvent(personReceiveChiDaos));
            txtNoDataReceive.setVisibility(View.GONE);
            PersonReceiveChiDaoAdapter contactAdapter = new PersonReceiveChiDaoAdapter(this, R.layout.item_person_chidao_send, personReceiveChiDaos, thongTinId);
            int adapterCount = contactAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = contactAdapter.getView(i, null, null);
                layout_receive.addView(item);
            }
        } else {
            txtNoDataReceive.setVisibility(View.VISIBLE);
        }
    }

    private void displayPerson() {
        PersonChiDaoAddEvent emails = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class);
        List<String> chons = emails.getEmails();
        if (personInGroup != null) {
            for (int i = 0; i < personInGroup.size(); i++) {
                if (!chons.contains(personInGroup.get(i))) {
                    chons.add(personInGroup.get(i));
                }
            }
            for (int i = 0; i < personInGroupPre.size(); i++) {
                if (!personInGroup.contains(personInGroupPre.get(i))) {
                    if (chons.contains(personInGroupPre.get(i))) {
                        chons.remove(personInGroupPre.get(i));
                    }
                }
            }
            emails.setEmails(chons);
        }
        EventBus.getDefault().postSticky(emails);
        layoutContact.removeAllViewsInLayout();
        layoutContact.setVisibility(View.GONE);
        List<PersonChiDao> lstParent = new ArrayList<>();
        EventBus.getDefault().postSticky(new PersonChiDaoResultEvent(personChiDaos));
        if (personChiDaos != null && personChiDaos.size() > 0) {
            txtNoData.setText("Vui lòng đợi trong giây lát...");
            for (int i = 0; i < personChiDaos.size(); i++) {
                if (personChiDaos.get(i).getParentId() == null) {
                    lstParent.add(personChiDaos.get(i));
                }
            }
            List<Integer> sizes = new ArrayList<Integer>(lstParent.size());
            List<Integer> countUsers = new ArrayList<Integer>(lstParent.size());
            for (PersonChiDao contact : lstParent) {
                int count = 0;
                int countUser = 0;
                for (int i = 0; i < personChiDaos.size(); i++) {
                    if (personChiDaos.get(i).getParentId() != null && personChiDaos.get(i).getParentId().equals(contact.getUnitId())) {
                        count++;
                        if (personChiDaos.get(i).getChucVu() != null && !personChiDaos.get(i).getChucVu().trim().equals("")) {
                            countUser++;
                        }
                    }
                }
                sizes.add(count);
                countUsers.add(countUser);
            }
            List<PersonChiDaoCheck> personChiDaoChecks = new ArrayList<>();
            EventBus.getDefault().postSticky(new PersonChiDaoCheckEvent(personChiDaoChecks));
            PersonChiDaoAdapter contactAdapter = new PersonChiDaoAdapter(this, R.layout.item_person_chidao_list, R.layout.item_person_chidao_detail, R.layout.item_person_chidao_list_detail, lstParent, sizes, countUsers, "EDIT");
            int adapterCount = contactAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = contactAdapter.getView(i, null, null);
                PersonChiDaoCheckEvent personChiDaoCheckEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoCheckEvent.class);
                personChiDaoChecks = personChiDaoCheckEvent.getPersonChiDaoCheckList();
                PersonChiDaoCheck personChiDaoCheck = new PersonChiDaoCheck(item, lstParent.get(i).getId());
                personChiDaoChecks.add(personChiDaoCheck);
                personChiDaoCheckEvent.setPersonChiDaoCheckList(personChiDaoChecks);
                layoutContact.addView(item);
                EventBus.getDefault().postSticky(personChiDaoCheckEvent);
            }
            layoutContact.setVisibility(View.VISIBLE);
            txtNoData.setVisibility(View.GONE);
            init = false;
        } else {
            txtNoData.setText("Không tìm thấy danh sách người nhận");
            txtNoData.setVisibility(View.VISIBLE);
        }
        personInGroupPre = personInGroup;
    }

    private boolean init = true;

    public void showContact() {
        layoutContact.setVisibility(View.VISIBLE);
        txtNoData.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private boolean validateExtension(String filename) {
        if (filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.DOC)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.DOCX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.XLS)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.XLSX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.PDF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.JPG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.JPEG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.PNG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.GIF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.TIFF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.BMP)) {
            return true;
        }
        return false;
    }

    private boolean validateSize(File file) {
        double size = (double) file.length() / (1024 * 1024);
        if (size < 50) {
            return true;
        }
        return false;
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = new File(fileUri.getPath());
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());//getMimeType(fileUri.getPath())
        RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @Override
    public void showProgress() {
        if (EditChiDaoActivity.this.isFinishing()) {
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
        if (EditChiDaoActivity.this.isFinishing()) {
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

    @Override
    public void onBackPressed() {
        hideSoftInput();
        EventBus.getDefault().removeStickyEvent(ChoiseGroupEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonChiDaoAddEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonEditChiDaoEvent.class);
        EventBus.getDefault().removeStickyEvent(PersonChiDaoResultEvent.class);
        finish();
    }

    @OnCheckedChanged(R.id.checkSMS)
    public void checkSMS(CompoundButton compoundButton, boolean b) {
        EventBus.getDefault().postSticky(new Boolean(b));
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ChoiseGroupEvent choiseGroupEvent) {
        String type = choiseGroupEvent.getType();
        if (type.equals("EDIT") && !init) {
            groups = choiseGroupEvent.getGroups();
            if (groups == null || groups.trim().equals("")) {
                personInGroup = new ArrayList<>();
                displayPerson();
            } else {
                getPersonInGroup();
            }
        }
    }

}
