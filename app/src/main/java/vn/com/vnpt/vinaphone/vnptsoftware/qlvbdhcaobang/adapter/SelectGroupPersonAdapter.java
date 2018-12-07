package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChangeDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ListGroupPersonEvent;

/**
 * Created by VietNH on 8/31/2017.
 */

public class SelectGroupPersonAdapter extends RecyclerView.Adapter<SelectGroupPersonAdapter.MyViewHolder> {
    private Context context;
    private List<PersonGroupChangeDocInfo> personGroupChangeDocInfoList;
    private List<Boolean> touchs;
    private String type;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNameDV;
        public RecyclerView rcvChild;
        public RadioButton checkXLChinh;
        public CheckBox checkDongXL, checkXemDB;

        public MyViewHolder(View view) {
            super(view);
            txtNameDV = (TextView) view.findViewById(R.id.txtNameDonvi);
            rcvChild = (RecyclerView) view.findViewById(R.id.rcvChild);
            checkXLChinh = (RadioButton) view.findViewById(R.id.checkXLChinh);
            checkDongXL = (CheckBox) view.findViewById(R.id.checkDongXL);
            checkXemDB = (CheckBox) view.findViewById(R.id.checkXemDB);
        }
    }

    @Override
    public SelectGroupPersonAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_person_send_process_list, parent, false);
        return new SelectGroupPersonAdapter.MyViewHolder(itemView);
    }

    public SelectGroupPersonAdapter(Context context, List<PersonGroupChangeDocInfo> personGroupChangeDocInfoList, String type) {
        this.context = context;
        this.personGroupChangeDocInfoList = personGroupChangeDocInfoList;
        this.type = type;
        touchs = new ArrayList<>();
        for (int i = 0; i < personGroupChangeDocInfoList.size(); i++) {
            touchs.add(true);
        }
    }

    @Override
    public void onBindViewHolder(final SelectGroupPersonAdapter.MyViewHolder holder, final int position) {
        holder.txtNameDV.setText(personGroupChangeDocInfoList.get(position).getName());
        List<PersonGroupChangeDocInfo> personGroupChangeDocInfos = EventBus.getDefault().getStickyEvent(ListGroupPersonEvent.class).getPersonGroupChangeDocInfos();
        final List<PersonGroupChangeDocInfo> personGroupChangeDocInfoChilds = new ArrayList<>();
        for (PersonGroupChangeDocInfo personGroupChangeDocInfo : personGroupChangeDocInfos) {
            if (personGroupChangeDocInfo.getParentId() != null &&
                    personGroupChangeDocInfo.getParentId().equals(personGroupChangeDocInfoList.get(position).getId())) {
                personGroupChangeDocInfoChilds.add(personGroupChangeDocInfo);
            }
        }
        if (type.equals("2")) {
            holder.checkDongXL.setVisibility(View.GONE);
        }
        holder.txtNameDV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
        SelectGroupPersonDetailAdapter adapter = new SelectGroupPersonDetailAdapter(context, personGroupChangeDocInfoChilds, false, false,
                holder.checkDongXL, holder.checkXemDB, type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        holder.rcvChild.setLayoutManager(mLayoutManager);
        holder.rcvChild.setItemAnimator(new DefaultItemAnimator());
        holder.rcvChild.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        holder.txtNameDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!touchs.get(position)) {
                    holder.txtNameDV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_minus, 0, 0, 0);
                    holder.rcvChild.setVisibility(View.VISIBLE);
                    touchs.set(position, true);
                } else {
                    holder.txtNameDV.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                    holder.rcvChild.setVisibility(View.GONE);
                    touchs.set(position, false);
                }
            }
        });
        holder.checkDongXL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectGroupPersonDetailAdapter adapter = new SelectGroupPersonDetailAdapter(context, personGroupChangeDocInfoChilds,
                        holder.checkDongXL.isChecked() ? true : false, holder.checkDongXL.isChecked() ? false : holder.checkXemDB.isChecked() ? true : false,
                        holder.checkDongXL, holder.checkXemDB, type);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.rcvChild.setLayoutManager(mLayoutManager);
                holder.rcvChild.setItemAnimator(new DefaultItemAnimator());
                holder.rcvChild.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (holder.checkDongXL.isChecked()) {
                    if (holder.checkXemDB.isChecked()) {
                        holder.checkXemDB.setChecked(false);
                    }
                }
            }
        });
        holder.checkXemDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectGroupPersonDetailAdapter adapter = new SelectGroupPersonDetailAdapter(context, personGroupChangeDocInfoChilds,
                        holder.checkXemDB.isChecked() ? false : holder.checkDongXL.isChecked() ? true : false, holder.checkXemDB.isChecked() ? true : false,
                        holder.checkDongXL, holder.checkXemDB, type);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                holder.rcvChild.setLayoutManager(mLayoutManager);
                holder.rcvChild.setItemAnimator(new DefaultItemAnimator());
                holder.rcvChild.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                if (holder.checkXemDB.isChecked()) {
                    if (holder.checkDongXL.isChecked()) {
                        holder.checkDongXL.setChecked(false);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return personGroupChangeDocInfoList != null && personGroupChangeDocInfoList.size() > 0 ? personGroupChangeDocInfoList.size() : 0;
    }
}
