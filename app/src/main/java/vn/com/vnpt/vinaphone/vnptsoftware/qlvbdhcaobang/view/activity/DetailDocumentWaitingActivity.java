package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.AttachFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.LogAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.NewHistoryAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.RelatedDocumentAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.RelatedFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.DetailDocumentWaiting;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Event;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.FileAttach;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ExchangeHandlesInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changedocument.ChangeDocumentPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changedocument.IChangeDocumentPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentwaiting.DocumentWaitingPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentwaiting.IDocumentWaitingPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.KiThanhCongEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonPreEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.NhanWebViewEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocWaitingtEvent;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TitleTraoDoiXuLyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TraoDoiXuLyEvent;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDetailDocumentWaitingView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IGetTypeChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IHistoryDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class DetailDocumentWaitingActivity extends BaseActivity implements IDetailDocumentWaitingView, ILoginView, IGetTypeChangeDocumentView, IHistoryDocumentView {

    @BindView(R.id.tv_trichyeu)
    TextView tv_trichyeu;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_ngayden)
    TextView tv_ngayden;
    @BindView(R.id.tv_ngayden_label)
    TextView tv_ngayden_label;
    @BindView(R.id.tv_kh)
    TextView tvKH;
    @BindView(R.id.tv_so_ki_hieu_label)
    TextView tvKH_label;
    @BindView(R.id.tv_cqbh)
    TextView tvCQBH;
    @BindView(R.id.tv_cqbh_label)
    TextView tvCQBH_label;
    @BindView(R.id.tv_ngayvb)
    TextView tvNgayVB;
    @BindView(R.id.tv_ngayvb_label)
    TextView tvNgayVB_label;
    @BindView(R.id.tv_hinhthucvb)
    TextView tv_hinhthucvb;
    @BindView(R.id.tv_hinhthucvb_label)
    TextView tv_hinhthucvb_label;
    @BindView(R.id.tv_sovb)
    TextView tv_sovb;
    @BindView(R.id.tv_so_ioffice)
    TextView tv_so_ioffice;
    @BindView(R.id.tv_sovb_label)
    TextView tv_sovb_label;
    @BindView(R.id.tv_soden)
    TextView tv_soden;
    @BindView(R.id.tv_soden_label)
    TextView tv_soden_label;
    @BindView(R.id.tv_do_khan)
    TextView tvDoKhan;
    @BindView(R.id.tv_do_khan_label)
    TextView tvDoKhan_label;
    @BindView(R.id.tv_hanxuly)
    TextView tv_hanxuly;
    @BindView(R.id.tv_hanxuly_label)
    TextView tv_hanxuly_label;
    @BindView(R.id.tv_sotrang)
    TextView tv_sotrang;
    @BindView(R.id.tv_sotrang_label)
    TextView tv_sotrang_label;
    @BindView(R.id.tv_hinhthucgui)
    TextView tv_hinhthucgui;
    @BindView(R.id.tv_hinhthucgui_label)
    TextView tv_hinhthucgui_label;
    @BindView(R.id.tv_filedinhkem_label)
    TextView tv_filedinhkem_label;
    @BindView(R.id.tv_vblienquan)
    TextView tv_vblienquan;
    @BindView(R.id.layout_file_attach)
    LinearLayout layout_file_attach;
    @BindView(R.id.layout_file_related)
    LinearLayout layout_file_related;
    @BindView(R.id.layout_related_docs)
    LinearLayout layout_related_docs;
    @BindView(R.id.layout_log)
    LinearLayout layout_log;
    @BindView(R.id.layoutFileDK)
    LinearLayout layoutFileDK;
    @BindView(R.id.layoutAction)
    LinearLayout layoutAction;
    @BindView(R.id.layoutVanBan)
    LinearLayout layoutVanBan;
    @BindView(R.id.btnMove)
    Button btnMove;
    @BindView(R.id.btnMark)
    Button btnMark;
    @BindView(R.id.btnComment)
    Button btnComment;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.btnHistory)
    Button btnHistory;
    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;
    @BindView(R.id.tv_so)
    TextView tv_so;

    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnSend;
    private IDocumentWaitingPresenter documentWaitingPresenter = new DocumentWaitingPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean marked;
    private DocumentWaitingInfo newItem;
    private IChangeDocumentPresenter changeDocumentPresenter = new ChangeDocumentPresenterImpl(this);
    private TypeChangeDocumentRequest typeChangeDocumentRequest;
    private NewHistoryAdapter adapter;
    List<UnitLogInfo> logInfoList = new ArrayList<>();
    private DetailDocumentInfo detailDocumentInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_document_waiting);
        ButterKnife.bind(this);
        onNewIntent(getIntent());
