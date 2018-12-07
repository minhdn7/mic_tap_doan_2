package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.DetailDocumentProcessed;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentProcessedInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentprocessed.DocumentProcessedPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentprocessed.IDocumentProcessedPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.CheckRecoverEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonPreEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocProcessedEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TitleTraoDoiXuLyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TraoDoiXuLyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDetailDocumentProcessedView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class DetailDocumentProcessedActivity extends BaseActivity implements IDetailDocumentProcessedView, ILoginView {

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
    @BindView(R.id.tv_comment_label)
    TextView tv_comment_label;
    @BindView(R.id.layout_file_attach)
    LinearLayout layout_file_attach;
    @BindView(R.id.layout_related_docs)
    LinearLayout layout_related_docs;
    @BindView(R.id.layout_log)
    LinearLayout layout_log;
    @BindView(R.id.layout_file_related)
    LinearLayout layout_file_related;
    @BindView(R.id.layoutFileDK)
    LinearLayout layoutFileDK;
    @BindView(R.id.layoutVanBan)
    LinearLayout layoutVanBan;
    @BindView(R.id.btnMark)
    Button btnMark;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.btnHistory)
    Button btnHistory;
    @BindView(R.id.recycler_history)
    RecyclerView recyclerHistory;

    @BindView(R.id.tv_so_ioffice)
    TextView tv_so_ioffice;
    @BindView(R.id.tv_sovb_of)
    TextView tv_sovb_of;

    private Toolbar toolbar;
    private ImageView btnBack;
    private ImageView btnLayLai;
    private IDocumentProcessedPresenter documentProcessedPresenter = new DocumentProcessedPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean marked;
    private DocumentProcessedInfo newItem;
    private NewHistoryAdapter adapter;
    List<UnitLogInfo> logInfoList = new ArrayList<>();
    private DetailDocumentInfo detailDocumentInfo;
    private List<String> labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_document_processed);
        ButterKnife.bind(this);
        onNewIntent(getIntent());
        init();
        getDetail();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            if (extras.containsKey(Constants.CONSTANTS_INTENT_DETAIL_INFOR)) {
                detailDocumentInfo = (DetailDocumentInfo) extras.getSerializable(Constants.CONSTANTS_INTENT_DETAIL_INFOR);
                newItem = (DocumentProcessedInfo) extras.getSerializable(Constants.CONSTANTS_INTENT_DOC_PROCESSED_INFOR);
            }
        } else {
            detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            newItem = EventBus.getDefault().getStickyEvent(DocumentProcessedInfo.class);
        }
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
        tv_vblienquan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_comment_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        layoutVanBan.setVisibility(View.GONE);
        layoutFileDK.setVisibility(View.GONE);

        tv_sovb_of.setTypeface(Application.getApp().getTypeface());
        tv_so_ioffice.setTypeface(Application.getApp().getTypeface());


        if (newItem != null) {
            btnMark.setVisibility(View.VISIBLE);
            if (!newItem.getIsCheck().equals(Constants.NOT_MARKED)) {
                marked = true;
                btnMark.setText("Hủy đánh dấu");
            } else {
                marked = false;
                btnMark.setText("Đánh dấu");
            }
        } else {
            btnMark.setVisibility(View.GONE);
        }
        btnSend.setVisibility(View.GONE);
        adapter = new NewHistoryAdapter(this, logInfoList, Constants.PROCESSED_HISTORY);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerHistory.setFocusable(false);
        recyclerHistory.setNestedScrollingEnabled(false);
        recyclerHistory.setLayoutManager(mLayoutManager);
        recyclerHistory.setItemAnimator(new DefaultItemAnimator());
        recyclerHistory.setAdapter(adapter);
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
        setTitle(getString(R.string.DETAIL_DOCUMENT_LABEL));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnLayLai = (ImageView) toolbar.findViewById(R.id.btnLayLai);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //checkRecover();
        btnLayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recover();
            }
        });
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().removeStickyEvent(DocumentProcessedInfo.class);
        EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
        EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
        finish();
    }

    private void checkChange() {
        EventBus.getDefault().postSticky(new StepPre("checkChange"));
        documentProcessedPresenter.checkChange(Integer.parseInt(detailDocumentInfo.getId()));
    }

    private void checkRecover() {
        EventBus.getDefault().postSticky(new StepPre("checkRecover"));
        EventBus.getDefault().postSticky(new CheckRecoverEvent(Integer.parseInt(detailDocumentInfo.getId()), detailDocumentInfo.getRecover()));
        documentProcessedPresenter.checkRecover(detailDocumentInfo.getRecover(), Integer.parseInt(detailDocumentInfo.getId()));
    }

    private void recover() {
        EventBus.getDefault().postSticky(new StepPre("recover"));
        documentProcessedPresenter.recover(detailDocumentInfo.getRecover(), Integer.parseInt(detailDocumentInfo.getId()));
    }

    private void getDetail() {
        EventBus.getDefault().postSticky(new StepPre("getDetail"));
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_PROCESSED)) {
                documentProcessedPresenter.getDetail(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getAttachFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_PROCESSED)) {
                documentProcessedPresenter.getAttachFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedDocs() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_PROCESSED)) {
                documentProcessedPresenter.getRelatedDocs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getLogs() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_PROCESSED)) {
                documentProcessedPresenter.getLogs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillDetail(DetailDocumentProcessed detailDocumentProcessed) {
        tvTitle.setText(detailDocumentProcessed.getTrichYeu());
        if (detailDocumentProcessed.getDonViBanHanh() != null && !detailDocumentProcessed.getDonViBanHanh().trim().equals("")) {
            tvCQBH.setText(detailDocumentProcessed.getDonViBanHanh());
        } else {
            tvCQBH.setVisibility(View.GONE);
            tvCQBH_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getSoKiHieu() != null && !detailDocumentProcessed.getSoKiHieu().trim().equals("")) {
            tvKH.setText(detailDocumentProcessed.getSoKiHieu());
        } else {
            tvKH.setVisibility(View.GONE);
            tvKH_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getNgayDenDi() != null && !detailDocumentProcessed.getNgayDenDi().trim().equals("")) {
            tv_ngayden.setText(detailDocumentProcessed.getNgayDenDi());
        } else {
            tv_ngayden.setVisibility(View.GONE);
            tv_ngayden_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getNgayVanBan() != null && !detailDocumentProcessed.getNgayVanBan().trim().equals("")) {
            tvNgayVB.setText(detailDocumentProcessed.getNgayVanBan());
        } else {
            tvNgayVB.setVisibility(View.GONE);
            tvNgayVB_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getHinhThucVanBan() != null && !detailDocumentProcessed.getHinhThucVanBan().trim().equals("")) {
            tv_hinhthucvb.setText(detailDocumentProcessed.getHinhThucVanBan());
        } else {
            tv_hinhthucvb.setVisibility(View.GONE);
            tv_hinhthucvb_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getSoVanBan() != null && !detailDocumentProcessed.getSoVanBan().trim().equals("")) {
            tv_sovb.setText(detailDocumentProcessed.getSoVanBan());
        } else {
            tv_sovb.setVisibility(View.GONE);
            tv_sovb_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getSoDenDi() > 0) {
            tv_soden.setText(String.valueOf(detailDocumentProcessed.getSoDenDi()));
        } else {
            tv_soden.setVisibility(View.GONE);
            tv_soden_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getDoUuTien() != null && !detailDocumentProcessed.getDoUuTien().trim().equals("")) {
            tvDoKhan.setText(detailDocumentProcessed.getDoUuTien());
        } else {
            tvDoKhan.setVisibility(View.GONE);
            tvDoKhan_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getHanGiaiQuyet() != null && !detailDocumentProcessed.getHanGiaiQuyet().trim().equals("")) {
            tv_hanxuly.setText(detailDocumentProcessed.getHanGiaiQuyet());
        } else {
            tv_hanxuly.setVisibility(View.GONE);
            tv_hanxuly_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getSoTrang() > 0) {
            tv_sotrang.setText(String.valueOf(detailDocumentProcessed.getSoTrang()));
        } else {
            tv_sotrang.setVisibility(View.GONE);
            tv_sotrang_label.setVisibility(View.GONE);
        }
        if (detailDocumentProcessed.getHinhThucGui() != null && !detailDocumentProcessed.getHinhThucGui().trim().equals("")) {
            tv_hinhthucgui.setText(detailDocumentProcessed.getHinhThucGui());
        } else {
            tv_hinhthucgui.setVisibility(View.GONE);
            tv_hinhthucgui_label.setVisibility(View.GONE);
        }

        if (detailDocumentProcessed.getIoffice() != null && !detailDocumentProcessed.getIoffice().trim().equals("")) {
            tv_sovb_of.setText(detailDocumentProcessed.getIoffice());
        } else {
            tv_sovb_of.setVisibility(View.GONE);
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

    private void getRelatedFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_PROCESSED)) {
                documentProcessedPresenter.getRelatedFiles(Integer.parseInt(detailDocumentInfo.getId()));
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

    private void fillRelatedDocs(List<RelatedDocumentInfo> relatedDocumentInfoList) {
        if (relatedDocumentInfoList != null && relatedDocumentInfoList.size() > 0) {
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

    @Override
    public void onSuccess(Object object) {
        if (object instanceof DetailDocumentProcessed) {
            DetailDocumentProcessed detailDocumentProcessed = (DetailDocumentProcessed) object;
            fillDetail(detailDocumentProcessed);
            getAttachFiles();
            getRelatedDocs();
            getRelatedFiles();
            getLogs();
            checkChange();
        }
        if (object instanceof List) {
            List<Object> lstObj = (List) object;
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof AttachFileInfo) {
                List<AttachFileInfo> attachFileInfoList = (List<AttachFileInfo>) object;
                fillAttachFiles(attachFileInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof RelatedDocumentInfo) {
                List<RelatedDocumentInfo> relatedDocumentInfoList = (List<RelatedDocumentInfo>) object;
                fillRelatedDocs(relatedDocumentInfoList);
            }
            if (lstObj != null && lstObj.size() > 0 && lstObj.get(0) instanceof RelatedFileInfo) {
                List<RelatedFileInfo> attachFileInfoList = (List<RelatedFileInfo>) object;
                fillRelatedFiles(attachFileInfoList);
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
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            String step = EventBus.getDefault().getStickyEvent(StepPre.class).getCall();
            if (step.equals("recover")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.RECOVER_DOC_TITLE_ERROR), getString(R.string.DOCUMENT_RECOVERED_FAIL), true, AlertDialogManager.ERROR);
            }
            if (step.equals("checkChange")) {
                btnSend.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onSuccessCheckRecover(boolean recover) {
        if (recover) {
            btnLayLai.setVisibility(View.VISIBLE);
        } else {
            btnLayLai.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessRecover(boolean recover) {
        if (recover) {
            btnLayLai.setVisibility(View.GONE);
            AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_SUCCESS), getString(R.string.DOCUMENT_RECOVERED_SUCCESS), true, AlertDialogManager.SUCCESS);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.RECOVER_DOC_TITLE_ERROR), getString(R.string.DOCUMENT_RECOVERED_FAIL), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void showProgress() {

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
        if (!DetailDocumentProcessedActivity.this.isFinishing()) {
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
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        String step = EventBus.getDefault().getStickyEvent(StepPre.class).getCall();
        switch (step) {
            case "getDetail":
                getDetail();
                break;
            case "checkRecover":
                //checkRecover();
                break;
            case "recover":
                //recover();
                break;
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(DetailDocumentProcessedActivity.this, LoginActivity.class));
        finish();
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
        EventBus.getDefault().postSticky(new ReloadDocProcessedEvent(true));
    }

    @Override
    public void onChange() {
        btnSend.setVisibility(View.VISIBLE);
    }

    private void mark() {
        if (connectionDetector.isConnectingToInternet()) {
            documentProcessedPresenter.mark(Integer.parseInt(newItem.getId()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void send() {
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent == null) {
            listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
        }
        List<TypeChangeDocument> typeChangeDocumentList = new ArrayList<>();
        typeChangeDocumentList.add(new TypeChangeDocument("true"));
        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_PERSON_PROCESS));
        listPersonEvent.setPersonNotifyList(null);
        listPersonEvent.setPersonSendList(null);
        listPersonEvent.setPersonDirectList(null);
        EventBus.getDefault().postSticky(new FinishEvent(1, false));
        EventBus.getDefault().postSticky(listPersonEvent);
        EventBus.getDefault().postSticky(new TypeChangeEvent("", 0, typeChangeDocumentList));
        startActivity(new Intent(DetailDocumentProcessedActivity.this, SelectPersonActivity.class));
        //finish();
    }

    @OnClick({R.id.btnMark, R.id.btnSend, R.id.btnHistory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMark:
                mark();
                break;
            case R.id.btnSend:
                try {
                    TraoDoiXuLyEvent event = EventBus.getDefault().getStickyEvent(TraoDoiXuLyEvent.class);
                    if (event.getTraodoi() == 1) {
                        selectBtnSend();
                    } else {
                        send();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    send();
                }
                break;
            case R.id.btnHistory:
                EventBus.getDefault().postSticky(new DetailDocumentInfo(newItem.getId(), Constants.DOCUMENT_PROCESSED, null));
                startActivity(new Intent(this, NewHistoryDocumentActivity.class));
                break;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FinishEvent finishEvent) {
        if (finishEvent != null && finishEvent.getType() == 1 && finishEvent.isFinish()) {
            EventBus.getDefault().removeStickyEvent(FinishEvent.class);
            finish();
        }
    }

    private void selectBtnSend() {
        labels = new ArrayList<String>();

        labels.add("Chuyển xử lý");
        labels.add("Trao đổi thông tin");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.weight_table_menu,
                R.id.textViewTableMenuItem, labels);
        final ListPopupWindow listPopupWindow = new ListPopupWindow(this);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setAnchorView(btnSend);
        listPopupWindow.setWidth(550);
        listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
        listPopupWindow.setHorizontalOffset(-402); // margin left
        listPopupWindow.setVerticalOffset(-20);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String typeLabe = labels.get(i).trim();
                switch (typeLabe){
                    case "Trao đổi thông tin":
                        EventBus.getDefault().postSticky(new TitleTraoDoiXuLyEvent(tvTitle.getText().toString()));
                        startActivity(new Intent(DetailDocumentProcessedActivity.this, ExchangeHandlesActivity.class));
                        break;
                        case "Chuyển xử lý" :
                            send();
                            break;
                }
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }
}
