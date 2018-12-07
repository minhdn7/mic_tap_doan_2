package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.DocumentWaitingAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentwaiting.DocumentWaitingPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentwaiting.IDocumentWaitingPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.ILoginPresenter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.login.LoginPresenterImpl;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.LoginActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.MainActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.InitEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ReloadDocWaitingtEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDocumentWaitingView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ILoginView;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DocumentWaitingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DocumentWaitingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentWaitingFragment extends Fragment implements IDocumentWaitingView, ILoginView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rcvDanhSach) RecyclerView rcvDanhSach;
    @BindView(R.id.txtSearch) EditText txtSearch;
    @BindView(R.id.txtNoData) TextView txtNoData;
    @BindView(R.id.layoutSearch) LinearLayout layoutSearch;
    @BindView(R.id.layoutDisplay) LinearLayout layoutDisplay;
    private IDocumentWaitingPresenter documentWaitingPresenter = new DocumentWaitingPresenterImpl(this);
    private ILoginPresenter loginPresenter = new LoginPresenterImpl(this);
    private ProgressDialog progressDialog;
    private ConnectionDetector connectionDetector;

    private DocumentWaitingAdapter documentWaitingAdapter;
    private List<DocumentWaitingInfo> documentWaitingInfoList;
    private int page = 1;
    private int totalPage;
    private String keyword = "";
    private Activity activityAttach;

    public DocumentWaitingFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment DocumentWaitingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentWaitingFragment newInstance(String param1) {
        DocumentWaitingFragment fragment = new DocumentWaitingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document_waiting, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

//    private void getAllDocuments() {
//        RealmResults<DocumentWaiting> documentWaitingRealms = RealmDao.with(this).findAll(DocumentWaiting.class);
//        documentWaitings = new ArrayList<DisplayDocumentWaiting>();
//        for (int i = 0; i < documentWaitingRealms.size(); i++) {
//            documentWaitings.add(new DisplayDocumentWaiting(documentWaitingRealms.get(i)));
//        }
//    }

    private void init() {
        ((MainActivity) activityAttach).showNotify();
        layoutDisplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(activityAttach.getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
        rcvDanhSach.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                try {
                    imm.hideSoftInputFromWindow(activityAttach.getCurrentFocus().getWindowToken(), 0);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
        txtSearch.setTypeface(Application.getApp().getTypeface());
        txtSearch.addTextChangedListener(
                new TextWatcher() {
                    private Timer timer = new Timer();

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void afterTextChanged(final Editable s) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                // TODO: do what you need here (refresh list)
                                // you will probably need to use runOnUiThread(Runnable action) for some specific actions
                                if(activityAttach == null)
                                    return;
                                activityAttach.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (txtSearch.getText() != null && !txtSearch.getText().toString().trim().equals("")) {
                                            keyword = txtSearch.getText().toString().trim();
                                        } else {
                                            keyword = "";
                                        }
                                        View view = activityAttach.getCurrentFocus();
                                        if (view != null) {
                                            InputMethodManager imm = (InputMethodManager) activityAttach.getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                        }
                                        search();
                                    }
                                });
                            }
                        }, Constants.DELAY_TIME_SEARCH);
                    }
                }
        );
        //getAllDocuments();
        connectionDetector = new ConnectionDetector(getContext());
        page = 1;
        documentWaitingInfoList = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page), String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword,mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
//        if (documentWaitings != null || documentWaitings.size() > 0) {
//            if (documentWaitings.size() >= Constants.DISPLAY_RECORD_SIZE) {
//                displayDocumentWaitings.addAll(documentWaitings.subList(0, Constants.DISPLAY_RECORD_SIZE));
//            } else {
//                displayDocumentWaitings.addAll(documentWaitings.subList(0, documentWaitings.size()));
//            }
//            totalPage = documentWaitings.size() / Constants.DISPLAY_RECORD_SIZE;
//            prepareNewData();
//        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadRefresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

