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
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ExchangeHandlesInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.CommentActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailDocumentWaitingActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DiagramActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.NewHistoryDocumentActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.SendDocumentActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DiagramInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListPersonEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.LoginEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.MarkEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.TypeChangeEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.UserNameEvent;

/**
 * Created by VietNH on 9/5/17.
 */

public class ExchangeHandlesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ExchangeHandlesInfo> datalist;
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
        @BindView(R.id.fullname)
        TextView fullname;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.comment)
        TextView comment;

        @BindView(R.id.khach)
        LinearLayout khach;
        @BindView(R.id.chu)
        LinearLayout chu;
        @BindView(R.id.timeAdmin)
        TextView timeAdmin;
        @BindView(R.id.commentAdmin)
        TextView commentAdmin;


        private ConnectionDetector connectionDetector;
        private ICallFinishedListener callFinishedListener;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindData(final ExchangeHandlesInfo newItem) {
            if (datalist != null) {
                fullname.setTypeface(Application.getApp().getTypeface());
                time.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
                comment.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);


                try {

                    UserNameEvent username = EventBus.getDefault().getStickyEvent(UserNameEvent.class);
                    if (username != null) {
                        if (username.getUserName().equals(newItem.getUsername())) {
                            // neu la chu hien ben phai
                            khach.setVisibility(View.GONE);
                            chu.setVisibility(View.VISIBLE);

                            if (newItem.getTime() != null) {
                                timeAdmin.setText(newItem.getTime());
                            }
                            if (newItem.getComment() != null) {
                                commentAdmin.setText(newItem.getComment());
                            }

                        } else {
                            //neu la khach hien ben trai
                            khach.setVisibility(View.VISIBLE);
                            chu.setVisibility(View.GONE);

                            if (newItem.getFullname() != null) {
                                fullname.setText(newItem.getFullname());
                            }
                            if (newItem.getTime() != null) {
                                time.setText("  " + newItem.getTime());
                            }
                            if (newItem.getComment() != null) {
                                comment.setText(newItem.getComment());
                            }
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


//            itemDocument.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EventBus.getDefault().postSticky(newItem);
//                    EventBus.getDefault().postSticky(new DetailDocumentInfo(newItem.getId(), Constants.DOCUMENT_WAITING, null));
//                    EventBus.getDefault().postSticky(new TypeChangeDocumentRequest(newItem.getId(), newItem.getProcessDefinitionId(), newItem.getCongVanDenDi()));
//                    mContext.startActivity(new Intent(mContext, DetailDocumentWaitingActivity.class));
//                }
//            });
        }

    }

    public ExchangeHandlesAdapter(Context mContext, List<ExchangeHandlesInfo> datalist) {
        this.mContext = mContext;
        this.datalist = datalist;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == TYPE_NEW) {
            return new MyViewHolder(inflater.inflate(R.layout.exchange_handles_list, parent, false));
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
        if (datalist.get(position) instanceof ExchangeHandlesInfo) {
            return TYPE_NEW;
        } else {
            return TYPE_LOAD;
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
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
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public void removeAll() {
        int size = this.datalist.size();
        this.datalist.clear();
        notifyItemRangeRemoved(0, size);
    }

}
