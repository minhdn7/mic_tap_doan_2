package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.AttachFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.LogAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.RelatedDocumentAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.RelatedFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.DetailDocumentExpired;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentExpiredInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentexpired.DocumentExpiredPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentexpired.IDocumentExpiredPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDetailDocumentExpiredView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class DetailDocumentExpiredActivity extends BaseActivity implements IDetailDocumentExpiredView, ILoginView {

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
    @BindView(R.id.tv_so_ioffice)
    TextView tv_so_ioffice;
    @BindView(R.id.tv_sovb_of)
    TextView tv_sovb_of;
    private Toolbar toolbar;
    private ImageView btnBack;
    private IDocumentExpiredPresenter documentExpiredPresenter = new DocumentExpiredPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;
    private boolean marked;
    private DocumentExpiredInfo newItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_document_expired);
        ButterKnife.bind(this);
        init();
        getDetail();
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
        newItem = EventBus.getDefault().getStickyEvent(DocumentExpiredInfo.class);
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
    }

    private void getDetail() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_EXPIRED)) {
                documentExpiredPresenter.getDetail(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getAttachFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_EXPIRED)) {
                documentExpiredPresenter.getAttachFiles(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedDocs() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_EXPIRED)) {
                documentExpiredPresenter.getRelatedDocs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getLogs() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_EXPIRED)) {
                documentExpiredPresenter.getLogs(Integer.parseInt(detailDocumentInfo.getId()));
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getRelatedFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            if (detailDocumentInfo.getType().equals(Constants.DOCUMENT_EXPIRED)) {
                documentExpiredPresenter.getRelatedFiles(Integer.parseInt(detailDocumentInfo.getId()));
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

    private void fillDetail(DetailDocumentExpired detailDocumentExpired) {
        tvTitle.setText(detailDocumentExpired.getTrichYeu());
        if (detailDocumentExpired.getDonViBanHanh() != null && !detailDocumentExpired.getDonViBanHanh().trim().equals("")) {
            tvCQBH.setText(detailDocumentExpired.getDonViBanHanh());
        } else {
            tvCQBH.setVisibility(View.GONE);
            tvCQBH_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getSoKiHieu() != null && !detailDocumentExpired.getSoKiHieu().trim().equals("")) {
            tvKH.setText(detailDocumentExpired.getSoKiHieu());
        } else {
            tvKH.setVisibility(View.GONE);
            tvKH_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getNgayDenDi() != null && !detailDocumentExpired.getNgayDenDi().trim().equals("")) {
            tv_ngayden.setText(detailDocumentExpired.getNgayDenDi());
        } else {
            tv_ngayden.setVisibility(View.GONE);
            tv_ngayden_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getNgayVanBan() != null && !detailDocumentExpired.getNgayVanBan().trim().equals("")) {
            tvNgayVB.setText(detailDocumentExpired.getNgayVanBan());
        } else {
            tvNgayVB.setVisibility(View.GONE);
            tvNgayVB_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getHinhThucVanBan() != null && !detailDocumentExpired.getHinhThucVanBan().trim().equals("")) {
            tv_hinhthucvb.setText(detailDocumentExpired.getHinhThucVanBan());
        } else {
            tv_hinhthucvb.setVisibility(View.GONE);
            tv_hinhthucvb_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getSoVanBan() != null && !detailDocumentExpired.getSoVanBan().trim().equals("")) {
            tv_sovb.setText(detailDocumentExpired.getSoVanBan());
        } else {
            tv_sovb.setVisibility(View.GONE);
            tv_sovb_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getSoDenDi() > 0) {
            tv_soden.setText(String.valueOf(detailDocumentExpired.getSoDenDi()));
        } else {
            tv_soden.setVisibility(View.GONE);
            tv_soden_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getDoUuTien() != null && !detailDocumentExpired.getDoUuTien().trim().equals("")) {
            tvDoKhan.setText(detailDocumentExpired.getDoUuTien());
        } else {
            tvDoKhan.setVisibility(View.GONE);
            tvDoKhan_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getHanGiaiQuyet() != null && !detailDocumentExpired.getHanGiaiQuyet().trim().equals("")) {
            tv_hanxuly.setText(detailDocumentExpired.getHanGiaiQuyet());
        } else {
            tv_hanxuly.setVisibility(View.GONE);
            tv_hanxuly_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getSoTrang() > 0) {
            tv_sotrang.setText(String.valueOf(detailDocumentExpired.getSoTrang()));
        } else {
            tv_sotrang.setVisibility(View.GONE);
            tv_sotrang_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getHinhThucGui() != null && !detailDocumentExpired.getHinhThucGui().trim().equals("")) {
            tv_hinhthucgui.setText(detailDocumentExpired.getHinhThucGui());
        } else {
            tv_hinhthucgui.setVisibility(View.GONE);
            tv_hinhthucgui_label.setVisibility(View.GONE);
        }
        if (detailDocumentExpired.getIoffice() != null && !detailDocumentExpired.getIoffice().trim().equals("")) {
            tv_sovb_of.setText(detailDocumentExpired.getIoffice());
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
        if (object instanceof DetailDocumentExpired) {
            DetailDocumentExpired detailDocumentExpired = (DetailDocumentExpired) object;
            fillDetail(detailDocumentExpired);
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
                List<UnitLogInfo> logInfoList = (List<UnitLogInfo>) object;
                fillLogs(logInfoList);
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
        if (DetailDocumentExpiredActivity.this.isFinishing()) {
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
        if (!DetailDocumentExpiredActivity.this.isFinishing()) {
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
        getDetail();
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(DetailDocumentExpiredActivity.this, LoginActivity.class));
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
    }

    private void mark() {
        if (connectionDetector.isConnectingToInternet()) {
            documentExpiredPresenter.mark(Integer.parseInt(newItem.getId()));
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @OnClick(R.id.btnMark)
    public void onViewClicked() {
        mark();
    }
}
