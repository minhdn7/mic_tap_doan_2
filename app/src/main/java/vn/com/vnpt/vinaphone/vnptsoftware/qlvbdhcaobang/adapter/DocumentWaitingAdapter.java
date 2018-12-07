package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentwaiting.DocumentWaitingDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.FileSigning;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.CommentActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailDocumentWaitingActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DiagramActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.ExchangeHandlesActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.NewHistoryDocumentActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.SendDocumentActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DiagramInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.LoginEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.MarkEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TitleTraoDoiXuLyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TraoDoiXuLyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypeChangeEvent;

/**
 * Created by VietNH on 9/5/17.
 */

public class DocumentWaitingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DocumentWaitingInfo> datalist;
    private Context mContext;
    public final int TYPE_NEW = 0;
    public final int TYPE_LOAD = 1;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date) TextView tvDate;
        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_trang_thai) TextView tvTrangThai;
        @BindView(R.id.tv_kh) TextView tvKH;
        @BindView(R.id.tv_cqbh) TextView tvCQBH;
        @BindView(R.id.tv_ngay_vb) TextView tvNgayVB;
        @BindView(R.id.tv_do_khan) TextView tvDoKhan;
        @BindView(R.id.img_file_dinh_kem) ImageView imgFileDinhkem;
        @BindView(R.id.img_click) ImageView imgClick;
        @BindView(R.id.itemDocument) LinearLayout itemDocument;
        @BindView(R.id.tv_trang_thai_label) TextView tvTrangThai_label;
        @BindView(R.id.tv_kh_label) TextView tvKH_label;
        @BindView(R.id.tv_cqbh_label) TextView tvCQBH_label;
        @BindView(R.id.tv_ngay_vb_label) TextView tvNgayVB_label;
        @BindView(R.id.tv_do_khan_label) TextView tvDoKhan_label;
        @BindView(R.id.tv_file_attach_label) TextView imgFileDinhkem_label;
        @BindView(R.id.tv_so_ioffice) TextView tv_so_ioffice;
        @BindView(R.id.tv_text_so_ioff) TextView tv_text_so_ioff;
        private DocumentWaitingDao documentWaitingDao;
        private LoginDao loginDao;
        private ConnectionDetector connectionDetector;
        private ICallFinishedListener callFinishedListener;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final DocumentWaitingInfo newItem) {
            tvDate.setTypeface(Application.getApp().getTypeface());
            tvTime.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
            tvTitle.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
            tvTrangThai.setTypeface(Application.getApp().getTypeface());
            tvKH.setTypeface(Application.getApp().getTypeface());
            tv_text_so_ioff.setTypeface(Application.getApp().getTypeface());
            tvCQBH.setTypeface(Application.getApp().getTypeface());
            tvNgayVB.setTypeface(Application.getApp().getTypeface());
            tvDoKhan.setTypeface(Application.getApp().getTypeface());
            tvTrangThai_label.setTypeface(Application.getApp().getTypeface());
            tvKH_label.setTypeface(Application.getApp().getTypeface());
            tvCQBH_label.setTypeface(Application.getApp().getTypeface());
            tvNgayVB_label.setTypeface(Application.getApp().getTypeface());
            tvDoKhan_label.setTypeface(Application.getApp().getTypeface());
            imgFileDinhkem_label.setTypeface(Application.getApp().getTypeface());
            try {
                if (newItem.getNgayNhan() != null) {
                    try {
                        String[] arr = newItem.getNgayNhan().split(" ");
                        tvTime.setText(arr[1]);
                        tvDate.setText(arr[0]);
                    } catch (Exception ex) {
                        tvTime.setText("");
                        tvDate.setText("");
                    }
                }
                if(newItem.getIoffice() != null){
                    tv_so_ioffice.setText(" " + newItem.getIoffice());
                }
                if (newItem.getTrichYeu() != null) {
                    tvTitle.setText(newItem.getTrichYeu());
                }
                if (newItem.getIsRead() != null) {
                    if (newItem.getIsRead().equals(Constants.IS_READ)) {
                        tvTrangThai.setText(" " + mContext.getString(R.string.IS_READ));
                        tvTitle.setTextColor(mContext.getResources().getColor(R.color.md_black));
                    }
                    if (newItem.getIsRead().equals(Constants.IS_NOT_READ)) {
                        tvTrangThai.setText(" " + mContext.getString(R.string.IS_NOT_READ));
                        tvTitle.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    }
                }
                if (newItem.getSoKihieu() != null) {
                    tvKH.setText(" " + newItem.getSoKihieu());
                }
                if (newItem.getCoQuanBanHanh() != null) {
                    tvCQBH.setText(newItem.getCoQuanBanHanh());
                }
                if (newItem.getNgayVanBan() != null) {
                    tvNgayVB.setText(" " + newItem.getNgayVanBan());
                }
                if (newItem.getDoKhan() != null) {
                    tvDoKhan.setText(" " + newItem.getDoKhan());
                    if (newItem.getDoKhan().equals("Thường")) {
                        tvDoKhan.setTextColor(mContext.getResources().getColor(R.color.md_light_green_status));
                    }
                }
                if (newItem.getHasFile() != null) {
                    if (newItem.getHasFile().equals(Constants.HAS_FILE)) {
                        imgFileDinhkem.setVisibility(View.VISIBLE);
                    }
                    if (newItem.getHasFile().equals(Constants.HAS_NOT_FILE)) {
                        imgFileDinhkem.setVisibility(View.GONE);
                    }
                } else {
                    imgFileDinhkem.setVisibility(View.VISIBLE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            imgClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    List<String> labels = new ArrayList<String>();
                    final List<String> tags = new ArrayList<String>();
//                    if (newItem.getIsChuTri().equals(Constants.SEND_RULE)) {
//                        labels.add(v.getResources().getStringArray(R.array.string_array_more_doc_waiting)[0]);
//                        tags.add(Constants.SEND_TAG);
//                    }
                    if (newItem.getIsSign() != null && newItem.getIsSign().equals(Constants.SIGN_RULE)) {
                        labels.add(v.getResources().getStringArray(R.array.string_array_more_doc_waiting)[3]);
                        tags.add(Constants.SIGN_TAG);
                    }
                    if (!newItem.getIsCheck().equals(Constants.NOT_MARKED)) {
                        labels.add(v.getResources().getStringArray(R.array.string_array_more_doc_waiting)[6]);
                        tags.add(Constants.MARK_TAG);
                    } else {
                        labels.add(v.getResources().getStringArray(R.array.string_array_more_doc_waiting)[1]);
                        tags.add(Constants.MARK_TAG);
                    }
//                    if (newItem.getIsComment().equals(Constants.COMMENT_RULE)) {
//                        labels.add(v.getResources().getStringArray(R.array.string_array_more_doc_waiting)[2]);
//                        tags.add(Constants.COMMENT_TAG);
//                    }

                    if (newItem.getViewComment().equals(Constants.VIEW_COMMENT)) {
                        EventBus.getDefault().postSticky(new TraoDoiXuLyEvent(1));
                        labels.add(v.getResources().getStringArray(R.array.string_array_more_doc_waiting)[7]);
                        tags.add(Constants.VIEW_COMMENT_TAG);
                    } else {
                        EventBus.getDefault().postSticky(new TraoDoiXuLyEvent(0));
                    }

                    if(newItem.getTrichYeu() != null){
                        EventBus.getDefault().postSticky(new TitleTraoDoiXuLyEvent(newItem.getTrichYeu()));
                    }

//                    labels.add(v.getResources().getStringArray(R.array.string_array_more_doc_waiting)[4]);
//                    tags.add(Constants.FLOW_TAG);
                    labels.add(v.getResources().getStringArray(R.array.string_array_more_doc_waiting)[5]);
                    tags.add(Constants.HISTORY_TAG);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.weight_table_menu, R.id.textViewTableMenuItem, labels);
                    final ListPopupWindow listPopupWindow = new ListPopupWindow(mContext);
                    listPopupWindow.setAdapter(adapter);
                    listPopupWindow.setAnchorView(imgClick);
                    listPopupWindow.setWidth(420);
                    listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
                    listPopupWindow.setHorizontalOffset(-402); // margin left
                    listPopupWindow.setVerticalOffset(-20);
                    listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String tag = tags.get(position);
                            switch (tag) {
                                case Constants.SEND_TAG:
                                    EventBus.getDefault().removeStickyEvent(TypeChangeEvent.class);
                                    EventBus.getDefault().removeStickyEvent(ListPersonEvent.class);
                                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(newItem.getId(), newItem.getProcessDefinitionId(), newItem.getCongVanDenDi()));
                                    mContext.startActivity(new Intent(mContext, SendDocumentActivity.class));
                                    break;
                                case Constants.MARK_TAG:
                                    connectionDetector = new ConnectionDetector(mContext);
                                    documentWaitingDao = new DocumentWaitingDao();
                                    callFinishedListener = new ICallFinishedListener() {
                                        @Override
                                        public void onCallSuccess(Object object) {
                                            if (object instanceof MarkDocumentRespone) {
                                                MarkDocumentRespone markDocumentRespone = (MarkDocumentRespone) object;
                                                if (markDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                                    if (markDocumentRespone.getData() != null && markDocumentRespone.getData().equals(Constants.MARKED_SUCCESS)) {
//                                                        Realm realm = RealmDao.with(Application.getApp()).getRealm();
//                                                        realm.beginTransaction();
//                                                        DocumentWaiting documentWaiting = new DocumentWaiting();
//                                                        documentWaiting.setId(newItem.getId());
//                                                        documentWaiting.setCoQuanBanHanh(newItem.getCoQuanBanHanh());
//                                                        documentWaiting.setDoKhan(newItem.getDoKhan());
//                                                        documentWaiting.setFileDinhKem(newItem.getFileDinhKem());
//                                                        documentWaiting.setKiHieu(newItem.getKiHieu());
//                                                        documentWaiting.setTrichYeu(newItem.getTrichYeu());
//                                                        documentWaiting.setNgayVanBan(newItem.getNgayVanBan());
//                                                        documentWaiting.setProcessDefinitionId(newItem.getProcessDefinitionId());
//                                                        documentWaiting.setProcessInstanceId(newItem.getProcessInstanceId());
//                                                        documentWaiting.setTrangThai(newItem.getTrangThai());
//                                                        documentWaiting.setCongVanDenDi(newItem.getCongVanDenDi());
//                                                        documentWaiting.setIsChuTri(newItem.getIsChuTri());
//                                                        documentWaiting.setIsComment(newItem.getIsComment());
//                                                        documentWaiting.setNgayNhan(newItem.getNgayNhan());
//                                                        documentWaiting.setThoiGianNhan(newItem.getThoiGianNhan());
                                                        if (EventBus.getDefault().getStickyEvent(MarkEvent.class).getType().equals(Constants.MARKED)) {
                                                            newItem.setIsCheck(Constants.MARKED);
                                                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.TITLE_SUCCESS), mContext.getString(R.string.MARKED_SUCCESS), true, AlertDialogManager.SUCCESS);
                                                        }
                                                        if (EventBus.getDefault().getStickyEvent(MarkEvent.class).getType().equals(Constants.NOT_MARKED)) {
                                                            newItem.setIsCheck(Constants.NOT_MARKED);
                                                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.TITLE_SUCCESS), mContext.getString(R.string.NOT_MARKED_SUCCESS), true, AlertDialogManager.SUCCESS);
                                                        }
//                                                        documentWaiting.setIsCheck(newItem.getIsCheck());
//                                                        realm.copyToRealmOrUpdate(documentWaiting);
//                                                        realm.commitTransaction();
                                                        notifyDataSetChanged();
                                                    } else {
                                                        if (EventBus.getDefault().getStickyEvent(MarkEvent.class).getType().equals(Constants.MARKED)) {
                                                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.MARK_DOC_TITLE_ERROR), mContext.getString(R.string.MARKED_FAIL), true, AlertDialogManager.ERROR);
                                                        }
                                                        if (EventBus.getDefault().getStickyEvent(MarkEvent.class).getType().equals(Constants.NOT_MARKED)) {
                                                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.MARK_DOC_TITLE_ERROR), mContext.getString(R.string.NOT_MARKED_FAIL), true, AlertDialogManager.ERROR);
                                                        }
                                                    }
                                                }
                                            }
                                            if (object instanceof LoginRespone) {
                                                LoginRespone loginRespone = (LoginRespone) object;
                                                if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                                    LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                                                    Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
                                                    if (connectionDetector.isConnectingToInternet()) {
                                                        if (EventBus.getDefault().getStickyEvent(LoginEvent.class).getAction().equals(Constants.MARK_ACTION)) {
                                                            documentWaitingDao.onMarkDocument(Integer.parseInt(newItem.getId()), this);
                                                        }
                                                    } else {
                                                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                                                    }
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCallError(Object object) {
                                            APIError apiError = (APIError) object;
                                            LoginEvent loginEvent = EventBus.getDefault().getStickyEvent(LoginEvent.class);
                                            if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                                                if (!loginEvent.isLogin()) {
                                                    EventBus.getDefault().postSticky(new LoginEvent(true, loginEvent.getAction()));
                                                    if (connectionDetector.isConnectingToInternet()) {
                                                        loginDao = new LoginDao();
                                                        loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), callFinishedListener);
                                                    } else {
                                                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                                                    }
                                                }
                                            } else {
                                                if (loginEvent.getAction().equals(Constants.MARK_ACTION)) {
                                                    AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.MARK_DOC_TITLE_ERROR), mContext.getString(R.string.DOCUMENT_MARKED_FAIL), true, AlertDialogManager.ERROR);
                                                }
                                            }
                                        }
                                    };
                                    if (newItem.getIsCheck().equals(Constants.MARKED)) {
                                        EventBus.getDefault().postSticky(new LoginEvent(false, Constants.MARK_ACTION));
                                        EventBus.getDefault().postSticky(new MarkEvent(Constants.NOT_MARKED));
                                        if (connectionDetector.isConnectingToInternet()) {
                                            documentWaitingDao.onMarkDocument(Integer.parseInt(newItem.getId()), callFinishedListener);
                                        } else {
                                            AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                                        }
                                    } else {
                                        if (newItem.getIsCheck().equals(Constants.NOT_MARKED)) {
                                            EventBus.getDefault().postSticky(new LoginEvent(false, Constants.MARK_ACTION));
                                            EventBus.getDefault().postSticky(new MarkEvent(Constants.MARKED));
                                            if (connectionDetector.isConnectingToInternet()) {
                                                documentWaitingDao.onMarkDocument(Integer.parseInt(newItem.getId()), callFinishedListener);
                                            } else {
                                                AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                                            }
                                        }
                                    }
                                    break;
                                case Constants.COMMENT_TAG:
                                    if (newItem.getCongVanDenDi().equals(Constants.DOCUMENT_SEND)) {
                                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.TITLE_NOTIFICATION), mContext.getString(R.string.NO_COMMENT_DOC_SEND), true, AlertDialogManager.INFO);
                                    }
                                    if (newItem.getCongVanDenDi().equals(Constants.DOCUMENT_RECEIVE)) {
                                        EventBus.getDefault().postSticky(new DetailDocumentInfo(newItem.getId(), Constants.DOCUMENT_WAITING, null));
                                        mContext.startActivity(new Intent(mContext, CommentActivity.class));
                                    }
                                    break;
                                case Constants.SIGN_TAG:
                                    connectionDetector = new ConnectionDetector(mContext);
                                    documentWaitingDao = new DocumentWaitingDao();



                                    callFinishedListener = new ICallFinishedListener() {
                                        @Override
                                        public void onCallSuccess(Object object) {
                                            if (object instanceof SigningRespone) {
                                                SigningRespone signingRespone = (SigningRespone) object;
                                                if (signingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                                    AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.TITLE_NOTIFICATION), mContext.getString(R.string.DOCUMENT_SIGN_SUCCES), true, AlertDialogManager.INFO);
                                                } else {
                                                    AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.MARK_SIGN_TITLE_ERROR), mContext.getString(R.string.DOCUMENT_SIGN_FAIL), true, AlertDialogManager.ERROR);
                                                }
                                            }
                                            if (object instanceof AttachFileRespone) {
                                                AttachFileRespone attachFileRespone = (AttachFileRespone) object;
                                                if (attachFileRespone != null && attachFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                                    List<AttachFileInfo> attachFileInfoList = ConvertUtils.fromJSONList(attachFileRespone.getData(), AttachFileInfo.class);
                                                    if (attachFileInfoList != null && attachFileInfoList.size() > 0) {
                                                        List<FileSigning> fileSignings = new ArrayList<FileSigning>();
                                                        //String ids = "";
                                                        for (AttachFileInfo attachFileInfo : attachFileInfoList) {
                                                            FileSigning fileSigning = new FileSigning(String.valueOf(attachFileInfo.getId()), attachFileInfo.getName());
                                                            fileSignings.add(fileSigning);
                                                            //ids += fileSigning.getId() + ", ";
                                                        }
//                                                        documentWaitingDao.onSigningDocument( Integer.parseInt(newItem.getId()), callFinishedListener);
                                                    } else {
                                                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.TITLE_NOTIFICATION), mContext.getString(R.string.NOT_FILE_SIGN), true, AlertDialogManager.INFO);
                                                    }
                                                } else {
                                                    AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.MARK_SIGN_TITLE_ERROR), mContext.getString(R.string.FILE_ERROR_SIGN), true, AlertDialogManager.ERROR);
                                                }
                                            }
                                            if (object instanceof LoginRespone) {
                                                LoginRespone loginRespone = (LoginRespone) object;
                                                if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                                    LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                                                    Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
                                                    if (connectionDetector.isConnectingToInternet()) {
                                                        documentWaitingDao.onGetAttachFiles(Integer.parseInt(newItem.getId()), callFinishedListener);
                                                    } else {
                                                        AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                                                    }
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCallError(Object object) {
                                            APIError apiError = (APIError) object;
                                            if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                                                if (connectionDetector.isConnectingToInternet()) {
                                                    loginDao = new LoginDao();
                                                    loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), callFinishedListener);
                                                } else {
                                                    AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.NETWORK_TITLE_ERROR), mContext.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                                                }
                                            } else {
                                                AlertDialogManager.showAlertDialog(mContext, mContext.getString(R.string.MARK_SIGN_TITLE_ERROR), mContext.getString(R.string.DOCUMENT_SIGN_FAIL), true, AlertDialogManager.ERROR);
                                            }
                                        }
                                    };

