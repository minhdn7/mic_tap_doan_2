package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import io.realm.CompactOnLaunchCallback;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model.ExpandedMenuModel;

/**
 * Created by VietNH on 9/20/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ExpandedMenuModel> mListDataHeader; // header titles

    // child data in format of header title, child title
    private HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> mListDataChild;
    ExpandableListView expandList;

    public ExpandableListAdapter(Context context, List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listChildData, ExpandableListView mView) {
        this.mContext = context;
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        this.expandList = mView;
    }

    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childCount = 0;
        if (groupPosition == Constants.DOCUMENT || groupPosition == Constants.SETTING || groupPosition == Constants.REPORT_EXT) {
            childCount = this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).size();
        }
        return childCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.mListDataChild.get(this.mListDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandedMenuModel headerTitle = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.listheader, null);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.submenu);
        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconimage);
        lblListHeader.setTypeface(Application.getApp().getTypeface());
        lblListHeader.setText(headerTitle.getIconName());
        headerIcon.setImageResource(headerTitle.getIconImg());
        if (headerTitle.getIconName().equalsIgnoreCase(Constants.CONSTANTS_TEXT_QUAN_LY_VAN_BAN) ||
                headerTitle.getIconName().equalsIgnoreCase(Constants.CONSTANTS_TEXT_CAI_DAT_HE_THONG) ||
                headerTitle.getIconName().equalsIgnoreCase(Constants.CONSTANTS_TEXT_THONG_TIN_DIEU_HANH)) {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_expand, 0);
        } else {
            lblListHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ExpandedMenuModel child = (ExpandedMenuModel) getChild(groupPosition, childPosition);
        final ExpandedMenuModel groupObject = (ExpandedMenuModel) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_submenu, null);
        }


        TextView txtListChild = (TextView) convertView.findViewById(R.id.submenu);
        TextView txtNumberDoc = (TextView) convertView.findViewById(R.id.number_Doc);
        ImageView headerIcon = (ImageView) convertView.findViewById(R.id.iconsubimage);
        if (groupObject.getIconName().equalsIgnoreCase(Constants.CONSTANTS_TEXT_QUAN_LY_VAN_BAN)) {
            if (child.getNumber() > 0) {
                txtNumberDoc.setVisibility(View.VISIBLE);
                txtNumberDoc.setText(child.getNumber() + "");
            } else {
                txtNumberDoc.setVisibility(View.GONE);
            }
        } else {
            txtNumberDoc.setVisibility(View.GONE);
        }
        txtListChild.setTypeface(Application.getApp().getTypeface());
        txtListChild.setText(child.getIconName());
        headerIcon.setImageResource(child.getIconImg());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public void updateDataEx(List<ExpandedMenuModel> listDataHeader, HashMap<ExpandedMenuModel, List<ExpandedMenuModel>> listChildData) {
        this.mListDataHeader = listDataHeader;
        this.mListDataChild = listChildData;
        notifyDataSetChanged();
    }
}
