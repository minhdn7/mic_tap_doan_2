package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ScheduleBossInfo;

/**
 * Created by VietNH on 8/31/2017.
 */

public class WeekBossAdapter extends RecyclerView.Adapter<WeekBossAdapter.MyViewHolder> {
    private Context context;
    private List<ScheduleBossInfo> scheduleBossInfoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtDay;
        public RecyclerView rcv_booking_calendar;

        public MyViewHolder(View view) {
            super(view);
            txtDay = (TextView) view.findViewById(R.id.txtDay);
            rcv_booking_calendar = (RecyclerView) view.findViewById(R.id.rcv_booking_calendar);
        }
    }

    @Override
    public WeekBossAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking_calendar, parent, false);
        return new WeekBossAdapter.MyViewHolder(itemView);
    }

    public WeekBossAdapter(Context context, List<ScheduleBossInfo> scheduleBossInfoList) {
        this.context = context;
        this.scheduleBossInfoList = scheduleBossInfoList;
    }

    @Override
    public void onBindViewHolder(WeekBossAdapter.MyViewHolder holder, int position) {
        ScheduleBossInfo scheduleBoss = scheduleBossInfoList.get(position);
        switch (scheduleBoss.getDayOfWeek()) {
            case "MON":
                holder.txtDay.setText("Thứ 2, " + scheduleBoss.getDate());
                break;
            case "TUE":
                holder.txtDay.setText("Thứ 3, " + scheduleBoss.getDate());
                break;
            case "WED":
                holder.txtDay.setText("Thứ 4, " + scheduleBoss.getDate());
                break;
            case "THU":
                holder.txtDay.setText("Thứ 5, " + scheduleBoss.getDate());
                break;
            case "FRI":
                holder.txtDay.setText("Thứ 6, " + scheduleBoss.getDate());
                break;
            case "SAT":
                holder.txtDay.setText("Thứ 7, " + scheduleBoss.getDate());
                break;
            case "SUN":
                holder.txtDay.setText("Chủ nhật, " + scheduleBoss.getDate());
                break;
        }
        DayBossAdapter adapter = new DayBossAdapter(context, scheduleBoss.getScheduleBosses());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        holder.rcv_booking_calendar.setLayoutManager(mLayoutManager);
        holder.rcv_booking_calendar.setItemAnimator(new DefaultItemAnimator());
        holder.rcv_booking_calendar.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return scheduleBossInfoList != null && scheduleBossInfoList.size() > 0 ? scheduleBossInfoList.size() : 0;
    }
}