//                                    documentWaitingDao.onGetAttachFiles(Integer.parseInt(newItem.getId()), callFinishedListener);
                                    //goi api ky file
                                    documentWaitingDao.onSigningDocument( Integer.parseInt(newItem.getId()), callFinishedListener);

                                    break;
                                case Constants.FLOW_TAG:
                                    String insid = "";
                                    String defid = "";
                                    if (newItem.getProcessInstanceId() != null && !newItem.getProcessInstanceId().trim().equals("")) {
                                        insid = newItem.getProcessInstanceId().trim();
                                    }
                                    if (newItem.getProcessDefinitionId() != null && !newItem.getProcessDefinitionId().trim().equals("")) {
                                        defid = newItem.getProcessDefinitionId().trim();
                                    }
                                    EventBus.getDefault().postSticky(new DiagramInfo(insid, defid, Constants.DOCUMENT_WAITING));
                                    mContext.startActivity(new Intent(mContext, DiagramActivity.class));
                                    break;
                                case Constants.HISTORY_TAG:
                                    EventBus.getDefault().postSticky(new DetailDocumentInfo(newItem.getId(), Constants.DOCUMENT_WAITING, null));
                                    mContext.startActivity(new Intent(mContext, NewHistoryDocumentActivity.class));
                                    break;
                                case Constants.VIEW_COMMENT_TAG:
                                    EventBus.getDefault().postSticky(new DetailDocumentInfo(newItem.getId(), Constants.DOCUMENT_WAITING, null));
                                    mContext.startActivity(new Intent(mContext, ExchangeHandlesActivity.class));
                                    break;
                            }
                            listPopupWindow.dismiss();
                        }
                    });
                    listPopupWindow.show();
                }
            });
            itemDocument.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(newItem);
                    if (newItem.getViewComment().equals(Constants.VIEW_COMMENT)) {
                        EventBus.getDefault().postSticky(new TraoDoiXuLyEvent(1));
                    }
                    else {
                        EventBus.getDefault().postSticky(new TraoDoiXuLyEvent(0));
                    }
                    EventBus.getDefault().postSticky(new DetailDocumentInfo(newItem.getId(), Constants.DOCUMENT_WAITING, null));
                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(newItem.getId(), newItem.getProcessDefinitionId(), newItem.getCongVanDenDi()));
                    mContext.startActivity(new Intent(mContext, DetailDocumentWaitingActivity.class));
                }
            });
        }

    }

    public DocumentWaitingAdapter(Context mContext, List<DocumentWaitingInfo> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.document_waiting, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
        if (getItemViewType(position) == TYPE_NEW) {
            ((MyViewHolder) holder).bindData(datalist.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (datalist.get(position) instanceof DocumentWaitingInfo) {
            return TYPE_NEW;
        } else {
            return TYPE_LOAD;
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
    */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }

    public void removeAll(){
        int size = this.datalist.size();
        this.datalist.clear();
        notifyItemRangeRemoved(0, size);
    }

}
