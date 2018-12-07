package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.chidao.ChiDaoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.PersonReceiveChiDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SavePersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SendChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonChiDaoAddEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonChiDaoDeleteEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.PersonSendChiDaoEvent;

/**
 * Created by VietNH on 8/31/2017.
 */

public class PersonReceiveChiDaoAdapter extends ArrayAdapter<PersonReceiveChiDao> {
    private Context context;
    private int resource;
    private List<PersonReceiveChiDao> personList;
    private ICallFinishedListener callFinishedListener;
    private ConnectionDetector connectionDetector;
    private ChiDaoDao chiDaoDao;
    private String thongTinId;

    public PersonReceiveChiDaoAdapter(Context context, int resource, List<PersonReceiveChiDao> personList, String thongTinId) {
        super(context, resource, personList);
        this.context = context;
        this.resource = resource;
        this.personList = personList;
        this.thongTinId = thongTinId;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        final PersonReceiveChiDao person = personList.get(position);
        ImageView imgAvatar = (ImageView) view.findViewById(R.id.avatarUser);
        Glide.with(context).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
        final TextView txtName = (TextView) view.findViewById(R.id.txtName);
        final TextView txtDonVi = (TextView) view.findViewById(R.id.txtDonVi);
        final TextView txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        final TextView btnRemove = (TextView) view.findViewById(R.id.btnRemove);
        final ImageView btnSend = (ImageView) view.findViewById(R.id.btnSend);
        final LinearLayout layoutReceive = (LinearLayout) view.findViewById(R.id.layoutReceive);
        txtDonVi.setTypeface(Application.getApp().getTypeface());
        txtName.setTypeface(Application.getApp().getTypeface());
        txtStatus.setTypeface(Application.getApp().getTypeface());
        txtName.setText(person.getFullName());
        txtDonVi.setText(person.getUnitName());
        if (person.getNgayNhan() == null) {
            txtStatus.setText(context.getResources().getString(R.string.tv_not_send));
            txtStatus.setTextColor(context.getResources().getColor(R.color.md_red_500));
            btnRemove.setAlpha(1f);
            btnSend.setAlpha(1f);
            btnRemove.setEnabled(true);
            btnSend.setEnabled(true);
        } else {
            txtStatus.setText(context.getResources().getString(R.string.tv_sent));
            txtStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            btnRemove.setAlpha(0.5f);
            btnSend.setAlpha(0.5f);
            btnRemove.setEnabled(false);
            btnSend.setEnabled(false);
        }
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectionDetector = new ConnectionDetector(context);
                if (connectionDetector.isConnectingToInternet()) {
                    callFinishedListener = new ICallFinishedListener() {
                        @Override
                        public void onCallSuccess(Object object) {
                            SavePersonChiDaoRespone savePersonChiDaoRespone = (SavePersonChiDaoRespone) object;
                            if (savePersonChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                List<PersonReceiveChiDao> personReceiveChiDaos = EventBus.getDefault().getStickyEvent(PersonSendChiDaoEvent.class).getPersonReceiveChiDaos();
                                PersonChiDaoDeleteEvent personChiDaoDeleteEvent = EventBus.getDefault().getStickyEvent(PersonChiDaoDeleteEvent.class);
                                List<String> deletes = new ArrayList<>();
                                for (int i = 0; i < personReceiveChiDaos.size(); i++) {
                                    if (personReceiveChiDaos.get(i).getId().equals(person.getId())) {
                                        if (personChiDaoDeleteEvent != null) {
                                            deletes = personChiDaoDeleteEvent.getEmails();
                                            if (deletes != null) {
                                                if (!deletes.contains(personReceiveChiDaos.get(i).getId())) {
                                                    deletes.add(personReceiveChiDaos.get(i).getId());
                                                }
                                            } else {
                                                deletes = new ArrayList<>();
                                                deletes.add(personReceiveChiDaos.get(i).getId());
                                            }
                                        } else {
                                            deletes = new ArrayList<>();
                                            deletes.add(personReceiveChiDaos.get(i).getId());
                                            personChiDaoDeleteEvent = new PersonChiDaoDeleteEvent(deletes);
                                        }
                                        EventBus.getDefault().postSticky(personChiDaoDeleteEvent);
                                        List<String> empAdd = EventBus.getDefault().getStickyEvent(PersonChiDaoAddEvent.class).getEmails();
                                        if (empAdd != null && empAdd.size() > 0) {
                                            if (empAdd.contains(personReceiveChiDaos.get(i).getId())) {
                                                empAdd.remove(personReceiveChiDaos.get(i).getId());
                                            }
                                        }
                                        personReceiveChiDaos.remove(i);
                                        EventBus.getDefault().postSticky(personReceiveChiDaos);
                                        break;
                                    }
                                }
                                layoutReceive.setVisibility(View.GONE);
                                AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_NOTIFICATION), context.getString(R.string.REMOVE_PERSON_SUCCESS), true, AlertDialogManager.SUCCESS);
                            } else {
                                AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_NOTIFICATION), context.getString(R.string.REMOVE_PERSON_ERROR), true, AlertDialogManager.ERROR);
                            }
                        }
                        @Override
                        public void onCallError(Object object) {
                        }
                    };
                } else {
                    AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
                chiDaoDao = new ChiDaoDao();
                chiDaoDao.onSavePersonChiDao(new SavePersonChiDaoRequest(thongTinId, new ArrayList<String>(), Arrays.asList(person.getId())), callFinishedListener);
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectionDetector = new ConnectionDetector(context);
                if (connectionDetector.isConnectingToInternet()) {
                    callFinishedListener = new ICallFinishedListener() {
                        @Override
                        public void onCallSuccess(Object object) {
                            SendChiDaoRespone sendChiDaoRespone = (SendChiDaoRespone) object;
                            if (sendChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                                txtStatus.setText(context.getResources().getString(R.string.tv_sent));
                                txtStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                                btnRemove.setAlpha(0.5f);
                                btnSend.setAlpha(0.5f);
                                btnRemove.setEnabled(false);
                                btnSend.setEnabled(false);
                                List<PersonReceiveChiDao> personReceiveChiDaos = EventBus.getDefault().getStickyEvent(PersonSendChiDaoEvent.class).getPersonReceiveChiDaos();
                                for (int i = 0; i < personReceiveChiDaos.size(); i++) {
                                    if (personReceiveChiDaos.get(i).getId().equals(person.getId())) {
                                        personReceiveChiDaos.get(i).setNgayNhan("Đã gửi");
                                        EventBus.getDefault().postSticky(personReceiveChiDaos);
                                        break;
                                    }
                                }
                                AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_NOTIFICATION), context.getString(R.string.SEND_CHIDAO_SUCCESS), true, AlertDialogManager.SUCCESS);
                            } else {
                                AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_NOTIFICATION), context.getString(R.string.SEND_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
                            }
                        }
                        @Override
                        public void onCallError(Object object) {
                            AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_NOTIFICATION), context.getString(R.string.SEND_CHIDAO_ERROR), true, AlertDialogManager.ERROR);
                        }
                    };
                } else {
                    AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                }
                chiDaoDao = new ChiDaoDao();
                Boolean sms = EventBus.getDefault().getStickyEvent(Boolean.class);
                chiDaoDao.onSendChiDao(new SendChiDaoRequest(thongTinId, sms != null && sms ? "1" : "0", person.getId()), callFinishedListener);
            }
        });
        return view;
    }
}
