package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReportWorkInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.report.IReportPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.report.ReportPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.ReportTemplateActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypeReportEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IReportView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment implements IReportView, ILoginView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.tv_vanban) TextView tv_vanban;
    @BindView(R.id.tv_congviec) TextView tv_congviec;
    @BindView(R.id.tv_report_vanbandi) TextView tv_report_vanbandi;
    @BindView(R.id.tv_report_vanbanden) TextView tv_report_vanbanden;

    @BindView(R.id.tv_vbden_cho_xuly_label) TextView tv_vbden_cho_xuly_label;
    @BindView(R.id.tv_vbden_qua_han_label) TextView tv_vbden_qua_han_label;
    @BindView(R.id.tv_vbden_da_xuly_label) TextView tv_vbden_da_xuly_label;
    @BindView(R.id.tv_vbden_ban_hanh_label) TextView tv_vbden_ban_hanh_label;
    @BindView(R.id.tv_vbden_cho_xuly) TextView tv_vbden_cho_xuly;
    @BindView(R.id.tv_vbden_qua_han) TextView tv_vbden_qua_han;
    @BindView(R.id.tv_vbden_da_xuly) TextView tv_vbden_da_xuly;
    @BindView(R.id.tv_vbden_ban_hanh) TextView tv_vbden_ban_hanh;

    @BindView(R.id.tv_vbdi_cho_xuly_label) TextView tv_vbdi_cho_xuly_label;
    @BindView(R.id.tv_vbdi_qua_han_label) TextView tv_vbdi_qua_han_label;
    @BindView(R.id.tv_vbdi_da_xuly_label) TextView tv_vbdi_da_xuly_label;
    @BindView(R.id.tv_vbdi_ban_hanh_label) TextView tv_vbdi_ban_hanh_label;
    @BindView(R.id.tv_vbdi_cho_xuly) TextView tv_vbdi_cho_xuly;
    @BindView(R.id.tv_vbdi_qua_han) TextView tv_vbdi_qua_han;
    @BindView(R.id.tv_vbdi_da_xuly) TextView tv_vbdi_da_xuly;
    @BindView(R.id.tv_vbdi_ban_hanh) TextView tv_vbdi_ban_hanh;

    @BindView(R.id.tv_cv_chuathuchien_label) TextView tv_cv_chuathuchien_label;
    @BindView(R.id.tv_cv_chuathuchien) TextView tv_cv_chuathuchien;
    @BindView(R.id.tv_cv_dangthuchien_label) TextView tv_cv_dangthuchien_label;
    @BindView(R.id.tv_cv_dangthuchien) TextView tv_cv_dangthuchien;
    @BindView(R.id.tv_cv_dathuchien_label) TextView tv_cv_dathuchien_label;
    @BindView(R.id.tv_cv_dathuchien) TextView tv_cv_dathuchien;
    @BindView(R.id.tv_cv_quahan_label) TextView tv_cv_quahan_label;
    @BindView(R.id.tv_cv_quahan) TextView tv_cv_quahan;
    @BindView(R.id.tv_cv_dunghan_label) TextView tv_cv_dunghan_label;
    @BindView(R.id.tv_cv_dunghan) TextView tv_cv_dunghan;

    @BindView(R.id.sReport) Spinner sReport;
    @BindView(R.id.btnReport) Button btnReport;

    private String type;
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;

    private IReportPresenter reportPresenter = new ReportPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);

    private OnFragmentInteractionListener mListener;
    private Activity activityAttach;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        setupSpinner();
        setFont();
        ((MainActivity) activityAttach).hideNotify();
        connectionDetector = new ConnectionDetector(getContext());
        if (connectionDetector.isConnectingToInternet()) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
            getReportDoc();
            getReportWork();
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void setFont() {
        btnReport.setTypeface(Application.getApp().getTypeface());
        tv_congviec.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vanban.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_report_vanbanden.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_report_vanbandi.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_ban_hanh.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_cho_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_da_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_qua_han.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbdi_ban_hanh.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbdi_cho_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbdi_da_xuly.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbdi_qua_han.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_chuathuchien.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_dangthuchien.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_dathuchien.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_dunghan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_cv_quahan.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        tv_vbden_ban_hanh_label.setTypeface(Application.getApp().getTypeface());
        tv_vbden_cho_xuly_label.setTypeface(Application.getApp().getTypeface());
        tv_vbden_da_xuly_label.setTypeface(Application.getApp().getTypeface());
        tv_vbden_qua_han_label.setTypeface(Application.getApp().getTypeface());
        tv_vbdi_ban_hanh_label.setTypeface(Application.getApp().getTypeface());
        tv_vbdi_cho_xuly_label.setTypeface(Application.getApp().getTypeface());
        tv_vbdi_da_xuly_label.setTypeface(Application.getApp().getTypeface());
        tv_vbdi_qua_han_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_chuathuchien_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_dangthuchien_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_dathuchien_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_dunghan_label.setTypeface(Application.getApp().getTypeface());
        tv_cv_quahan_label.setTypeface(Application.getApp().getTypeface());
    }

    private void setupSpinner() {
        final String[] spinnerItems = getResources().getStringArray(R.array.string_array_report);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerItems) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                return v;
            }
            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setTypeface(Application.getApp().getTypeface());
                ((TextView) v).setTextColor(getResources().getColor(R.color.md_black));
                return v;
            }
        };
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        sReport.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        sReport.setSelection(0);
        sReport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                type = String.valueOf(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void displayReportDoc(ReportDocumentInfo object) {
        progressDialog.dismiss();
        tv_vbden_ban_hanh.setText(object.getDenBanHanh());
        tv_vbden_cho_xuly.setText(object.getDenChoXuLy());
        tv_vbden_da_xuly.setText(object.getDenDaXuLy());
        tv_vbden_qua_han.setText(object.getDenQuaHan());
        tv_vbdi_ban_hanh.setText(object.getDiBanHanh());
        tv_vbdi_cho_xuly.setText(object.getDiChoXuLy());
        tv_vbdi_da_xuly.setText(object.getDiDaXuLy());
        tv_vbdi_qua_han.setText(object.getDiQuaHan());
    }

    private void displayReportWork(ReportWorkInfo object) {
        progressDialog.dismiss();
        tv_cv_chuathuchien.setText(String.valueOf(object.getChuaThucHien()));
        tv_cv_dangthuchien.setText(String.valueOf(object.getDangThucHien()));
        tv_cv_dathuchien.setText(String.valueOf(object.getDaThucHien()));
        tv_cv_dunghan.setText(String.valueOf(object.getDungHan()));
        tv_cv_quahan.setText(String.valueOf(object.getQuaHan()));
    }

    @OnClick({R.id.btnReport})
    public void viewReport(View view) {
        if (view.getId() == R.id.btnReport) {
            if (type != null && !type.equals("0")) {
                EventBus.getDefault().postSticky(new TypeReportEvent(type));
                startActivity(new Intent(activityAttach, ReportTemplateActivity.class));
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.REPORT_TYPE_REQUIRED), true, AlertDialogManager.INFO);
            }
        }
    }

    private void getReportDoc() {
        reportPresenter.getReportDocument();
    }

    private void getReportWork() {
        Calendar now = Calendar.getInstance();
        reportPresenter.getReportWork(now.get(Calendar.MONTH) + 1);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activityAttach = (Activity)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        hideProgress();
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            getReportDoc();
            getReportWork();
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(activityAttach, LoginActivity.class));
        activityAttach.finish();
    }

    @Override
    public void showProgress() {
        if (!activityAttach.isFinishing()) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(activityAttach);
                progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
            }

            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (!activityAttach.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onSuccessDoc(ReportDocumentInfo object) {
        displayReportDoc(object);
    }

    @Override
    public void onSuccessWork(ReportWorkInfo object) {
        displayReportWork(object);
    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String init) {/* Init */};

}
