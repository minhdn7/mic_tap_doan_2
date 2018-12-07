package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Event;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.ReportActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ChiDaotEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.EventCheckSMS;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.KhoLoginEvent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.txtDocument) TextView txtDocument;
    @BindView(R.id.txtSchedule)TextView txtSchedule;
    @BindView(R.id.txtContact)TextView txtContact;
    @BindView(R.id.txtReportExt)TextView txtReportExt;
    @BindView(R.id.txtReport)TextView txtReport;
    @BindView(R.id.txtNews)TextView txtNews;
    @BindView(R.id.id_Document) FrameLayout layoutDocument;
    @BindView(R.id.id_Schedule) FrameLayout layoutSchedule;
    @BindView(R.id.id_Contact) FrameLayout layoutContact;
    @BindView(R.id.id_Report_ext) FrameLayout layoutReportExt;
    @BindView(R.id.id_Report) FrameLayout layoutReport;
    @BindView(R.id.id_New) FrameLayout layoutNews;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        txtDocument.setTypeface(Application.getApp().getTypeface());
        txtSchedule.setTypeface(Application.getApp().getTypeface());
        txtContact.setTypeface(Application.getApp().getTypeface());
        txtReportExt.setTypeface(Application.getApp().getTypeface());
        txtReport.setTypeface(Application.getApp().getTypeface());
        txtNews.setTypeface(Application.getApp().getTypeface());
        ((MainActivity) getActivity()).showNotify();
    }

    @OnClick({R.id.id_Document, R.id.id_Schedule, R.id.id_Contact, R.id.id_Report_ext, R.id.id_Report, R.id.id_New})
    public void clickItem(View view) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (view.getId()) {
            case R.id.id_Document:
                Fragment fragment = null;
                String title = null;

                KhoLoginEvent kho = EventBus.getDefault().getStickyEvent(KhoLoginEvent.class);
                for(String khoItem : kho.getKho()){
                    if(khoItem.equals(getString(R.string.tv_report_vanbanden))){
                        //Van ban den
                        fragment = DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_DEN);
                        title = getString(R.string.tv_report_vanbanden);
                    } else if(khoItem.equals("Văn bản đến chờ xử lý")) {
                        // van ban cho xu ly
                        fragment =DocumentWaitingFragment.newInstance(Constants.CONSTANTS_VAN_BAN_DEN_CHO_XU_LY);
                        title = "Văn bản đến chờ xử lý";
                    }
                }
                if (fragment != null) {
                    EventBus.getDefault().postSticky(new EventCheckSMS(true));
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    ((MainActivity) getActivity()).setTitle(title);
                }
                break;
            case R.id.id_Schedule:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduleBossFragment()).commit();
                ((MainActivity) getActivity()).setTitle(getString(R.string.SCHEDULE_MENU));
                break;
            case R.id.id_Contact:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ContactFragment()).commit();
                ((MainActivity) getActivity()).setTitle(getString(R.string.CONTACT_MENU));
                break;
            case R.id.id_Report_ext:
                EventBus.getDefault().postSticky(new ChiDaotEvent(0));
                fragmentManager.beginTransaction().replace(R.id.content_frame, new ChiDaoFragment()).commit();
                ((MainActivity) getActivity()).setTitle(getString(R.string.CHIDAO_NHAN_MENU));
                ((MainActivity) getActivity()).hideNotify();
                break;
            case R.id.id_Report:
                startActivity(new Intent(getActivity(), ReportActivity.class));
                break;
            case R.id.id_New:
                AlertDialogManager.showAlertDialog(getContext(), getString(R.string.TITLE_NOTIFICATION), getString(R.string.FUNCTION_NO_SUPPORT_INFO), true, AlertDialogManager.INFO);
                break;
        }
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
