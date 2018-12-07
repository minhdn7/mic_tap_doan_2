package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ApplicationSharedPreferences;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangePasswordRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changepassword.ChangePasswordPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changepassword.IChangePasswordPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChangePasswordView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

public class ChangePasswordActivity extends BaseActivity implements ILoginView, IChangePasswordView, Validator.ValidationListener {

    private ApplicationSharedPreferences appPrefs;
    private Toolbar toolbar;
    @BindView(R.id.btnBack) ImageView btnBack;
    private boolean isValidateChangePassword;
    private Validator validator;
    private IChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    @BindView(R.id.txtOldPassword) TextView txtOldPassword;
    @BindView(R.id.txtNewPassword) TextView txtNewPassword;
    @BindView(R.id.txtConfirmPassword) TextView txtConfirmPassword;
    @BindView(R.id.btnUpdate) Button btnUpdate;
    @NotEmpty(messageResId = R.string.OLD_PASSWORD_REQUIRED)
    @Length(min = 8, messageResId = R.string.PASSWORD_INVALID_LENGTH)
    @BindView(R.id.edtOldPassword)
    EditText edtOldPassword;
    @NotEmpty(messageResId = R.string.NEW_PASSWORD_REQUIRED)
    @Length(min = 8, messageResId = R.string.PASSWORD_INVALID_LENGTH)
    @Password
    @Pattern(regex = StringDef.PASSWORD_PATTERN, messageResId = R.string.PASSWORD_INVALID_STRENGTH)
    @BindView(R.id.edtNewPassword)
    EditText edtNewPassword;
    @NotEmpty(messageResId = R.string.CONFIRM_PASSWORD_REQUIRED)
    @Length(min = 8, messageResId = R.string.PASSWORD_INVALID_LENGTH)
    @ConfirmPassword(messageResId = R.string.CONFIRM_PASSWORD_MISMACTH)
    @Pattern(regex = StringDef.PASSWORD_PATTERN, messageResId = R.string.PASSWORD_INVALID_STRENGTH)
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector = new ConnectionDetector(this);
    @BindView(R.id.layoutDisplay) ConstraintLayout layoutDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        appPrefs = Application.getApp().getAppPrefs();
        validator = new Validator(this);
        validator.setValidationListener(this);
        setupToolbar();
        init();
    }

    @OnClick({R.id.btnUpdate})
    public void clickUpdate(View view) {
        if (edtNewPassword.getText() != null && edtConfirmPassword.getText() != null && edtOldPassword.getText() != null
                && !edtNewPassword.getText().toString().trim().equals("") && !edtOldPassword.getText().toString().trim().equals("") && !edtConfirmPassword.getText().toString().trim().equals("")
                && (edtConfirmPassword.getText().toString().equals(edtOldPassword.getText().toString()) || edtNewPassword.getText().toString().equals(edtOldPassword.getText().toString()))) {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.DUPLICATE_PASSWORD_ERROR), true, AlertDialogManager.ERROR);
        } else {
            validator.validate();
            if (isValidateChangePassword && view.getId() == R.id.btnUpdate) {
                if (connectionDetector.isConnectingToInternet()) {
                    changePasswordPresenter.changePasswordPresenter(new ChangePasswordRequest(edtOldPassword.getText().toString(), edtNewPassword.getText().toString()));
                } else {
                    AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarChangePassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getString(R.string.CHANGE_PASSWORD_MENU));
    }

    private void init() {
        txtOldPassword.setTypeface(Application.getApp().getTypeface());
        txtNewPassword.setTypeface(Application.getApp().getTypeface());
        txtConfirmPassword.setTypeface(Application.getApp().getTypeface());
        edtOldPassword.setTypeface(Application.getApp().getTypeface());
        edtNewPassword.setTypeface(Application.getApp().getTypeface());
        edtConfirmPassword.setTypeface(Application.getApp().getTypeface());
        btnUpdate.setTypeface(Application.getApp().getTypeface());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        isValidateChangePassword = true;
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        isValidateChangePassword = false;
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), message, true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onSuccess() {
        AlertDialogManager.showAlertDialog(this, getString(R.string.TITLE_SUCCESS), getString(R.string.CHANGE_PASSWORD_MESSAGE_SUCCESS), true, AlertDialogManager.SUCCESS);
    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(appPrefs.getAccount());
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            AlertDialogManager.showAlertDialog(this, getString(R.string.CHANGE_PASSWORD_TITLE_ERROR), getString(R.string.CHANGE_PASSWORD_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void showChangePasswordProgress() {
        if (ChangePasswordActivity.this.isFinishing()) {
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
    public void hideChangePasswordProgress() {
        if (ChangePasswordActivity.this.isFinishing()) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        appPrefs.setToken(loginInfo.getToken());
        if (isValidateChangePassword) {
            if (connectionDetector.isConnectingToInternet()) {
                changePasswordPresenter.changePasswordPresenter(new ChangePasswordRequest(edtOldPassword.getText().toString(), edtNewPassword.getText().toString()));
            } else {
                AlertDialogManager.showAlertDialog(this, getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        appPrefs.removeAll();
        startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showProgress() {
        if (ChangePasswordActivity.this.isFinishing()) {
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
        if (ChangePasswordActivity.this.isFinishing()) {
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
}
