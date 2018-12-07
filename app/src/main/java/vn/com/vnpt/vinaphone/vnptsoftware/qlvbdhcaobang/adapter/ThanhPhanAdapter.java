package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.CircleTransform;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;

/**
 * Created by VietNH on 8/31/2017.
 */

public class ThanhPhanAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;
    private String[] thanhPhanList;

    public ThanhPhanAdapter(Context context, int resource, String[] thanhPhanList) {
        super(context, resource, thanhPhanList);
        this.context = context;
        this.resource = resource;
        this.thanhPhanList = thanhPhanList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        String name = thanhPhanList[position];
        ImageView imgAvatar = (ImageView) view.findViewById(R.id.img_anh_dai_dien);
        Glide.with(context).load(R.drawable.ic_avatar).error(R.drawable.ic_avatar)
                .bitmapTransform(new CircleTransform(context)).into(imgAvatar);
        TextView txtName = (TextView) view.findViewById(R.id.txtName);
        txtName.setTypeface(Application.getApp().getTypeface());
        txtName.setText(name);
        return view;
    }
}
