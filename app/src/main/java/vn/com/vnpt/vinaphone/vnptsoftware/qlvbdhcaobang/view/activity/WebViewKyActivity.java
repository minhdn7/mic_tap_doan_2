package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.http.SslError;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentwaiting.DocumentWaitingDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.FileIdKyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.NhanWebViewEvent;

public class WebViewKyActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.text)
    TextView text;
    private String link="";

    private ProgressDialog progressDialog;
    private DocumentWaitingDao documentWaitingDao;
    private ICallFinishedListener callFinishedListener;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_ky);

        Intent intent = getIntent();
        link = intent.getExtras().getString("linkweb");

        if(intent.getExtras().getString("title") != null){
            text.setText("Đăng nhập");
        }

        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(WebViewKyActivity.this);
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Bạn có muốn tiếp tục chức năng không ?";

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton("Tiếp tục", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("Quay lại", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
//                Log.e("Error", description);
            }
        });

        webView.loadUrl(link);


        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);


    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NhanWebViewEvent event) {
        event = EventBus.getDefault().getStickyEvent( NhanWebViewEvent.class);
        if(event.getId() == 1){
            Log.d("Thao","Bắn notifi thành công ");

            // ky
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            FileIdKyEvent fileIdKyEvent = EventBus.getDefault().getStickyEvent(FileIdKyEvent.class);

            signdocumentAttach(Integer.parseInt(detailDocumentInfo.getId()), fileIdKyEvent.getFileId());

        }
    }

    private void signdocumentAttach(int docid,  int fileid) {
        progressDialog = new ProgressDialog(this);
        documentWaitingDao = new DocumentWaitingDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                if (object instanceof SigningRespone) {
                    progressDialog.dismiss();
                    if (((SigningRespone) object).getResponeAPI().getCode().equals("0")) {
                        AlertDialogManager.showAlertDialog(WebViewKyActivity.this, "Thông báo ",
                                "Ký Thành Công !", true, AlertDialogManager.SUCCESS);

                        finish();

                    } else if(((SigningRespone) object).getResponeAPI().getCode().equals("LOGIN_TO_SIGN_SERVER")){

                        String url = ((SigningRespone) object).getResponeAPI().getMessage();
//                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                        Intent i = new Intent(WebViewKyActivity.this, WebViewKyActivity.class);
                        i.putExtra("linkweb", url);
                        i.putExtra("title", "Đăng nhập");
                        startActivity(i);
                    }

                }
            }

            @Override
            public void onCallError(Object object) {
                progressDialog.dismiss();
                APIError apiError = (APIError) object;
                if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                    AlertDialogManager.showAlertDialog(WebViewKyActivity.this, "Lỗi ký văn bản", "Ký chưa thành công !", true, AlertDialogManager.ERROR);
                } else {
                    AlertDialogManager.showAlertDialog(WebViewKyActivity.this, "Lỗi ký văn bản", apiError.getMessage(), true, AlertDialogManager.ERROR);
                }
            }
        };
        progressDialog.setMessage(getApplicationContext().getString(R.string.DOWNLOADING));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();

        documentWaitingDao.onKyVanBan(docid, fileid, callFinishedListener);
    }



    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().postSticky(new  NhanWebViewEvent(0));
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals(link)) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.btnBack})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                break;
        }
    }


}
