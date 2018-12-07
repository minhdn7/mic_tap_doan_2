package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.DetailDocumentNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentAdvanceSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentMarkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentSearchInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentnotification.DocumentNotificationPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentnotification.IDocumentNotificationPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.FinishEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocAdvanceSearchEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocMarkEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocNotificationEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocSearchEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypePersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDetailDocumentNotificationView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class DetailDocumentNotificationActivity extends BaseActivity implements IDetailDocumentNotificationView, ILoginView {

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
    private IDocumentNotificationPresenter documentNotificationPresenter = new DocumentNotificationPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean marked;
    private int id;
    private int type;
    private NewHistoryAdapter adapter;
    List<UnitLogInfo> logInfoList = new ArrayList<>();
    private DetailDocumentInfo detailDocumentInfo;
    private DocumentSearchInfo newItem2;
    private DocumentNotificationInfo newItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_document_notification);
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
                newItem2 = (DocumentSearchInfo) extras.getSerializable(Constants.CONSTANTS_INTENT_DOC_SEARCH_INFOR);
                newItem = (DocumentNotificationInfo) extras.getSerializable(Constants.CONSTANTS_INTENT_DOC_NOTIFICATION_INFOR);
            }
        } else {
            detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            newItem2 = EventBus.getDefault().getStickyEvent(DocumentSearchInfo.class);
            newItem = EventBus.getDefault().getStickyEvent(DocumentNotificationInfo.class);
        }
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        switch (type) {
            case 0:
                EventBus.getDefault().removeStickyEvent(DocumentNotificationInfo.class);
                EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
                EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
                break;
            case 1:
                EventBus.getDefault().removeStickyEvent(DocumentMarkInfo.class);
                EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
                EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
                break;
            case 2:
                EventBus.getDefault().removeStickyEvent(DocumentSearchInfo.class);
                EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
                EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
                break;
            case 3:
                EventBus.getDefault().removeStickyEvent(DocumentAdvanceSearchInfo.class);
                EventBus.getDefault().removeStickyEvent(DetailDocumentInfo.class);
                EventBus.getDefault().removeStickyEvent(TypeChangeDocumentRequest.class);
                break;
        }
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
        tv_vblienquan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_comment_label.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        layoutVanBan.setVisibility(View.GONE);
        layoutFileDK.setVisibility(View.GONE);
        tv_sovb_of.setTypeface(Application.getApp().getTypeface());
        tv_so_ioffice.setTypeface(Application.getApp().getTypeface());

        if (newItem != null) {
            type = 0;
            id = Integer.parseInt(newItem.getId());
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
            btnSend.setVisibility(View.GONE);
            DocumentMarkInfo newItem1 = EventBus.getDefault().getStickyEvent(DocumentMarkInfo.class);
            if (newItem1 != null) {
                type = 1;
                id = Integer.parseInt(newItem1.getId());
                btnMark.setVisibility(View.VISIBLE);
                if (!newItem1.getIsCheck().equals(Constants.NOT_MARKED)) {
                    marked = true;
                    btnMark.setText("Hủy đánh dấu");
                } else {
                    marked = false;
                    btnMark.setText("Đánh dấu");
                }
            } else {
                btnMark.setVisibility(View.GONE);
                btnHistory.setVisibility(View.GONE);
                DocumentSearchInfo newItem2 = EventBus.getDefault().getStickyEvent(DocumentSearchInfo.class);
                if (newItem2 != null) {
                    type = 2;
                    id = Integer.parseInt(newItem2.getId());
                    btnMark.setVisibility(View.VISIBLE);
                    if (newItem2.getIsCheck() != null && !newItem2.getIsCheck().equals(Constants.NOT_MARKED)) {
                        marked = true;
                        btnMark.setText("Hủy đánh dấu");
                    } else {
                        marked = false;
                        btnMark.setText("Đánh dấu");
                    }
                } else {
                    btnMark.setVisibility(View.GONE);
                    DocumentAdvanceSearchInfo newItem3 = EventBus.getDefault().getStickyEvent(DocumentAdvanceSearchInfo.class);
                    if (newItem3 != null) {
                        type = 3;
                        id = Integer.parseInt(newItem3.getId());
                        btnMark.setVisibility(View.GONE);
//                        if (!newItem3.getIsCheck().equals(Constants.NOT_MARKED)) {
//                            marked = true;
//                            btnMark.setText("Hủy đánh dấu");
//                        } else {
//                            marked = false;
//                            btnMark.setText("Đánh dấu");
//                        }
                    } else {
                        btnMark.setVisibility(View.GONE);
                    }
                }
            }

        }
        adapter = new NewHistoryAdapter(this, logInfoList, Constants.PROCESSED_HISTORY);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerHistory.setFocusable(false);
        recyclerHistory.setNestedScrollingEnabled(false);
        recyclerHistory.setLayoutManager(mLayoutManager);
        recyclerHistory.setItemAnimator(new DefaultItemAnimator());
        recyclerHistory.setAdapter(adapter);
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getDetail(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getAttachFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getAttachFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedDocs() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getRelatedDocs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getLogs() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getLogs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_NOTIFICATION)) {
                documentNotificationPresenter.getRelatedFiles(Integer.parseInt(detailDocumentInfo.getId()));
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

    private void fillDetail(DetailDocumentNotification detailDocumentNotification) {
        tvTitle.setText(detailDocumentNotification.getTrichYeu());
        if (detailDocumentNotification.getDonViBanHanh() != null && !detailDocumentNotification.getDonViBanHanh().trim().equals("")) {
            tvCQBH.setText(detailDocumentNotification.getDonViBanHanh());
        } else {
            tvCQBH.setVisibility(View.GONE);
            tvCQBH_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getSoKiHieu() != null && !detailDocumentNotification.getSoKiHieu().trim().equals("")) {
            tvKH.setText(detailDocumentNotification.getSoKiHieu());
        } else {
            tvKH.setVisibility(View.GONE);
            tvKH_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getNgayDenDi() != null && !detailDocumentNotification.getNgayDenDi().trim().equals("")) {
            tv_ngayden.setText(detailDocumentNotification.getNgayDenDi());
        } else {
            tv_ngayden.setVisibility(View.GONE);
            tv_ngayden_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getNgayVanBan() != null && !detailDocumentNotification.getNgayVanBan().trim().equals("")) {
            tvNgayVB.setText(detailDocumentNotification.getNgayVanBan());
        } else {
            tvNgayVB.setVisibility(View.GONE);
            tvNgayVB_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getHinhThucVanBan() != null && !detailDocumentNotification.getHinhThucVanBan().trim().equals("")) {
            tv_hinhthucvb.setText(detailDocumentNotification.getHinhThucVanBan());
        } else {
            tv_hinhthucvb.setVisibility(View.GONE);
            tv_hinhthucvb_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getSoVanBan() != null && !detailDocumentNotification.getSoVanBan().trim().equals("")) {
            tv_sovb.setText(detailDocumentNotification.getSoVanBan());
        } else {
            tv_sovb.setVisibility(View.GONE);
            tv_sovb_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getSoDenDi() > 0) {
            tv_soden.setText(String.valueOf(detailDocumentNotification.getSoDenDi()));
        } else {
            tv_soden.setVisibility(View.GONE);
            tv_soden_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getDoUuTien() != null && !detailDocumentNotification.getDoUuTien().trim().equals("")) {
            tvDoKhan.setText(detailDocumentNotification.getDoUuTien());
        } else {
            tvDoKhan.setVisibility(View.GONE);
            tvDoKhan_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getHanGiaiQuyet() != null && !detailDocumentNotification.getHanGiaiQuyet().trim().equals("")) {
            tv_hanxuly.setText(detailDocumentNotification.getHanGiaiQuyet());
        } else {
            tv_hanxuly.setVisibility(View.GONE);
            tv_hanxuly_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getSoTrang() > 0) {
            tv_sotrang.setText(String.valueOf(detailDocumentNotification.getSoTrang()));
        } else {
            tv_sotrang.setVisibility(View.GONE);
            tv_sotrang_label.setVisibility(View.GONE);
        }
        if (detailDocumentNotification.getHinhThucGui() != null && !detailDocumentNotification.getHinhThucGui().trim().equals("")) {
            tv_hinhthucgui.setText(detailDocumentNotification.getHinhThucGui());
        } else {
            tv_hinhthucgui.setVisibility(View.GONE);
            tv_hinhthucgui_label.setVisibility(View.GONE);
        }

        if (detailDocumentNotification.getIoffice() != null && !detailDocumentNotification.getIoffice().trim().equals("")) {
            tv_sovb_of.setText(detailDocumentNotification.getIoffice());
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

    private void fillRelatedDocs(List<RelatedDocumentInfo> relatedDocumentInfoList) {
        if (relatedDocumentInfoList != null && relatedDocumentInfoList.size() > 0) {
            layoutVanBan.setVisibility(View.VISIBLE);
            layout_related_docs.removeAllViews();
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
        if (object instanceof DetailDocumentNotification) {
            DetailDocumentNotification detailDocumentNotification = (DetailDocumentNotification) object;
            fillDetail(detailDocumentNotification);
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
        }
    }

    @Override
    public void showProgress() {
        if (DetailDocumentNotificationActivity.this.isFinishing()) {
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
        if (!DetailDocumentNotificationActivity.this.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
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
        startActivity(new Intent(DetailDocumentNotificationActivity.this, LoginActivity.class));
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
        switch (type) {
            case 0:
                EventBus.getDefault().postSticky(new ReloadDocNotificationEvent(true));
                break;
            case 1:
                EventBus.getDefault().postSticky(new ReloadDocMarkEvent(true));
                break;
            case 2:
                EventBus.getDefault().postSticky(new ReloadDocSearchEvent(true));
                break;
            case 3:
                EventBus.getDefault().postSticky(new ReloadDocAdvanceSearchEvent(true));
                break;
        }
    }

    private void mark() {
        if (connectionDetector.isConnectingToInternet()) {
            documentNotificationPresenter.mark(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void send() {
        ListPersonEvent listPersonEvent = EventBus.getDefault().getStickyEvent(ListPersonEvent.class);
        if (listPersonEvent == null) {
            listPersonEvent = new ListPersonEvent(null, null, null, null, null, null);
        }
        EventBus.getDefault().postSticky(new TypePersonEvent(Constants.TYPE_SEND_VIEW));
        listPersonEvent.setPersonProcessList(null);
        listPersonEvent.setPersonSendList(null);
        listPersonEvent.setPersonDirectList(null);
        EventBus.getDefault().postSticky(listPersonEvent);
        EventBus.getDefault().postSticky(new FinishEvent(2, false));
        EventBus.getDefault().postSticky(new TypeChangeEvent("", 0, null));
        startActivity(new Intent(DetailDocumentNotificationActivity.this, SelectPersonActivity.class));
        //finish();
    }

    @OnClick({R.id.btnMark, R.id.btnSend, R.id.btnHistory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnMark:
                mark();
                break;
            case R.id.btnSend:
                send();
                break;
            case R.id.btnHistory:
                switch (type) {
                    case 0:
                        EventBus.getDefault().postSticky(new DetailDocumentInfo(String.valueOf(id), Constants.DOCUMENT_NOTIFICATION, null));
                        break;
                    case 1:
                        EventBus.getDefault().postSticky(new DetailDocumentInfo(String.valueOf(id), Constants.DOCUMENT_MARK, null));
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
                startActivity(new Intent(this, NewHistoryDocumentActivity.class));
                break;
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(FinishEvent finishEvent) {
        if (finishEvent != null && finishEvent.getType() == 2 && finishEvent.isFinish()) {
            EventBus.getDefault().removeStickyEvent(FinishEvent.class);
            finish();
        }
    }
}
