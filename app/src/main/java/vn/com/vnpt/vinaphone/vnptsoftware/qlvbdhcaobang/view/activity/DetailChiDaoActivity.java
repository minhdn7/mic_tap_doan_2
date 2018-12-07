package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.AttachFileAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.ReplyChiDaoAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.FileUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.ChiDaoFlow;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.FileChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao.ChiDaoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao.IChiDaoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.userinfo.IUserInfoPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.userinfo.UserInfoPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ChiDaoFlowsEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.CloseProgressEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ResponseChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ResponsedChiDaoEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChiDaoView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IUserAvatarView;

public class DetailChiDaoActivity extends AppCompatActivity implements ILoginView, IChiDaoView, IUserAvatarView {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.img_anh_dai_dien)
    ImageView imgAnhDaiDien;
    @BindView(R.id.txtName)
    TextView txtName;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtContent)
    TextView txtContent;
    @BindView(R.id.layoutFile)
    LinearLayout layoutFile;
    @BindView(R.id.rvReplys)
    RecyclerView rvReplys;
    @BindView(R.id.btnReply)
    LinearLayout btnReply;
    @BindView(R.id.btnForward)
    LinearLayout btnForward;
    @BindView(R.id.btnReplyAll)
    LinearLayout btnReplyAll;
    @BindView(R.id.txtNguoiNhan)
    TextView txtNguoiNhan;
    @BindView(R.id.txtNoData)
    TextView txtNoData;
    @BindView(R.id.btnMore)
    TextView btnMore;
    private String id;
    private String event;
    private ChiDaoFlow chiDaoFlow;
    private List<ChiDaoFlow> chiDaoFlows;
    private List<ChiDaoFlow> chiDaoFlowReplys;
    private List<FileChiDao> fileChiDaos;
    private ReplyChiDaoAdapter adapter;
    private ProgressDialog progressDialog;
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private IChiDaoPresenter chiDaoPresenter = new ChiDaoPresenterImpl(this);
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private IUserInfoPresenter userInfoPresenter = new UserInfoPresenterImpl(this);
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chi_dao);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("ID_CHIDAO");
        getFlow();
    }

    private void getFlow() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_FLOW";
            chiDaoPresenter.getFlowChiDao(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void getFiles() {
        if (connectionDetector.isConnectingToInternet()) {
            event = "GET_FILE";
            chiDaoPresenter.getFileChiDao(id);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void fillData(ChiDaoFlow chiDaoFlow) {
        txtName.setText(chiDaoFlow.getTenNguoiTao());
        txtDate.setText(chiDaoFlow.getNgayTao());
        txtTitle.setText(chiDaoFlow.getTieuDe());
        btnMore.setVisibility(View.GONE);
        if (chiDaoFlow.getNoiDung() != null && !chiDaoFlow.getNoiDung().trim().equals("")) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                txtContent.setText(Html.fromHtml(chiDaoFlow.getNoiDung()));
            } else {
                txtContent.setText(Html.fromHtml(chiDaoFlow.getNoiDung(), Html.FROM_HTML_MODE_COMPACT));
            }
            int lineCount = txtContent.getLineCount();
            if (lineCount > 5) {
                txtContent.setMaxLines(5);
                btnMore.setVisibility(View.VISIBLE);
            } else {
                btnMore.setVisibility(View.GONE);
            }
        } else {
            txtContent.setText("");
        }
        String nguoiNhan = "";
        String[] nguoiNhans = chiDaoFlow.getUserCount().split("\\|");
        if (nguoiNhans[1].equals("1")) {
            nguoiNhan += "Bạn";
        }
        if (!nguoiNhans[0].equals("0")) {
            if (nguoiNhans[1].equals("1")) {
                nguoiNhan += " và " + nguoiNhans[0] + " người khác";
            } else {
                nguoiNhan += nguoiNhans[0] + " người khác";
            }
        }
        txtNguoiNhan.setText(nguoiNhan);
        userInfoPresenter.getAvatar(chiDaoFlow.getUserId());
    }

    private void fillFlow() {
        EventBus.getDefault().postSticky(new ChiDaoFlowsEvent(chiDaoFlows));
        adapter = new ReplyChiDaoAdapter(this, chiDaoFlowReplys);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvReplys.setLayoutManager(mLayoutManager);
        rvReplys.setItemAnimator(new DefaultItemAnimator());
        rvReplys.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (chiDaoFlowReplys != null && chiDaoFlowReplys.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void fillFiles() {
        layoutFile.removeAllViewsInLayout();
//        int i = 0;
//        LinearLayout linearLayout = new LinearLayout(this);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//        while (i < fileChiDaos.size()) {
//            if (i % 3 == 0) {
//                if (i != 0) {
//                    layoutFile.addView(linearLayout);
//                    linearLayout = new LinearLayout(this);
//                    linearLayout.setLayoutParams(params);
//                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//                    int paddingDp = 5;
//                    float density = getResources().getDisplayMetrics().density;
//                    int paddingPixel = (int)(paddingDp * density);
//                    linearLayout.setPadding(0, paddingPixel, 0, paddingPixel);
//                }
//
//            }
//            TextView textView = createView(i, fileChiDaos.get(i).getName());
//            if (textView != null) {
//                linearLayout.addView(textView);
//            }
//            i++;
//        }
//        if (i == fileChiDaos.size()) {
//            if (linearLayout.getParent() != null) {
//                ((ViewGroup) linearLayout.getParent()).removeView(linearLayout);
//            }
//            layoutFile.addView(linearLayout);
//        }
        for (int i = 0; i < fileChiDaos.size(); i++) {
            TextView textView = createView(i, fileChiDaos.get(i).getName());
            if (textView != null) {
                layoutFile.addView(textView);
            }
        }
    }

    private TextView createView(final int id, final String filename) {
        if (filename != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            TextView textView = new TextView(this);
            textView.setText(filename);
            textView.setId(id);
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setTypeface(Application.getApp().getTypeface());
            textView.setTextColor(getResources().getColor(R.color.colorPrimary));
            if (filename.trim().toUpperCase().endsWith(Constants.DOC) || filename.trim().toUpperCase().endsWith(Constants.DOCX)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_doc, 0, 0, 0);
            }
            if (filename.trim().toUpperCase().endsWith(Constants.XLS) || filename.trim().toUpperCase().endsWith(Constants.XLSX)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_xls, 0, 0, 0);
            }
            if (filename.trim().toUpperCase().endsWith(Constants.PDF)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pdf, 0, 0, 0);
            }
            if (filename.trim().toUpperCase().endsWith(Constants.TXT)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_txt, 0, 0, 0);
            }
            if (filename.trim().toUpperCase().endsWith(Constants.JPG)
                    || filename.trim().toUpperCase().endsWith(Constants.JPEG)
                    || filename.trim().toUpperCase().endsWith(Constants.PNG)
                    || filename.trim().toUpperCase().endsWith(Constants.GIF)
                    || filename.trim().toUpperCase().endsWith(Constants.TIFF)
                    || filename.trim().toUpperCase().endsWith(Constants.BMP)) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_image, 0, 0, 0);
            }
            textView.setLayoutParams(params);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (connectionDetector.isConnectingToInternet()) {
                        if (fileChiDaos.get(id).getHdd() != null && !fileChiDaos.get(id).getHdd().trim().equals("")) {
                            event = "GET_URLFILE";
                            fileName = filename;
//                            chiDaoPresenter.downloadFileChiDao(new DownloadChiDaoRequest(fileChiDaos.get(id).getHdd().trim()));
                            chiDaoPresenter.getWebViewFile(fileChiDaos.get(id).getHdd().trim());
                        } else {
                            AlertDialogManager.showAlertDialog(DetailChiDaoActivity.this, getString(R.string.DOWNLOAD_TITLE_ERROR), getString(R.string.NO_URL_ERROR), true, AlertDialogManager.ERROR);
                        }
                    } else {
                        AlertDialogManager.showAlertDialog(DetailChiDaoActivity.this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                    }
                }
            });
            return textView;
        }
        return null;
    }

    @Override
    public void onSuccess(Object object) {
        if (event.endsWith("GET_URLFILE")) {
            event = "";
            webViewDialog((GetViewFileObj) object, fileName);
//            ResponseBody responseBody = (ResponseBody) object;
//            FileUtils fileUtils = new FileUtils(this);
//            File fileDownload = fileUtils.writeResponseBodyToDisk(responseBody, fileName);
//            if (fileDownload != null) {
//                if (fileName != null && !fileName.equals("")) {
//                    String ext = fileUtils.validateExtension(fileName);
//                    if (ext != null) {
//                        if (ext.endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.PDF)) {
//                            fileUtils.openPDF(fileDownload);
//                        } else {
//                            if (ext.endsWith(Constants.XLS) || ext.endsWith(Constants.XLSX)) {
//                                fileUtils.openExcel(fileDownload);
//                            } else {
//                                if (ext.endsWith(Constants.DOC) || ext.endsWith(Constants.DOCX)) {
//                                    fileUtils.openWord(fileDownload);
//                                } else {
//                                    EventBus.getDefault().postSticky(fileDownload);
//                                    startActivity(new Intent(this, ImageViewActivity.class));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }
        if (event.equals("GET_FILE")) {
            fileChiDaos = ConvertUtils.fromJSONList(object, FileChiDao.class);
            if (fileChiDaos != null && fileChiDaos.size() > 0) {
                fillFiles();
            }
//            CloseProgressEvent closeProgressEvent = EventBus.getDefault().getStickyEvent(CloseProgressEvent.class);
//            if (closeProgressEvent != null) {
//                boolean closeProgress = EventBus.getDefault().getStickyEvent(CloseProgressEvent.class).isCloseProgress();
//                if (closeProgress) {
//                    progressDialog.dismiss();
//                }
//            }
        }
        if (event.equals("GET_FLOW")) {
            chiDaoFlows = ConvertUtils.fromJSONList(object, ChiDaoFlow.class);
            if (chiDaoFlows != null && chiDaoFlows.size() > 0) {
                int index = 0;
                chiDaoFlowReplys = new ArrayList<>();
                for (int i = 0; i < chiDaoFlows.size(); i++) {
                    if (chiDaoFlows.get(i).getParentId() != null && chiDaoFlows.get(i).getParentId().trim().equals(id)) {
                        chiDaoFlowReplys.add(chiDaoFlows.get(i));
                    }
                    if (chiDaoFlows.get(i).getParentId() == null || chiDaoFlows.get(i).getParentId().trim().equals("")
                            || chiDaoFlows.get(i).getParentId().trim().equals("0") || chiDaoFlows.get(i).getId().trim().equals(id)) {
                        fillData(chiDaoFlows.get(i));
                        index = i;
                        chiDaoFlow = chiDaoFlows.get(i);
                    }
                }
                chiDaoFlows.remove(index);
                fillFlow();
                getFiles();
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.CHIDAO_NOT_FOUND), true, AlertDialogManager.ERROR);
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
            if (event.equals("GET_FLOW")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_FLOW_ERROR), true, AlertDialogManager.ERROR);
            }
            if (event.equals("GET_FILE")) {
                AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_FILE_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            getFlow();
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
    public void onSuccess() {

    }

    @Override
    public void onSuccess(List<Object> object) {

    }

    @Override
    public void showProgress() {
        if (DetailChiDaoActivity.this.isFinishing()) {
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
        if (DetailChiDaoActivity.this.isFinishing()) {
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

    @OnClick({R.id.btnReply, R.id.btnForward, R.id.btnReplyAll, R.id.txtNguoiNhan, R.id.btnBack, R.id.btnMore})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnReply:
                EventBus.getDefault().postSticky(new CloseProgressEvent(false));
                intent = new Intent(this, ReplyChiDaoActivity.class);
                intent.putExtra("parentId", id);
                intent.putExtra("typeReply", "1");
                intent.putExtra("title", "TrL: " + chiDaoFlow.getTieuDe());
                startActivity(intent);
                break;
            case R.id.btnForward:
                EventBus.getDefault().postSticky(new CloseProgressEvent(false));
                intent = new Intent(this, ForwardChiDaoActivity.class);
                EventBus.getDefault().postSticky(new ResponseChiDaoEvent(chiDaoFlow));
                startActivity(intent);
                break;
            case R.id.btnReplyAll:
                EventBus.getDefault().postSticky(new CloseProgressEvent(false));
                intent = new Intent(this, ReplyChiDaoActivity.class);
                intent.putExtra("parentId", id);
                intent.putExtra("typeReply", "2");
                intent.putExtra("title", "TrL: " + chiDaoFlow.getTieuDe());
                startActivity(intent);
                break;
            case R.id.txtNguoiNhan:
                intent = new Intent(this, ListReceiveActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnMore:
                if (txtContent.getMaxLines() == 5) {
                    txtContent.setMaxLines(100000);
                    btnMore.setText("Rút gọn");
                    return;
                }
                if (txtContent.getMaxLines() == 100000) {
                    btnMore.setText("Xem thêm");
                    txtContent.setMaxLines(5);
                    return;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        EventBus.getDefault().removeStickyEvent(ChiDaoFlowsEvent.class);
        finish();
    }

    @Override
    public void onErrorAvatar(APIError apiError) {

    }

    @Override
    public void onSuccessAvatar(byte[] bitmap) {
        Glide.with(this).load(bitmap).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(this)).into(imgAnhDaiDien);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.POSTING)
    public void onEvent(ResponsedChiDaoEvent responsedChiDaoEvent) {
        EventBus.getDefault().removeStickyEvent(ResponsedChiDaoEvent.class);
        //boolean closeProgress = EventBus.getDefault().getStickyEvent(CloseProgressEvent.class).isCloseProgress();
        //if (!closeProgress && responsedChiDaoEvent != null && responsedChiDaoEvent.isResponse()) {
            EventBus.getDefault().postSticky(new CloseProgressEvent(true));
            getFlow();
        //}
    }
    private void webViewDialog(GetViewFileObj responseBody, String fileName) {

        final Dialog dialog = new Dialog(DetailChiDaoActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.layout_dialog_view_file);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title_label);
        ImageView ivCloseDialog = (ImageView) dialog.findViewById(R.id.ivCloseDialog);
        WebView webViewFile = (WebView) dialog.findViewById(R.id.web_view_file);
        tvTitle.setText(fileName);
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        webViewFile.getSettings().setJavaScriptEnabled(true);
        webViewFile.getSettings().setPluginState(WebSettings.PluginState.ON);
        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---
        webViewFile.setWebViewClient(new Callback());

        String pdfURL = responseBody.getData();
        webViewFile.loadUrl(pdfURL);
        dialog.show();
    }
    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }
}