//        getDetail();
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
                onBackPressed();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().removeStickyEvent(TypeChangeEvent.class);
                EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
                startActivity(new Intent(DetailDocumentWaitingActivity.this, SendDocumentActivity.class));
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(Constants.CONSTANTS_INTENT_DETAIL_INFOR)) {
                detailDocumentInfo = (DetailDocumentInfo) extras.getSerializable(Constants.CONSTANTS_INTENT_DETAIL_INFOR);
                newItem = (DocumentWaitingInfo) extras.getSerializable(Constants.CONSTANTS_INTENT_DOC_WATTING_INFOR);
                typeChangeDocumentRequest = (TypeChangeDocumentRequest) extras.getSerializable(Constants.CONSTANTS_INTENT_TYPE_CHANGE_DOC);
            }
        } else {
            detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            newItem = EventBus.getDefault().getStickyEvent(DocumentWaitingInfo.class);
            typeChangeDocumentRequest = EventBus.getDefault().getStickyEvent(TypeChangeDocumentRequest.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        layout_file_attach.removeAllViews();
        getDetail();
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().removeStickyEvent(DocumentWaitingInfo.class);
        EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
        EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
        EventBus.getDefault().removeStickyEvent(TraoDoiXuLyEvent.class);
        finish();
    }

    private void init() {
        setupToolbar();
        connectionDetector = new ConnectionDetector(this);
        tv_trichyeu.setTypeface(Application.getApp().getTypeface());
        tvTitle.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_ngayden.setTypeface(Application.getApp().getTypeface());
        tv_ngayden_label.setTypeface(Application.getApp().getTypeface());
        tvKH.setTypeface(Application.getApp().getTypeface());
        tvCQBH.setTypeface(Application.getApp().getTypeface());
        tvNgayVB.setTypeface(Application.getApp().getTypeface());
        tvDoKhan.setTypeface(Application.getApp().getTypeface());
        tv_hinhthucvb.setTypeface(Application.getApp().getTypeface());
        tvKH_label.setTypeface(Application.getApp().getTypeface());
        tvCQBH_label.setTypeface(Application.getApp().getTypeface());
        tvNgayVB_label.setTypeface(Application.getApp().getTypeface());
        tvDoKhan_label.setTypeface(Application.getApp().getTypeface());
        tv_hinhthucvb_label.setTypeface(Application.getApp().getTypeface());
        tv_sovb.setTypeface(Application.getApp().getTypeface());
        tv_sovb_label.setTypeface(Application.getApp().getTypeface());
        tv_soden.setTypeface(Application.getApp().getTypeface());
        tv_soden_label.setTypeface(Application.getApp().getTypeface());
        tv_hanxuly.setTypeface(Application.getApp().getTypeface());
        tv_hanxuly_label.setTypeface(Application.getApp().getTypeface());
        tv_sotrang.setTypeface(Application.getApp().getTypeface());
        tv_sotrang_label.setTypeface(Application.getApp().getTypeface());
        tv_hinhthucgui.setTypeface(Application.getApp().getTypeface());
        tv_hinhthucgui_label.setTypeface(Application.getApp().getTypeface());
        tv_filedinhkem_label.setTypeface(Application.getApp().getTypeface());
        tv_so_ioffice.setTypeface(Application.getApp().getTypeface());

        tv_vblienquan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        layoutVanBan.setVisibility(View.GONE);
        layoutFileDK.setVisibility(View.GONE);
        btnFinish.setVisibility(View.GONE);
        if (newItem != null) {
            layoutAction.setVisibility(View.VISIBLE);
            if (newItem.getIsChuTri().equals(Constants.SEND_RULE)) {
                btnMove.setVisibility(View.VISIBLE);
            } else {
                btnMove.setVisibility(View.GONE);
            }
            if (!newItem.getIsCheck().equals(Constants.NOT_MARKED)) {
                marked = true;
                btnMark.setText("Hủy đánh dấu");
            } else {
                marked = false;
                btnMark.setText("Đánh dấu");
            }
//            if (newItem.getIsComment().equals(Constants.COMMENT_RULE)) {
//                btnComment.setVisibility(View.VISIBLE);
//            } else {
//                btnComment.setVisibility(View.GONE);
//            }
            btnComment.setVisibility(View.GONE);
        } else {
            layoutAction.setVisibility(View.GONE);
        }
        adapter = new NewHistoryAdapter(this, logInfoList, Constants.PROCESSED_HISTORY);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerHistory.setFocusable(false);
        recyclerHistory.setNestedScrollingEnabled(false);
        recyclerHistory.setLayoutManager(mLayoutManager);
        recyclerHistory.setItemAnimator(new DefaultItemAnimator());
        recyclerHistory.setAdapter(adapter);
    }

    private void checkFinish(int id) {
        documentWaitingPresenter.checkFinish(id);
    }

    private void getDetail() {
        try {
            if (connectionDetector.isConnectingToInternet()) {
                if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                    // lay ma id truyen vao trao doi xu ly
                    documentWaitingPresenter.getDetail(Integer.parseInt(detailDocumentInfo.getId()));
                    checkFinish(Integer.parseInt(detailDocumentInfo.getId()));

                    //                documentWaitingDao.onGetAttachFiles(Integer.parseInt(newItem.getId()), callFinishedListener);
                }
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void getAttachFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getAttachFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedDocs() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getRelatedDocs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getRelatedFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillRelatedFiles(List<RelatedFileInfo> relatedFileInfos) {
        layout_file_related.removeAllViews();
        RelatedFileAdapter relatedDocumentAdapter = new RelatedFileAdapter(this, R.layout.item_file_related_list, relatedFileInfos);
        int adapterCount = relatedDocumentAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = relatedDocumentAdapter.getView(i, null, null);
            layout_file_related.addView(item);
        }
    }

    private void getLogs() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_WAITING)) {
                documentWaitingPresenter.getLogs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillDetail(DetailDocumentWaiting detailDocumentWaiting) {
        tvTitle.setText(detailDocumentWaiting.getTrichYeu());
        if (detailDocumentWaiting.getDonViBanHanh() != null && !detailDocumentWaiting.getDonViBanHanh().trim().equals("")) {
            tvCQBH.setText(detailDocumentWaiting.getDonViBanHanh());
        } else {
            tvCQBH.setVisibility(View.GONE);
            tvCQBH_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoKiHieu() != null && !detailDocumentWaiting.getSoKiHieu().trim().equals("")) {
            tvKH.setText(detailDocumentWaiting.getSoKiHieu());
        } else {
            tvKH.setVisibility(View.GONE);
            tvKH_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getNgayDenDi() != null && !detailDocumentWaiting.getNgayDenDi().trim().equals("")) {
            tv_ngayden.setText(detailDocumentWaiting.getNgayDenDi());
        } else {
            tv_ngayden.setVisibility(View.GONE);
            tv_ngayden_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getNgayVanBan() != null && !detailDocumentWaiting.getNgayVanBan().trim().equals("")) {
            tvNgayVB.setText(detailDocumentWaiting.getNgayVanBan());
        } else {
            tvNgayVB.setVisibility(View.GONE);
            tvNgayVB_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHinhThucVanBan() != null && !detailDocumentWaiting.getHinhThucVanBan().trim().equals("")) {
            tv_hinhthucvb.setText(detailDocumentWaiting.getHinhThucVanBan());
        } else {
            tv_hinhthucvb.setVisibility(View.GONE);
            tv_hinhthucvb_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoVanBan() != null && !detailDocumentWaiting.getSoVanBan().trim().equals("")) {
            tv_sovb.setText(detailDocumentWaiting.getSoVanBan());
        } else {
            tv_sovb.setVisibility(View.GONE);
            tv_sovb_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoDenDi() > 0) {
            tv_soden.setText(String.valueOf(detailDocumentWaiting.getSoDenDi()));
        } else {
            tv_soden.setVisibility(View.GONE);
            tv_soden_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getDoUuTien() != null && !detailDocumentWaiting.getDoUuTien().trim().equals("")) {
            tvDoKhan.setText(detailDocumentWaiting.getDoUuTien());
        } else {
            tvDoKhan.setVisibility(View.GONE);
            tvDoKhan_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHanGiaiQuyet() != null && !detailDocumentWaiting.getHanGiaiQuyet().trim().equals("")) {
            tv_hanxuly.setText(detailDocumentWaiting.getHanGiaiQuyet());
        } else {
            tv_hanxuly.setVisibility(View.GONE);
            tv_hanxuly_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getSoTrang() > 0) {
            tv_sotrang.setText(String.valueOf(detailDocumentWaiting.getSoTrang()));
        } else {
            tv_sotrang.setVisibility(View.GONE);
            tv_sotrang_label.setVisibility(View.GONE);
        }
        if (detailDocumentWaiting.getHinhThucGui() != null && !detailDocumentWaiting.getHinhThucGui().trim().equals("")) {
            tv_hinhthucgui.setText(detailDocumentWaiting.getHinhThucGui());
        } else {
            tv_hinhthucgui.setVisibility(View.GONE);
            tv_hinhthucgui_label.setVisibility(View.GONE);
        }

        if (detailDocumentWaiting.getIoffice() != null && !detailDocumentWaiting.getIoffice().trim().equals("")) {
            tv_so.setText(detailDocumentWaiting.getIoffice());
        } else {
            tv_so.setVisibility(View.GONE);
            tv_so_ioffice.setVisibility(View.GONE);
        }
    }

    private void fillAttachFiles(List<AttachFileInfo> attachFileInfoList) {

        if (attachFileInfoList != null && attachFileInfoList.size() > 0) {
            layoutFileDK.setVisibility(View.VISIBLE);

            AttachFileAdapter attachFileAdapter = new AttachFileAdapter(this, R.layout.item_file_attach_list, attachFileInfoList);
            int adapterCount = attachFileAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = attachFileAdapter.getView(i, null, null);

                layout_file_attach.addView(item);
            }
        } else {
            layoutFileDK.setVisibility(View.GONE);
        }
    }

    private void fillRelatedDocs(List<RelatedDocumentInfo> relatedDocumentInfoList) {
        if (relatedDocumentInfoList != null && relatedDocumentInfoList.size() > 0) {
            layout_related_docs.removeAllViews();
            layoutVanBan.setVisibility(View.VISIBLE);
            RelatedDocumentAdapter relatedDocumentAdapter = new RelatedDocumentAdapter(this, R.layout.item_related_doc_list, relatedDocumentInfoList);
            int adapterCount = relatedDocumentAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = relatedDocumentAdapter.getView(i, null, null);
                layout_related_docs.addView(item);
            }
        } else {
            layoutVanBan.setVisibility(View.GONE);
        }
    }

    private void fillLogs(List<UnitLogInfo> logInfoList) {
        LogAdapter logAdapter = new LogAdapter(this, R.layout.item_log_list, logInfoList);
        int adapterCount = logAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = logAdapter.getView(i, null, null);
            layout_log.addView(item);
        }
    }

    private void comment() {
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.dialog_comment, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 64);
        dialog.getWindow().setBackgroundDrawable(inset);
        dialog.setView(view);
        final EditText txtComment = (EditText) view.findViewById(R.id.txtComment);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        Button btnHuy = (Button) view.findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput();
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (connectionDetector.isConnectingToInternet()) {
                    if (txtComment.getText() != null && !txtComment.getText().toString().trim().equals("")) {
                        hideSoftInput();
                        documentWaitingPresenter.comment(new CommentDocumentRequest(detailDocumentInfo.getId(), txtComment.getText().toString().trim()));
                    } else {
                        AlertDialogManager.showAlertDialog(DetailDocumentWaitingActivity.this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NOT_COMMENT), true, AlertDialogManager.INFO);
                    }
                } else {
                    AlertDialogManager.showAlertDialog(DetailDocumentWaitingActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        });
        dialog.show();
    }

    private void mark() {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.mark(Integer.parseInt(newItem.getId()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void finishDoc() {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.finish(Integer.parseInt(newItem.getId()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof DetailDocumentWaiting) {
            DetailDocumentWaiting detailDocumentWaiting = (DetailDocumentWaiting) object;
            fillDetail(detailDocumentWaiting);
            getAttachFiles();
            getRelatedDocs();
            getRelatedFiles();
            getLogs();
        }
        if (object instanceof List) {
            List<Object> lstObj = (List) object;
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof AttachFileInfo) {
                List<AttachFileInfo> attachFileInfoList = (List<AttachFileInfo>) object;
                fillAttachFiles(attachFileInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof RelatedFileInfo) {
                List<RelatedFileInfo> attachFileInfoList = (List<RelatedFileInfo>) object;
                fillRelatedFiles(attachFileInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof RelatedDocumentInfo) {
                List<RelatedDocumentInfo> relatedDocumentInfoList = (List<RelatedDocumentInfo>) object;
                fillRelatedDocs(relatedDocumentInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof UnitLogInfo) {
                if (logInfoList != null && logInfoList.size() > 0) {
                    logInfoList.clear();
                }
                List<UnitLogInfo> logInfoListNew = (List<UnitLogInfo>) object;
                logInfoList.addAll(logInfoListNew);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onComment() {
        Toast.makeText(this, getString(R.string.COMMENT_SUCCESS), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onSuccess(List<UnitLogInfo> unitLogInfoList) {

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
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage() != null && !apiError.getMessage().trim().equals("") ? apiError.getMessage().trim() : "Có lỗi xảy ra!\nVui lòng thử lại sau!", true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        getDetail();
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(DetailDocumentWaitingActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSuccessTypeChange(final List<TypeChangeDocument> typeChangeDocumentList) {

        if (typeChangeDocumentList != null && typeChangeDocumentList.size() > 0) {
            try {
                TraoDoiXuLyEvent event = EventBus.getDefault().getStickyEvent(TraoDoiXuLyEvent.class);
                if (event.getTraodoi() == 1) {
                    TypeChangeDocument typeAddNew = new TypeChangeDocument();
                    typeAddNew.setType(Constants.TYPE_COMMENT_VIEW);
                    typeAddNew.setName("Trao đổi thông tin");
                    typeChangeDocumentList.add(typeAddNew);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (typeChangeDocumentList.size() == 1) {
                ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                if (listPersonEvent == null) {
                    listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
                }
                int type = typeChangeDocumentList.get(0).getType();
                if (type == Constants.TYPE_SEND_NOTIFY || type == Constants.TYPE_SEND_PROCESS) {
//                        if (type == Constants.TYPE_SEND_NOTIFY) {
//                            EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_NOTIFY));
//                            listPersonEvent.setPersonProcessList(null);
//                            listPersonEvent.setPersonSendList(null);
//                            listPersonEvent.setPersonDirectList(null);
//                        }
                    if (type == Constants.TYPE_SEND_PROCESS) {
                        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_PERSON_PROCESS));
                        listPersonEvent.setPersonNotifyList(null);
                        listPersonEvent.setPersonSendList(null);
                        listPersonEvent.setPersonDirectList(null);
                    }
                } else if (type == Constants.TYPE_COMMENT_VIEW) {
                    startActivity(new Intent(DetailDocumentWaitingActivity.this, ExchangeHandlesActivity.class));
                    return;
                } else {
                    if (typeChangeDocumentList.get(0).getNextStep().equals("get_cleck_publish")
                            && (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                            typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
                        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_DIRECT));
                        listPersonEvent.setPersonNotifyList(null);
                        listPersonEvent.setPersonProcessList(null);
                        listPersonEvent.setPersonSendList(null);
                    } else {
                        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_PROCESS));
                        EventBus.getDefault().postSticky(new ListProcessRequest(typeChangeDocumentList.get(0).getNextStep(),
                                typeChangeDocumentList.get(0).getRoleId(), typeChangeDocumentList.get(0).getApprovedValue(),
                                typeChangeDocumentRequest.getDocId(), ""));
                        listPersonEvent.setPersonNotifyList(null);
                        listPersonEvent.setPersonDirectList(null);
                        listPersonEvent.setPersonLienThongList(null);
                    }
                }
                EventBus.getDefault().postSticky(listPersonEvent);
                EventBus.getDefault().postSticky(new FinishEvent(0, false));
                EventBus.getDefault().postSticky(new TypeChangeEvent("", 0, typeChangeDocumentList));
                listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(DetailDocumentWaitingActivity.this);
                ListPersonPreEvent listPersonPreEvent = applicationSharedPreferences.getPersonPre();
                listPersonPreEvent.setPersonProcessPreList(listPersonEvent.getPersonProcessList());
                listPersonPreEvent.setPersonSendPreList(listPersonEvent.getPersonSendList());
                listPersonPreEvent.setPersonNotifyPreList(listPersonEvent.getPersonNotifyList());
                Application.getApp().getAppPrefs().setPersonPre(listPersonPreEvent);
                startActivity(new Intent(DetailDocumentWaitingActivity.this, SelectPersonActivity.class));
                //finish();
            } else {
                List<String> labels = new ArrayList<String>();
                for (int i = 0; i < typeChangeDocumentList.size(); i++) {
                    labels.add(typeChangeDocumentList.get(i).getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.weight_table_menu, R.id.textViewTableMenuItem, labels);
                final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
                listPopupWindow.setAdapter(adapter);
                listPopupWindow.setAnchorView(btnMove);
                listPopupWindow.setWidth(550);
                listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
                listPopupWindow.setHorizontalOffset(-402); // margin left
                listPopupWindow.setVerticalOffset(-20);
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                        if (listPersonEvent == null) {
                            listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
                        }
                        int type = typeChangeDocumentList.get(i).getType();


                        if (type == Constants.TYPE_SEND_NOTIFY || type == Constants.TYPE_SEND_PROCESS) {
//                        if (type == Constants.TYPE_SEND_NOTIFY) {
//                            EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_NOTIFY));
//                            listPersonEvent.setPersonProcessList(null);
//                            listPersonEvent.setPersonSendList(null);
//                            listPersonEvent.setPersonDirectList(null);
//                        }
                            if (type == Constants.TYPE_SEND_PROCESS) {
                                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_PERSON_PROCESS));
                                listPersonEvent.setPersonNotifyList(null);
                                listPersonEvent.setPersonSendList(null);
                                listPersonEvent.setPersonDirectList(null);
                            }
                        } else if (type == Constants.TYPE_COMMENT_VIEW) {
                            EventBus.getDefault().postSticky(new TitleTraoDoiXuLyEvent(tvTitle.getText().toString()));
                            startActivity(new Intent(DetailDocumentWaitingActivity.this, ExchangeHandlesActivity.class));
                            listPopupWindow.dismiss();
                            return;
                        } else {
                            if (typeChangeDocumentList.get(i).getNextStep().equals("get_cleck_publish")
                                    && (typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_RECEIVE) ||
                                    typeChangeDocumentRequest.getType().equals(Constants.DOCUMENT_SEND))) {
                                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_DIRECT));
                                listPersonEvent.setPersonNotifyList(null);
                                listPersonEvent.setPersonProcessList(null);
                                listPersonEvent.setPersonSendList(null);
                            } else {
                                EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_PERSON_PROCESS));
                                EventBus.getDefault().postSticky(new ListProcessRequest(typeChangeDocumentList.get(i).getNextStep(),
                                        typeChangeDocumentList.get(i).getRoleId(), typeChangeDocumentList.get(i).getApprovedValue(),
                                        typeChangeDocumentRequest.getDocId(), ""));
                                listPersonEvent.setPersonNotifyList(null);
                                listPersonEvent.setPersonDirectList(null);
                                listPersonEvent.setPersonLienThongList(null);
                            }
                        }
                        EventBus.getDefault().postSticky(listPersonEvent);
                        EventBus.getDefault().postSticky(new TypeChangeEvent("", i, typeChangeDocumentList));
                        listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
                        ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(DetailDocumentWaitingActivity.this);
                        ListPersonPreEvent listPersonPreEvent = applicationSharedPreferences.getPersonPre();
                        listPersonPreEvent.setPersonProcessPreList(listPersonEvent.getPersonProcessList());
                        listPersonPreEvent.setPersonSendPreList(listPersonEvent.getPersonSendList());
                        listPersonPreEvent.setPersonNotifyPreList(listPersonEvent.getPersonNotifyList());
                        Application.getApp().getAppPrefs().setPersonPre(listPersonPreEvent);
                        EventBus.getDefault().postSticky(new FinishEvent(0, false));
                        startActivity(new Intent(DetailDocumentWaitingActivity.this, SelectPersonActivity.class));
                        //finish();
                        listPopupWindow.dismiss();
                    }
                });
                listPopupWindow.show();
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), "Không thể thực hiện chuyển văn bản", true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorTypeChange(APIError apiError) {
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), apiError.getMessage(), true, AlertDialogManager.ERROR);
    }

    @Override
    public void showProgress() {
        if (DetailDocumentWaitingActivity.this.isFinishing()) { // or call isFinishing() if min sdk version < 17
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
        if (!DetailDocumentWaitingActivity.this.isFinishing()) {
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

    @Override
    public void onMark() {
        if (marked) {
            marked = false;
            btnMark.setText("Đánh dấu");
            Toast.makeText(this, getString(R.string.NOT_MARKED_SUCCESS), Toast.LENGTH_LONG).show();
        } else {
            marked = true;
            btnMark.setText("Hủy đánh dấu");
            Toast.makeText(this, getString(R.string.MARKED_SUCCESS), Toast.LENGTH_LONG).show();
        }
        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
    }

    @Override
    public void onFinish() {
        Toast.makeText(this, getString(R.string.FINISH_SUCCESS), Toast.LENGTH_LONG).show();
        EventBus.getDefault().postSticky(new ReloadDocWaitingtEvent(true));
        finish();
    }

    @Override
    public void onCheckFinish(boolean isFinish) {
        if (isFinish) {
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnFinish.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btnMove, R.id.btnMark, R.id.btnComment, R.id.btnFinish, R.id.btnHistory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMove:
                ApplicationSharedPreferences applicationSharedPreferences = new ApplicationSharedPreferences(this);
                applicationSharedPreferences.setPersonPre(new ListPersonPreEvent(null, null, null));

                changeDocumentPresenter.getTypeChangeDocument(new TypeChangeDocRequest(typeChangeDocumentRequest.getDocId(), typeChangeDocumentRequest.getProcessDefinitionId()));
                break;
            case R.id.btnMark:
                mark();
                break;
            case R.id.btnComment:
                if (newItem.getCongVanDenDi().equals(Constants.DOCUMENT_SEND)) {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.NO_COMMENT_DOC_SEND), true, AlertDialogManager.INFO);
                }
                if (newItem.getCongVanDenDi().equals(Constants.DOCUMENT_RECEIVE)) {
                    comment();
                }
                break;
            case R.id.btnFinish:
                finishDoc();
                break;
            case R.id.btnHistory:
                EventBus.getDefault().postSticky(new DetailDocumentInfo(newItem.getId(), Constants.DOCUMENT_WAITING, null));
                startActivity(new Intent(this, NewHistoryDocumentActivity.class));
                break;
        }
    }

    public void hideSoftInput() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FinishEvent finishEvent) {
        if (finishEvent != null && finishEvent.getType() == 0 && finishEvent.isFinish()) {
            EventBus.getDefault().removeStickyEvent(FinishEvent.class);
            finish();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(KiThanhCongEvent kiThanhCongEvent) {
        if (kiThanhCongEvent != null && kiThanhCongEvent.getKyThanhCong() == 1) {
            EventBus.getDefault().removeStickyEvent(KiThanhCongEvent.class);
            layout_file_attach.removeAllViews();
            getDetail();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().postSticky(new KiThanhCongEvent(0));
    }

}
