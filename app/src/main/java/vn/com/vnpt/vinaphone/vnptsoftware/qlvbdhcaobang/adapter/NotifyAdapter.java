package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.userinfo.UserInfoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.NotifyInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailDocumentNotifyActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailScheduleActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.DetailWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.LetterActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.NotifyActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity.ProfileWorkActivity;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.DetailNotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.NotifyEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.StepPre;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.Notify;

/**
 * Created by VietNH on 8/31/2017.
 */

public class NotifyAdapter extends ArrayAdapter<NotifyInfo> {
    private Context context;
    private int resource;
    private List<NotifyInfo> notifyInfoList;
    private boolean tickAll;
    private ICallFinishedListener callFinishedListener;
    private ConnectionDetector connectionDetector;
    private UserInfoDao userInfoDao;
    private NotifyActivity notifyActivity;

    public NotifyAdapter(Context context, int resource, List<NotifyInfo> notifyInfoList, boolean tickAll) {
        super(context, resource, notifyInfoList);
        this.context = context;
        this.resource = resource;
        this.notifyInfoList = notifyInfoList;
        this.tickAll = tickAll;
        this.notifyActivity = (NotifyActivity) context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        final NotifyInfo notifyInfo = notifyInfoList.get(position);
        LinearLayout layoutNotifyDetail = (LinearLayout) view.findViewById(R.id.layoutNotifyDetail);
        final ImageView imgAvatar = (ImageView) view.findViewById(R.id.img_anh_dai_dien);
        if (notifyInfo.getType() != null && Constants.TYPE_NOTIFY_SCHEDULE.contains(notifyInfo.getType())) {
            Glide.with(context).load(R.drawable.ic_schedule_notify).error(R.drawable.ic_schedule_notify)
                    .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
        } else {
            Glide.with(context).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                    .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
            connectionDetector = new ConnectionDetector(context);
            if (connectionDetector.isConnectingToInternet()) {
                callFinishedListener = new ICallFinishedListener() {
                    @Override
                    public void onCallSuccess(Object object) {
                        try {
                            ResponseBody responseBody = (ResponseBody) object;
                            Glide.with(context).load(responseBody.bytes()).error(R.drawable.ic_avatar)
                                    .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
                        } catch (Exception ex) {

                        }
                    }

                    @Override
                    public void onCallError(Object object) {
                    }
                };
                try {
                    userInfoDao = new UserInfoDao();
                    userInfoDao.onGetAvatarDao(callFinishedListener, notifyInfo.getCreatedUser());
                } catch (Exception ex) {

                }
            }
        }
        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtLink = (TextView) view.findViewById(R.id.txtLink);
        TextView txtDate1 = (TextView) view.findViewById(R.id.txtDate1);
        TextView txtDate2 = (TextView) view.findViewById(R.id.txtDate2);
        final CheckBox checkNotify = (CheckBox) view.findViewById(R.id.checkNotify);
        txtTitle.setTypeface(Application.getApp().getTypeface());
        txtLink.setTypeface(Application.getApp().getTypeface());
        txtDate1.setTypeface(Application.getApp().getTypeface());
        txtDate2.setTypeface(Application.getApp().getTypeface());


        if(notifyInfo.getTitle() != null){
            txtTitle.setText(notifyInfo.getTitle());
        }

        if(notifyInfo.getLink() != null){
            txtLink.setText(notifyInfo.getLink());
        }
        txtTitle.setText(notifyInfo.getTitle());
        try {
            String[] timeStr = notifyInfo.getCreatedDate().split(" ");
            if (Constants.TYPE_NOTIFY_DOCUMENT.contains(notifyInfo.getType())) {
                txtDate1.setText(timeStr[0]);
                txtDate2.setText(timeStr[1]);
            }
            if (Constants.TYPE_NOTIFY_SCHEDULE.contains(notifyInfo.getType())) {
                txtDate1.setText(timeStr[1]);
                txtDate2.setText(timeStr[0]);
                txtDate1.setTextColor(context.getResources().getColor(R.color.md_red_500));
                txtDate2.setTextColor(context.getResources().getColor(R.color.md_blue_800));
            } else {
                txtDate1.setText(timeStr[0]);
                txtDate2.setText(timeStr[1]);
            }
        } catch (Exception ex) {
            txtDate1.setText("");
            txtDate2.setText("");
        }
        if (tickAll) {
            checkNotify.setChecked(true);
        } else {
            checkNotify.setChecked(false);
        }
        checkNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyEvent notifyEvent = EventBus.getDefault().getStickyEvent(NotifyEvent.class);
                List<Notify> notifyList = notifyEvent.getNotifyList();
                if (!checkNotify.isChecked()) {
                    for (int i = 0; i < notifyList.size(); i++) {
                        if (notifyList.get(i).getId().equals(notifyInfo.getId())) {
                            notifyList.remove(i);
                            break;
                        }
                    }
                } else {
                    notifyList.add(new Notify(notifyInfo.getId()));
                }
                notifyEvent.setNotifyList(notifyList);
                EventBus.getDefault().postSticky(notifyEvent);
            }
        });
        layoutNotifyDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: gọi api lấy kho notify
                    String docID = "";
                    if(notifyInfo.getLink() != null){
                        try {
                            docID = notifyInfo.getLink().split("\\|")[1];
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    notifyActivity.getNotify(notifyInfo.getId(), docID);


                    // code cũ check theo type trong listview
//                    connectionDetector = new ConnectionDetector(context);
//                    if (connectionDetector.isConnectingToInternet()) {
//                        if (Constants.TYPE_NOTIFY_DOCUMENT.contains(notifyInfo.getType())) {
//                            EventBus.getDefault().postSticky(new DetailDocumentInfo(null, Constants.DOCUMENT_NOTIFICATION, null));
//                            EventBus.getDefault().postSticky(new DetailNotifyEvent(notifyInfo.getLink(), notifyInfo.getId()));
//                            context.startActivity(new Intent(context, DetailDocumentNotifyActivity.class));
//                            ((Activity) context).finish();
//                        } else {
//                            if (Constants.TYPE_NOTIFY_SCHEDULE.contains(notifyInfo.getType())) {
//                                try {
//                                    WeekViewEvent weekViewEvent = new WeekViewEvent();
//                                    String link = notifyInfo.getLink();
//                                    weekViewEvent.setId(Long.parseLong(link));
//                                    weekViewEvent.setType(Constants.SCHEDULE_MEETING);
//                                    EventBus.getDefault().postSticky(weekViewEvent);
//                                    EventBus.getDefault().postSticky(new StepPre(Constants.SCHEDULE_NOTIFY));
//                                    EventBus.getDefault().postSticky(new DetailNotifyEvent(notifyInfo.getLink(), notifyInfo.getId()));
//                                    context.startActivity(new Intent(context, DetailScheduleActivity.class));
//                                    ((Activity) context).finish();
//                                } catch (Exception ex) {
//                                    AlertDialogManager.showAlertDialog(getContext(), context.getString(R.string.TITLE_NOTIFICATION), context.getString(R.string.SCHEDULE_NO_SUPPORT_INFO), true, AlertDialogManager.INFO);
//                                }
//                            }
//                            if (Constants.TYPE_NOTIFY_WORK.contains(notifyInfo.getType())) {
//                                context.startActivity(new Intent(context, DetailWorkActivity.class));
//                                ((Activity) context).finish();
//                            }
//                            if (Constants.TYPE_NOTIFY_PROFILE.contains(notifyInfo.getType())) {
//                                context.startActivity(new Intent(context, ProfileWorkActivity.class));
//                                ((Activity) context).finish();
//                            }
//                            if (Constants.TYPE_NOTIFY_MAIL.contains(notifyInfo.getType())) {
//                                context.startActivity(new Intent(context, LetterActivity.class));
//                                ((Activity) context).finish();
//                            }
//                        }
//                    } else {
//                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
//                    }
                }
            });
        return view;
    }


}