//    private void getDocumentsByKeyword() {
//        Map<String, String> mapFields = new HashMap<String, String>();
//        //mapFields.put("id", keyword != null ? keyword.trim() : "");
//        mapFields.put("ngayNhan", keyword != null ? keyword.trim() : "");
//        //mapFields.put("thoiGianNhan", keyword != null ? keyword.trim() : "");
//        mapFields.put("trichYeu", keyword != null ? keyword.trim() : "");
//        //mapFields.put("trangThai", keyword != null ? keyword.trim() : "");
//        mapFields.put("kiHieu", keyword != null ? keyword.trim() : "");
//        mapFields.put("coQuanBanHanh", keyword != null ? keyword.trim() : "");
//        mapFields.put("ngayVanBan", keyword != null ? keyword.trim() : "");
//        //mapFields.put("congVanDenDi", keyword != null ? keyword.trim() : "");
//        //mapFields.put("processDefinitionId", keyword != null ? keyword.trim() : "");
//        //mapFields.put("processInstanceId", keyword != null ? keyword.trim() : "");
//        mapFields.put("doKhan", keyword != null ? keyword.trim() : "");
//        //mapFields.put("fileDinhKem", keyword != null ? keyword.trim() : "");
//        RealmResults<DocumentWaiting> documentWaitingRealms = RealmDao.with(this).findFilterOr(DocumentWaiting.class, mapFields);
//        documentWaitings = new ArrayList<DisplayDocumentWaiting>();
//        for (int i = 0; i < documentWaitingRealms.size(); i++) {
//            documentWaitings.add(new DisplayDocumentWaiting(documentWaitingRealms.get(i)));
//        }
//    }

    private void search() {
        page = 1;
        documentWaitingInfoList = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page),
                    String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword,mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void loadRefresh() {
        page = 1;
        documentWaitingInfoList = new ArrayList<>();
        if (connectionDetector.isConnectingToInternet()) {
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page),
                    String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword,mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    private void prepareNewData() {
        documentWaitingAdapter = new DocumentWaitingAdapter(getContext(), documentWaitingInfoList);
        documentWaitingAdapter.setLoadMoreListener(new DocumentWaitingAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                rcvDanhSach.post(new Runnable() {
                    @Override
                    public void run() {
                        page++;
//                        if (page <= totalPage + 1) {
//                            loadMore(page);
//                        }
                        if (documentWaitingInfoList.size() % Constants.DISPLAY_RECORD_SIZE == 0) {
                            loadMore(page);
                        }
                    }
                });
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rcvDanhSach.setLayoutManager(mLayoutManager);
        rcvDanhSach.setItemAnimator(new DefaultItemAnimator());
        rcvDanhSach.setAdapter(documentWaitingAdapter);
        documentWaitingAdapter.notifyDataSetChanged();
        if (documentWaitingInfoList != null && documentWaitingInfoList.size() > 0) {
            txtNoData.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.VISIBLE);
        }
    }

    private void loadMore(int page) {
        if (connectionDetector.isConnectingToInternet()) {
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page)
                    , String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword,mParam1));
        } else {
            AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
            EventBus.getDefault().postSticky(new InitEvent(true));
            documentWaitingPresenter.getRecords(new DocumentWaitingRequest(String.valueOf(page),
                    String.valueOf(Constants.DISPLAY_RECORD_SIZE), keyword,mParam1));
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
    public void onSuccessRecords(List<DocumentWaitingInfo> documentWaitingInfos) {
        if (EventBus.getDefault().getStickyEvent(InitEvent.class).isInit()) {
            if (documentWaitingInfos != null && documentWaitingInfos.size() > 0) {
                documentWaitingInfoList.addAll(documentWaitingInfos);
            }
            prepareNewData();
            EventBus.getDefault().postSticky(new InitEvent(false));
        } else {
            documentWaitingInfoList.add(new DocumentWaitingInfo());
            documentWaitingAdapter.notifyItemInserted(documentWaitingInfoList.size() - 1);
            documentWaitingInfoList.remove(documentWaitingInfoList.size() - 1);
            if (documentWaitingInfos != null && documentWaitingInfos.size() > 0) {
                documentWaitingInfoList.addAll(documentWaitingInfos);
            } else {
                documentWaitingAdapter.setMoreDataAvailable(false);
            }
            documentWaitingAdapter.notifyDataChanged();
        }
    }

    @Override
    public void onError(APIError apiError) {
        if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
            if (connectionDetector.isConnectingToInternet()) {
                if (Application.getApp().getAppPrefs().getAccount() == null) {
                    return;
                }
                loginPresenter.getUserLoginPresenter(Application.getApp().getAppPrefs().getAccount());
            } else {
                AlertDialogManager.showAlertDialog(getContext(), getContext().getString(R.string.NETWORK_TITLE_ERROR), getContext().getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
            }
        }
    }

    @Override
    public void showProgress() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void hideProgress() {
        try {
            if (activityAttach== null || activityAttach.isFinishing()) {
                return;
            }
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();

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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(ReloadDocWaitingtEvent reloadDocWaitingtEvent) {
        if (reloadDocWaitingtEvent != null && reloadDocWaitingtEvent.isLoad()) {
            loadRefresh();
        }
        EventBus.getDefault().removeStickyEvent(ReloadDocWaitingtEvent.class);
    }

}
