package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.HistoryAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.LogData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProcessingHistoryDocumentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProcessingHistoryDocumentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProcessingHistoryDocumentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProcessingHistoryDocumentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProcessingHistoryDocumentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProcessingHistoryDocumentFragment newInstance(String param1, String param2) {
        ProcessingHistoryDocumentFragment fragment = new ProcessingHistoryDocumentFragment();
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
        View view = inflater.inflate(R.layout.fragment_processing_history_document, container, false);
        LinearLayout layout_log = (LinearLayout) view.findViewById(R.id.layout_log);
        List<UnitLogInfo> unitLogInfoList = EventBus.getDefault().getStickyEvent(LogData.class).getUnitLogInfoList();
        HistoryAdapter logAdapter = new HistoryAdapter(getContext(), R.layout.item_history_list, unitLogInfoList, Constants.PROCESSING_HISTORY);
        int adapterCount = logAdapter.getCount();
        for (int i = 0; i < adapterCount; i++) {
            View item = logAdapter.getView(i, null, null);
            List<LogInfo> logInfos = ConvertUtils.fromJSONList(unitLogInfoList.get(i).getParameter(), LogInfo.class);
            if (logInfos != null && logInfos.size() > 0) {
                for (int j = 0; j < logInfos.size(); j++) {
                    if (logInfos.get(j).getStatus() != null && logInfos.get(j).getStatus().equals(Constants.PROCESSING_HISTORY)) {
                        layout_log.addView(item);
                        break;
                    }
                }
            }
        }
        return view;
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
