package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.activity;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter.DefaultHomeAdapter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;

public class DefaultHomeActivity extends BaseActivity {

    @BindView(R.id.rcvDanhSach) ListView rcvDanhSach;
    @BindView(R.id.txtFunctionDisplayTitle) TextView txtFunctionDisplayTitle;
    @BindView(R.id.txtFunctionTitle) TextView txtFunctionTitle;
    @BindView(R.id.txtChooseTitle) TextView txtChooseTitle;
    private DefaultHomeAdapter defaultHomeAdapter;
    private Toolbar toolbar;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_home);
        init();
    }

    private void init() {
        setupToolbar();
        txtFunctionDisplayTitle.setTypeface(Application.getApp().getTypeface(), Typeface.BOLD);
        txtFunctionTitle.setTypeface(Application.getApp().getTypeface());
        txtChooseTitle.setTypeface(Application.getApp().getTypeface());
        defaultHomeAdapter = new DefaultHomeAdapter(this, R.layout.item_default_home, getResources().getStringArray(R.array.navigation_drawer_items_left_menu));
        rcvDanhSach.setAdapter(defaultHomeAdapter);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_default_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitleView = (TextView) getWindow().findViewById(actionBarTitle);
        if (actionBarTitleView != null) {
            actionBarTitleView.setTypeface(Application.getApp().getTypeface());
        }
        setTitle(getString(R.string.default_home_title));
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}
