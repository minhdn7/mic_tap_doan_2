package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.WeekBossAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.StringDef;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ScheduleBossRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBossInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.schedule.ISchedulePresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.schedule.SchedulePresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IScheduleView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleBossFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleBossFragment extends Fragment implements IScheduleView, ILoginView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.btnPre)
    ImageView btnPre;
    @BindView(R.id.txtDay)
    TextView txtDay;
    @BindView(R.id.btnNext)
    ImageView btnNext;
    @BindView(R.id.rcv_meeting_roadmap)
    RecyclerView rcvMeetingRoadmap;
    Unbinder unbinder;
    @BindView(R.id.txtWeek)
    TextView txtWeek;

    private Calendar firstDayOfWeek;
    private Calendar lastDayOfWeek;
    private SimpleDateFormat sdf = new SimpleDateFormat(StringDef.DATE_FORMAT_SCHEDULE);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ISchedulePresenter schedulePresenter = new SchedulePresenterImpl(this);
    private ConnectionDetector connectionDetector;
    private ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Activity activityAttach;

    public ScheduleBossFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScheduleBossFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleBossFragment newInstance(String param1, String param2) {
        ScheduleBossFragment fragment = new ScheduleBossFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_boss, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        connectionDetector = new ConnectionDetector(getContext());
        firstDayOfWeek = Calendar.getInstance();
        while (firstDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            firstDayOfWeek.add(Calendar.DATE, -1);
        }
        firstDayOfWeek.set(Calendar.HOUR_OF_DAY, 8);
        firstDayOfWeek.set(Calendar.MINUTE, 0);
        lastDayOfWeek = Calendar.getInstance();
        if (lastDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            while (lastDayOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                lastDayOfWeek.add(Calendar.DATE, 1);
            }
        } else {
        }
        lastDayOfWeek.set(Calendar.HOUR_OF_DAY, 17);
        lastDayOfWeek.set(Calendar.MINUTE, 0);
        txtDay.setText(sdf.format(firstDayOfWeek.getTime()) + " - " + sdf.format(lastDayOfWeek.getTime()));
        search();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnPre, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPre:
                firstDayOfWeek.add(Calendar.DATE, -7);
                lastDayOfWeek.add(Calendar.DATE, -7);
                break;
            case R.id.btnNext:
                firstDayOfWeek.add(Calendar.DATE, 7);
                lastDayOfWeek.add(Calendar.DATE, 7);
                break;
        }
        txtDay.setText(sdf.format(firstDayOfWeek.getTime()) + " - " + sdf.format(lastDayOfWeek.getTime()));
        search();
    }

    private void search() {
        int week = firstDayOfWeek.get(Calendar.WEEK_OF_YEAR);
        int year = firstDayOfWeek.get(Calendar.YEAR);
        txtWeek.setText("Tuần " + week + " Năm " + year);
        if (connectionDetector.isConnectingToInternet()) {
            schedulePresenter.getWeekSchedulesBoss(new ScheduleBossRequest(sdf.format(firstDayOfWeek.getTime()), sdf.format(lastDayOfWeek.getTime())));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onSuccessLogin(LoginInfo loginInfo) {
        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
        if (connectionDetector.isConnectingToInternet()) {
            search();
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onErrorLogin(APIError apiError) {
        Application.getApp().getAppPrefs().removeAll();
        startActivity(new Intent(getContext(), LoginActivity.class));
        activityAttach.finish();
    }

    @Override
    public void onSuccess(List<ScheduleInfo> schedules) {

    }

    @Override
    public void onSuccessBoss(List<ScheduleBossInfo> schedules) {
        WeekBossAdapter adapter = new WeekBossAdapter(getContext(), schedules);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvMeetingRoadmap.setLayoutManager(mLayoutManager);
        rcvMeetingRoadmap.setItemAnimator(new DefaultItemAnimator());
        rcvMeetingRoadmap.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.NETWORK_TITLE_ERROR), getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.GET_SCHEDULE_BOSS_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void showProgress() {
        if (activityAttach== null || activityAttach.isFinishing()) {
            return;
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage(getString(R.string.PROCESSING_REQUEST));
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (!activityAttach.isFinishing()) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
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
}
