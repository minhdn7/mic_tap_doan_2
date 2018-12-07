package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.ExchangeHandlesAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.NewHistoryAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.DetailDocumentWaiting;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendCommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ExchangeHandlesInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ExchangeHandlesRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SendCommentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.exchangeHandles.ExchangeHandlesPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.exchangeHandles.IExchangeHandlesPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TitleTraoDoiXuLyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IExchangeHandlesView;

public class ExchangeHandlesActivity extends BaseActivity implements IExchangeHandlesView {

    @BindView(R.id.recycler_exchange_handles)
    RecyclerView rcExchangeHandles;
    @BindView(R.id.txt_nhap_text)
    EditText txt_nhap_text;

    private ExchangeHandlesAdapter exchangeHandlesAdapter;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    private Toolbar toolbar;
    private ImageView btnBack;

    private ExchangeHandlesAdapter adapter;

    private IExchangeHandlesPresenter exchangeHandlesPresenter = new ExchangeHandlesPresenterImpl(this);
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_handles);
        setupUI(findViewById(R.id.parentList));
        init();
    }

    private void setupToolbar() {
        TitleTraoDoiXuLyEvent event = EventBus.getDefault().getStickyEvent(TitleTraoDoiXuLyEvent.class);
        toolbar = (Toolbar) findViewById(R.id.toolbarDetail);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.tvTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        try {
            if (event != null) {
                tvTitle.setText(event.getTitleDocument());
            }
            else {
                tvTitle.setText(getString(R.string.EXCHANGE_HANDLES));
            }
        } catch (Exception e) {
            e.printStackTrace();
            tvTitle.setText(getString(R.string.EXCHANGE_HANDLES));
        }

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

        Application.getApp().getAppPrefs().getToken();
        if (connectionDetector.isConnectingToInternet()) {
            DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
            exchangeHandlesPresenter.getExchangeHandles(Integer.parseInt(detailDocumentInfo.getId()), 10, 20);
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }

    }

    @OnClick({R.id.btnSend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                if (connectionDetector.isConnectingToInternet()) {
                    DetailDocumentInfo detailDocumentInfo = EventBus.getDefault().getStickyEvent(DetailDocumentInfo.class);
                    String comment = txt_nhap_text.getText().toString().trim();
                    if(comment.isEmpty()){
                        comment ="";
                    } else {
                        comment = txt_nhap_text.getText().toString().trim();
                    }
                    SendCommentRequest sendCommentRequest = new SendCommentRequest(
                            Integer.parseInt(detailDocumentInfo.getId()),
                            comment
                    );

                    if(comment.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Nội dung thông tin không được để trống!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    exchangeHandlesPresenter.sendComment(sendCommentRequest);

                    // gui thanh cong load lai du lieu
                    exchangeHandlesPresenter.getExchangeHandles(Integer.parseInt(detailDocumentInfo.getId()), 10, 20);

                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
                break;

        }
    }

    @Override
    public void onSuccessRecords(ExchangeHandlesRespone object) {
        if(object.getData() != null){
            if(object.getData().size() >0){
                adapter = new ExchangeHandlesAdapter(this, object.getData());
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                rcExchangeHandles.setFocusable(false);
                rcExchangeHandles.setNestedScrollingEnabled(false);
                rcExchangeHandles.setLayoutManager(mLayoutManager);
                rcExchangeHandles.setItemAnimator(new DefaultItemAnimator());
                rcExchangeHandles.setAdapter(adapter);
                rcExchangeHandles.smoothScrollToPosition(adapter.getItemCount() -1);
            }
        }
    }

    @Override
    public void onSendSuccess(SendCommentRespone object) {
        txt_nhap_text.setText("");
        Toast.makeText(this, getString(R.string.COMMENT_SUCCESS), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(APIError apiError) {
        Log.d("Thao","onError");
    }

    @Override
    public void showProgress() {
        if (ExchangeHandlesActivity.this.isFinishing()) {
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
        if (!ExchangeHandlesActivity.this.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ExchangeHandlesActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
