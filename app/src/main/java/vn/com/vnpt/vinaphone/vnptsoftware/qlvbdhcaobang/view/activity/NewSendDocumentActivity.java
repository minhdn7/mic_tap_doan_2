package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.PersonSendDocAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Person;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChangeDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changedocument.ChangeDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changedocument.IChangeDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.EventCheckSMS;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.PersonSendDoc;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class NewSendDocumentActivity extends BaseActivity implements IChangeDocumentView, ILoginView {

    @BindView(R.id.txtNote)
    EditText txtNote;
    @BindView(R.id.layoutXLC)
    LinearLayout layoutXLC;
    @BindView(R.id.layoutXLCTitle)
    LinearLayout layoutXLCTitle;
    @BindView(R.id.layoutPH)
    LinearLayout layoutPH;
    @BindView(R.id.layoutPHTitle)
    LinearLayout layoutPHTitle;
    @BindView(R.id.layoutXemDB)
    LinearLayout layoutXemDB;
    @BindView(R.id.layoutXemDBTitle)
    LinearLayout layoutXemDBTitle;
    @BindView(R.id.layoutDisplay)
    ScrollView layoutDisplay;
    @BindView(R.id.checkSMS)
    CheckBox checkSMS;
    @BindView(R.id.ll_send_sms)
    LinearLayout llSendSms;
    @BindView(R.id.tv_han_xu_ly)
    TextView tvHanXuLy;
    @BindView(R.id.checkAutoSendJob)
    CheckBox checkAutoSendJob;
    @BindView(R.id.ll_auto_send_job)
    LinearLayout llAutoSendJob;

    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSend;
    private TextView tvTitle;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private TypeChangeDocumentRequest typeChangeDocumentRequest;
    private int type;
    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_send_document);
        init();
        initView();
        ButterKnife.bind(this);
    }

    private void initView() {
        llAutoSendJob.setVisibility(View.GONE);
        try {
            TypeChangeEvent typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
            List<TypeChangeDocument> typeChangeDocumentList = typeChangeEvent.getTypeChangeDocumentList();
            if (typeChangeDocumentList.get(typeChangeEvent.getSelected()).getJob().toLowerCase().equals("true")){
                llAutoSendJob.setVisibility(View.VISIBLE);
            }else {
                llAutoSendJob.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnSend = (ImageView) toolbar.findViewById(R.id.btnSend);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewSendDocumentActivity.this, SelectPersonActivity.class));
                finish();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionDetector.isConnectingToInternet()) {
                    send();
                } else {
                    AlertDialogManager.showAlertDialog(NewSendDocumentActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        });
    }

    private void init() {
        try {
            EventCheckSMS eventCheckSMS = EventBus.getDefault().getStickyEvent(EventCheckSMS.class);
            if (eventCheckSMS.isIsShow()) {
                llSendSms.setVisibility(View.VISIBLE);
            } else {
                llSendSms.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            llSendSms.setVisibility(View.VISIBLE);
        }
        connectionDetector = new ConnectionDetector(this);
        setupToolbar();
        txtNote.setTypeface(Application.getApp().getTypeface());
        typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
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
        displayPerson();
    }

    private void displayPerson() {
        TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        List<PersonSendDoc> personSendDocXLCs = new ArrayList<>();
        List<PersonSendDoc> personSendDocDXLs = new ArrayList<>();
        List<PersonSendDoc> personSendDocXems = new ArrayList<>();
        PersonSendDocAdapter personSendDocXLCAdapter = null;
        PersonSendDocAdapter personSendDocDXLAdapter = null;
        PersonSendDocAdapter personSendDocXemAdapter = null;
        int countXLC = 0;
        int countDXL = 0;
        int countXem = 0;
        if (typePersonEvent.getType().equals(Constants.TYPE_PERSON_SEND)
                || typePersonEvent.getType().equals(Constants.TYPE_PERSON_NOTIFY)
                || typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)
                || typePersonEvent.getType().equals(Constants.TYPE_PERSON_DIRECT)) {
            switch (typePersonEvent.getType()) {
                case Constants.TYPE_PERSON_SEND:
                    break;
                case Constants.TYPE_PERSON_NOTIFY:
                    break;
                case Constants.TYPE_SEND_PERSON_PROCESS:
                    if (listPersonEvent.getPersonProcessList() != null && listPersonEvent.getPersonProcessList().size() > 0) {
                        for (Person person : listPersonEvent.getPersonProcessList()) {
                            if (person.isXlc()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                personSendDocXLCs.add(personSendDoc);
                            }
                            if (person.isDxl()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                personSendDocDXLs.add(personSendDoc);
                            }
                            if (person.isXem()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                                personSendDocXems.add(personSendDoc);
                            }
                        }
                    }
                    if (listPersonEvent.getPersonLienThongList() != null && listPersonEvent.getPersonLienThongList().size() > 0) {
                        for (Person person : listPersonEvent.getPersonLienThongList()) {
                            if (person.isXlc()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                personSendDocXLCs.add(personSendDoc);
                            }
                            if (person.isDxl()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                personSendDocDXLs.add(personSendDoc);
                            }
                            if (person.isXem()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                personSendDocXems.add(personSendDoc);
                            }
                        }
                    }
                    personSendDocXLCAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXLCs);
                    countXLC = personSendDocXLCAdapter.getCount();
                    if (countXLC > 0) {
                        layoutXLCTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < countXLC; i++) {
                            View view = personSendDocXLCAdapter.getView(i, null, null);
                            layoutXLC.addView(view);
                        }
                    } else {
                        layoutXLCTitle.setVisibility(View.GONE);
                    }
                    personSendDocDXLAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocDXLs);
                    countDXL = personSendDocDXLAdapter.getCount();
                    if (countDXL > 0) {
                        layoutPHTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < countDXL; i++) {
                            View view = personSendDocDXLAdapter.getView(i, null, null);
                            layoutPH.addView(view);
                        }
                    } else {
                        layoutPHTitle.setVisibility(View.GONE);
                    }
                    personSendDocXemAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXems);
                    countXem = personSendDocXemAdapter.getCount();
                    if (countXem > 0) {
                        layoutXemDBTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < countXem; i++) {
                            View view = personSendDocXemAdapter.getView(i, null, null);
                            layoutXemDB.addView(view);
                        }
                    } else {
                        layoutXemDBTitle.setVisibility(View.GONE);
                    }
                    break;
                case Constants.TYPE_PERSON_DIRECT:
                    if (listPersonEvent.getPersonDirectList() != null && listPersonEvent.getPersonDirectList().size() > 0) {
                        for (Person person : listPersonEvent.getPersonDirectList()) {
                            if (person.isXlc()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "RIDRECT_LIST");
                                personSendDocXLCs.add(personSendDoc);
                            }
                            if (person.isDxl()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "RIDRECT_LIST");
                                personSendDocDXLs.add(personSendDoc);
                            }
                            if (person.isXem()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "RIDRECT_LIST");
                                personSendDocXems.add(personSendDoc);
                            }
                        }
                    }
                    if (listPersonEvent.getPersonLienThongList() != null && listPersonEvent.getPersonLienThongList().size() > 0) {
                        for (Person person : listPersonEvent.getPersonLienThongList()) {
                            if (person.isXlc()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                personSendDocXLCs.add(personSendDoc);
                            }
                            if (person.isDxl()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                personSendDocDXLs.add(personSendDoc);
                            }
                            if (person.isXem()) {
                                PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "LIENTHONG_LIST");
                                personSendDocXems.add(personSendDoc);
                            }
                        }
                    }
                    personSendDocXLCAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXLCs);
                    countXLC = personSendDocXLCAdapter.getCount();
                    if (countXLC > 0) {
                        layoutXLCTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < countXLC; i++) {
                            View view = personSendDocXLCAdapter.getView(i, null, null);
                            layoutXLC.addView(view);
                        }
                    } else {
                        layoutXLCTitle.setVisibility(View.GONE);
                    }
                    personSendDocDXLAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocDXLs);
                    countDXL = personSendDocDXLAdapter.getCount();
                    if (countDXL > 0) {
                        layoutPHTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < countDXL; i++) {
                            View view = personSendDocDXLAdapter.getView(i, null, null);
                            layoutPH.addView(view);
                        }
                    } else {
                        layoutPHTitle.setVisibility(View.GONE);
                    }
                    personSendDocXemAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXems);
                    countXem = personSendDocXemAdapter.getCount();
                    if (countXem > 0) {
                        layoutXemDBTitle.setVisibility(View.VISIBLE);
                        for (int i = 0; i < countXem; i++) {
                            View view = personSendDocXemAdapter.getView(i, null, null);
                            layoutXemDB.addView(view);
                        }
                    } else {
                        layoutXemDBTitle.setVisibility(View.GONE);
                    }
                    break;
            }
        } else {
            if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                for (Person person : listPersonEvent.getPersonNotifyList()) {
                    if (person.isXem()) {
                        PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "NOTIFY_LIST");
                        personSendDocXems.add(personSendDoc);
                    }
                }
                personSendDocXemAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXems);
                countXem = personSendDocXemAdapter.getCount();
                if (countXem > 0) {
                    layoutXemDBTitle.setVisibility(View.VISIBLE);
                    for (int i = 0; i < countXem; i++) {
                        View view = personSendDocXemAdapter.getView(i, null, null);
                        layoutXemDB.addView(view);
                    }
                } else {
                    layoutXemDBTitle.setVisibility(View.GONE);
                }
                layoutPHTitle.setVisibility(View.GONE);
                layoutXLCTitle.setVisibility(View.GONE);
            } else {
                if (listPersonEvent.getPersonProcessList() != null && listPersonEvent.getPersonProcessList().size() > 0) {
                    for (Person person : listPersonEvent.getPersonProcessList()) {
                        if (person.isXlc()) {
                            PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                            personSendDocXLCs.add(personSendDoc);
                        }
                        if (person.isDxl()) {
                            PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                            personSendDocDXLs.add(personSendDoc);
                        }
                        if (person.isXem()) {
                            PersonSendDoc personSendDoc = new PersonSendDoc(person.getId(), person.getName(), "PROCESS_LIST");
                            personSendDocXems.add(personSendDoc);
                        }
                    }
                }
                personSendDocXLCAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocXLCs);
                countXLC = personSendDocXLCAdapter.getCount();
                if (countXLC > 0) {
                    layoutXLCTitle.setVisibility(View.VISIBLE);
                    for (int i = 0; i < countXLC; i++) {
                        View view = personSendDocXLCAdapter.getView(i, null, null);
                        layoutXLC.addView(view);
                    }
                } else {
                    layoutXLCTitle.setVisibility(View.GONE);
                }
                personSendDocDXLAdapter = new PersonSendDocAdapter(this, R.layout.item_person_send_document, personSendDocDXLs);
                countDXL = personSendDocDXLAdapter.getCount();
                if (countDXL > 0) {
                    layoutPHTitle.setVisibility(View.VISIBLE);
                    for (int i = 0; i < countDXL; i++) {
                        View view = personSendDocDXLAdapter.getView(i, null, null);
                        layoutPH.addView(view);
                    }
                } else {
                    layoutPHTitle.setVisibility(View.GONE);
                }
                layoutXemDBTitle.setVisibility(View.GONE);
            }
        }
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void send() {
        hideSoftInput();
        TypePersonEvent typePersonEvent = EventBus.getDefault().getStickyEvent(TypePersonEvent.class);
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (typePersonEvent.getType().equals(Constants.TYPE_SEND_PERSON_PROCESS)) {
            List<Person> personProcessList = listPersonEvent.getPersonProcessList();
            List<Person> personLTList = listPersonEvent.getPersonLienThongList();
            String xlc = "";
            String dxl = "";
            String xem = "";
            if ((personProcessList != null && personProcessList.size() > 0) ||
                    personLTList != null && personLTList.size() > 0) {
                int index = -1;
                if (personProcessList != null && personProcessList.size() > 0) {
                    for (int i = 0; i < personProcessList.size(); i++) {
                        if (personProcessList.get(i).isXlc()) {
                            index = i;
                            xlc += personProcessList.get(i).getId() + ",";
                        }
                        if (personProcessList.get(i).isDxl()) {
                            dxl += personProcessList.get(i).getId() + ",";
                        }
                        if (personProcessList.get(i).isXem()) {
                            xem += personProcessList.get(i).getId() + ",";
                        }
                    }
                }
                String xlc_lt = "";
                String dxl_lt = "";
                String xem_lt = "";
                if (personLTList != null && personLTList.size() > 0) {
                    for (Person person : personLTList) {
                        if (person.isXlc()) {
                            xlc_lt += person.getId() + ",";
                        }
                        if (person.isDxl()) {
                            dxl_lt += person.getId() + ",";
                        }
                        if (person.isXem()) {
                            xem_lt += person.getId() + ",";
                        }
                    }
                }
                index = 0;//Không bắt validate người nhận XLC
                if (index > -1) {
                    ChangeProcessRequest changeProcessRequest = new ChangeProcessRequest();
                    TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                    changeProcessRequest.setDocId(typeChangeDocumentRequest.getDocId());
                    changeProcessRequest.setPrimaryProcess(!xlc.equals("") ? xlc.substring(0, xlc.length() - 1) : "");
                    changeProcessRequest.setCoevalProcess(!dxl.equals("") ? dxl.substring(0, dxl.length() - 1) : "");
                    changeProcessRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                    changeProcessRequest.setPrimaryInternal(!xlc_lt.equals("") ? xlc_lt.substring(0, xlc_lt.length() - 1) : "");
                    changeProcessRequest.setCoevalInternal(!dxl_lt.equals("") ? dxl_lt.substring(0, dxl_lt.length() - 1) : "");
                    changeProcessRequest.setReferInternal(!xem_lt.equals("") ? xem_lt.substring(0, xem_lt.length() - 1) : "");
                    changeProcessRequest.setComment(txtNote.getText().toString().trim());
                    changeProcessRequest.setType(Constants.TYPE_SEND_PROCESS_REQUEST);
                    changeProcessRequest.setSms(checkSMS.isChecked() ? 1 : 0);

                    //TODO: sửa code 15/11/2018 MinhDN
                    changeProcessRequest.setJob(checkAutoSendJob.isChecked()? 1 : 0);
                    if(tvHanXuLy.getText().equals(getString(R.string.str_hanXuLy))){
                        changeProcessRequest.setHanXuLy("");
                    }else {
                        changeProcessRequest.setHanXuLy(tvHanXuLy.getText().toString());
                    }
                    changeDocumentPresenter.changeProcess(changeProcessRequest);
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                }
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
            }

//            if (type == Constants.TYPE_SEND_NOTIFY) {
//                List<Person> personNotifyList = listPersonEvent.getPersonNotifyList();
//                String dongGui = "";
//                if (personNotifyList != null && personNotifyList.size() > 0) {
//                    for (Person person : personNotifyList) {
//                        if (person.isXlc()) {
//                            dongGui += person.getId() + ",";
//                        }
//                    }
//                    TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
//                    ChangeNotifyRequest changeNotifyRequest = new ChangeNotifyRequest();
//                    changeNotifyRequest.setDocId(typeChangeDocumentRequest.getDocId());
//                    changeNotifyRequest.setXlc(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
//                    changeNotifyRequest.setPhoiHop("");
//                    changeNotifyRequest.setComment(txtNote.getText().toString().trim());
//                    changeNotifyRequest.setType(Constants.TYPE_SEND_NOTIFY_REQUEST);
//                    changeDocumentPresenter.changeNotify(changeNotifyRequest);
//                } else {
//                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_NOTIFY), true, AlertDialogManager.INFO);
//                }
//            }
        } else {
            if (typePersonEvent.getType().equals(Constants.TYPE_SEND_VIEW)) {
                List<Person> personTBList = listPersonEvent.getPersonNotifyList();
                String xem = "";
                if (personTBList != null && personTBList.size() > 0) {
                    for (Person person : personTBList) {
                        if (person.isXem()) {
                            xem += person.getId() + ",";
                        }
                    }
                    ChangeDocumentNotifyRequest changeDocumentNotifyRequest = new ChangeDocumentNotifyRequest();
                    changeDocumentNotifyRequest.setDocId(typeChangeDocumentRequest.getDocId());
                    changeDocumentNotifyRequest.setComment(txtNote.getText().toString().trim());
                    changeDocumentNotifyRequest.setPrimaryProcess("");
                    changeDocumentNotifyRequest.setCoevalProcess("");
                    changeDocumentNotifyRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                    changeDocumentNotifyRequest.setPrimaryInternal("");
                    changeDocumentNotifyRequest.setCoevalInternal("");
                    changeDocumentNotifyRequest.setReferInternal("");
                    changeDocumentNotifyRequest.setType("1");
                    changeDocumentNotifyRequest.setSms(checkSMS.isChecked() ? 1 : 0);


                    changeDocumentPresenter.changeDocNotify(changeDocumentNotifyRequest);
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                }
            } else {
                TypeChangeEvent typeChangeEvent = EventBus.getDefault().getStickyEvent(TypeChangeEvent.class);
                List<TypeChangeDocument> typeChangeDocumentList = typeChangeEvent.getTypeChangeDocumentList();
                if (typeChangeDocumentList.get(typeChangeEvent.getSelected()).getNextStep().equals("get_cleck_publish")
                        && (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                        typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
                    List<Person> personXLCList = listPersonEvent.getPersonDirectList();
                    List<Person> personLTList = listPersonEvent.getPersonLienThongList();
                    String xlc = "";
                    String dxl = "";
                    String xem = "";
                    if ((personXLCList != null && personXLCList.size() > 0) ||
                            personLTList != null && personLTList.size() > 0) {
                        if (personXLCList != null && personXLCList.size() > 0) {
                            for (Person person : personXLCList) {
                                if (person.isXlc()) {
                                    xlc += person.getId() + ",";
                                }
                                if (person.isDxl()) {
                                    dxl += person.getId() + ",";
                                }
                                if (person.isXem()) {
                                    xem += person.getId() + ",";
                                }
                            }
                        }
                        String xlc_lt = "";
                        String dxl_lt = "";
                        String xem_lt = "";
                        if (personLTList != null && personLTList.size() > 0) {
                            for (Person person : personLTList) {
                                if (person.isXlc()) {
                                    xlc_lt += person.getId() + ",";
                                }
                                if (person.isDxl()) {
                                    dxl_lt += person.getId() + ",";
                                }
                                if (person.isXem()) {
                                    xem_lt += person.getId() + ",";
                                }
                            }
                        }
                        TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                        ChangeDocumentDirectRequest changeDocumentDirectRequest = new ChangeDocumentDirectRequest();
                        changeDocumentDirectRequest.setDocId(typeChangeDocumentRequest.getDocId());
                        changeDocumentDirectRequest.setComment(txtNote.getText().toString().trim());
                        changeDocumentDirectRequest.setPrimaryProcess(!xlc.equals("") ? xlc.substring(0, xlc.length() - 1) : "");
                        changeDocumentDirectRequest.setCoevalProcess(!dxl.equals("") ? dxl.substring(0, dxl.length() - 1) : "");
                        changeDocumentDirectRequest.setReferProcess(!xem.equals("") ? xem.substring(0, xem.length() - 1) : "");
                        changeDocumentDirectRequest.setPrimaryInternal(!xlc_lt.equals("") ? xlc_lt.substring(0, xlc_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setCoevalInternal(!dxl_lt.equals("") ? dxl_lt.substring(0, dxl_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setReferInternal(!xem_lt.equals("") ? xem_lt.substring(0, xem_lt.length() - 1) : "");
                        changeDocumentDirectRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                        changeDocumentDirectRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                        changeDocumentDirectRequest.setProcessDefinitionId(typeChangeDocumentRequest.getProcessDefinitionId());
                        changeDocumentDirectRequest.setActionType(String.valueOf(type));
                        changeDocumentDirectRequest.setSms(checkSMS.isChecked() ? 1 : 0);
                        //TODO: sửa code 15/11/2018 MinhDN
                        changeDocumentDirectRequest.setJob(checkAutoSendJob.isChecked()? 1 : 0);
                        if(tvHanXuLy.getText().equals(getString(R.string.str_hanXuLy))){
                            changeDocumentDirectRequest.setHanXuLy("");
                        }else {
                            changeDocumentDirectRequest.setHanXuLy(tvHanXuLy.getText().toString());
                        }
                        changeDocumentPresenter.changeDirect(changeDocumentDirectRequest);
                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON), true, AlertDialogManager.INFO);
                    }
                } else {
                    List<Person> personProcessList = listPersonEvent.getPersonProcessList();
                    List<Person> personSendList = listPersonEvent.getPersonSendList();
                    if (personProcessList != null && personProcessList.size() > 0) {
                        String dongXuLy = "";
                        int index = -1;
                        for (int i = 0; i < personProcessList.size(); i++) {
                            if (personProcessList.get(i).isXlc()) {
                                index = i;
                            } else {
                                dongXuLy += personProcessList.get(i).getId() + ",";
                            }
                        }
                        if (index > -1) {
                            String dongGui = "";
                            if (personSendList != null && personSendList.size() > 0) {
                                for (Person person : personSendList) {
                                    if (person.isXlc()) {
                                        dongGui += person.getId() + ",";
                                    }
                                }
                            }
                            TypeChangeDocumentRequest typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
                            if (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND)) {
                                ChangeDocumentSendRequest changeDocumentSendRequest = new ChangeDocumentSendRequest();
                                changeDocumentSendRequest.setDocId(typeChangeDocumentRequest.getDocId());
                                changeDocumentSendRequest.setChuTri(personProcessList.get(index).getId());
                                changeDocumentSendRequest.setPhoiHop(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                                changeDocumentSendRequest.setSFunc(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getNextStep());
                                changeDocumentSendRequest.setSApproved(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                                changeDocumentSendRequest.setSMore(txtNote.getText().toString().trim());
                                changeDocumentSendRequest.setAssigner("");
                                changeDocumentSendRequest.setAct(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                                changeDocumentSendRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                                changeDocumentSendRequest.setFormId("");
                                changeDocumentSendRequest.setSendType(String.valueOf(type));

                                //TODO: sửa code 15/11/2018 MinhDN
                                changeDocumentSendRequest.setSms(checkSMS.isChecked() ? 1 : 0);
                                changeDocumentSendRequest.setJob(checkAutoSendJob.isChecked()? 1 : 0);
                                if(tvHanXuLy.getText().equals(getString(R.string.str_hanXuLy))){
                                    changeDocumentSendRequest.setHanXuLy("");
                                }else {
                                    changeDocumentSendRequest.setHanXuLy(tvHanXuLy.getText().toString());
                                }
                                changeDocumentPresenter.changeSend(changeDocumentSendRequest);
                            }
                            if (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE)) {
                                ChangeDocumentReceiveRequest changeDocumentReceiveRequest = new ChangeDocumentReceiveRequest();
                                changeDocumentReceiveRequest.setDocId(typeChangeDocumentRequest.getDocId());
                                changeDocumentReceiveRequest.setChuTri(personProcessList.get(index).getId());
                                changeDocumentReceiveRequest.setApprovedValue(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getApprovedValue());
                                changeDocumentReceiveRequest.setDongXuLy(!dongXuLy.equals("") ? dongXuLy.substring(0, dongXuLy.length() - 1) : "");
                                changeDocumentReceiveRequest.setStrAction(typeChangeDocumentList.get(typeChangeEvent.getSelected()).getName());
                                changeDocumentReceiveRequest.setProcessDefinitionId(typeChangeDocumentRequest.getProcessDefinitionId());
                                changeDocumentReceiveRequest.setIsBanHanh("0");
                                changeDocumentReceiveRequest.setDongGui(!dongGui.equals("") ? dongGui.substring(0, dongGui.length() - 1) : "");
                                changeDocumentReceiveRequest.setComment(txtNote.getText().toString().trim());
                                changeDocumentReceiveRequest.setSendType(String.valueOf(type));
                                changeDocumentReceiveRequest.setSms(checkSMS.isChecked() ? 1 : 0);
                                //TODO: sửa code 15/11/2018 MinhDN
                                changeDocumentReceiveRequest.setJob(checkAutoSendJob.isChecked()? 1 : 0);
                                if(tvHanXuLy.getText().equals(getString(R.string.str_hanXuLy))){
                                    changeDocumentReceiveRequest.setHanXuLy("");
                                }else {
                                    changeDocumentReceiveRequest.setHanXuLy(tvHanXuLy.getText().toString());
                                }
                                changeDocumentPresenter.changeReceive(changeDocumentReceiveRequest);
                            }
                        } else {
                            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_PERSON_PROCESS), true, AlertDialogManager.INFO);
                    }
                }
            }
        }
    }

    @Override
    public void onSuccessChangeDoc() {
        EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
        EventBus.getDefault().removeStickyEvent(TypePersonEvent.class);
        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
        FinishEvent finishEvent = EventBus.getDefault().getStickyEvent(FinishEvent.class);
        finishEvent.setFinish(true);
        EventBus.getDefault().postSticky(finishEvent);
        Toast.makeText(this, getString(R.string.CHANGE_DOC_SUCCESS), Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onSuccessGroupPerson(List<PersonGroupChangeDocInfo> personGroupChangeDocInfos) {

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
            if (apiError.getMessage().equals("changeFail")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_DOC_TITLE_ERROR), getString(R.string.CHANGE_DOC_FAIL), true, AlertDialogManager.ERROR);
            }
            if (apiError.getMessage().contains("document_processed")) {
                Toast.makeText(NewSendDocumentActivity.this, getString(R.string.CHANGED_DOC), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            send();
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(NewSendDocumentActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        if (!NewSendDocumentActivity.this.isFinishing()) {
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
        if (!NewSendDocumentActivity.this.isFinishing()) {
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

    @OnClick({R.id.tv_han_xu_ly, R.id.checkAutoSendJob})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_han_xu_ly:
                showDatePicker();
                break;
            case R.id.checkAutoSendJob:
                break;
        }
    }

    private void showDatePicker() {
        Calendar nowBH = Calendar.getInstance();
        int mYear = nowBH.get(Calendar.YEAR);
        int mMonth = nowBH.get(Calendar.MONTH);
        int mDay = nowBH.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String strDay;
                if(dayOfMonth < 10){
                    strDay = "0" + dayOfMonth;
                } else {
                    strDay = String.valueOf(dayOfMonth);
                }
                String strMonth;
                if (monthOfYear + 1 < 10){
                    strMonth = "0" + String.valueOf(monthOfYear + 1);
                } else {
                    strMonth = String.valueOf(monthOfYear + 1);
                }
                String ngay = strDay + "/" + strMonth + "/" + year;
                tvHanXuLy.setText(ngay);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


}
