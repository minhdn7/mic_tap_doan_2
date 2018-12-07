package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;

/**
 * Created by linhl on 10/26/2018.
 */

public class CustomSwitchView  extends LinearLayout {
    private String mSwitchLeftText;
    private String mSwitchRightText;
    private SwitchToggleListener mSwitchToggleListener;
    private SwitchToggleState mSwitchToggleState = SwitchToggleState.LEFT;

    public enum SwitchToggleState {
        LEFT, RIGHT;
    }

    public CustomSwitchView(Context context) {
        super(context);
        initialize();
    }

    public CustomSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();

        TypedArray styleAttrs = getContext().obtainStyledAttributes(
                attrs, R.styleable.Switch);

        String leftSwitch = styleAttrs.getString(R.styleable.Switch_leftSwitch);
        String rightSwitch = styleAttrs.getString(R.styleable.Switch_rightSwitch);
        setSwitches(leftSwitch, rightSwitch);
    }

    public void setSwitchToggleListener(SwitchToggleListener switchToggleListener) {
        mSwitchToggleListener = switchToggleListener;
    }

    public void setSwitches(String switchLeftText, String switchRightText) {

        mSwitchLeftText = switchLeftText;
        mSwitchRightText = switchRightText;

        if (switchLeftText != null && !switchLeftText.isEmpty())
            mSwitchLeftText = switchLeftText;
        else
            throw new IllegalArgumentException("switchLeftText cannot be empty");

        if (switchRightText != null && !switchRightText.isEmpty())
            mSwitchRightText = switchRightText;
        else
            throw new IllegalArgumentException("switchRightText cannot be empty");

        buildSwitch();
    }

    private void initialize() {
        setOrientation(LinearLayout.HORIZONTAL);
        setWeightSum(2);
    }

    private void buildSwitch() {
        buildLeftButton();
        buildRightButton();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void buildLeftButton() {
        Button leftButton = new Button(getContext());
        leftButton.setOnClickListener(buttonClickListener);
        leftButton.setLayoutParams(getButtonLayoutParams());
        leftButton.setId(R.id.leftButton);
        leftButton.setTextSize(10);
        leftButton.setGravity(Gravity.CENTER);
        leftButton.setAllCaps(false);
        leftButton.setText(mSwitchLeftText);
        leftButton.setTextColor(getResources().getColor(R.color.md_white));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            leftButton.setBackground(getContext().getDrawable(R.drawable.switch_left_enabled));
        else
            leftButton.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.switch_left_enabled));

        addView(leftButton);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void buildRightButton() {
        Button rightButton = new Button(getContext());
        rightButton.setOnClickListener(buttonClickListener);
        rightButton.setLayoutParams(getButtonLayoutParams());
        rightButton.setId(R.id.rightButton);
        rightButton.setGravity(Gravity.CENTER);
        rightButton.setText(mSwitchRightText);
        rightButton.setAllCaps(false);
        rightButton.setTextSize(10);
        rightButton.setTextColor(getResources().getColor(R.color.colorPrimary));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            rightButton.setBackground(getContext().getDrawable(R.drawable.switch_right_disabled));
        else
            rightButton.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.switch_right_disabled));

        addView(rightButton);
    }

    private LayoutParams getButtonLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        return layoutParams;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toggleSwitch() {
        Button leftButton = (Button) findViewById(R.id.leftButton);
        Button rightButton = (Button) findViewById(R.id.rightButton);

        if (mSwitchToggleState == SwitchToggleState.LEFT) {
            leftButton.setTextColor(getResources().getColor(R.color.md_white));
            rightButton.setTextColor(getResources().getColor(R.color.colorPrimary));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                leftButton.setBackground(getContext().getDrawable(R.drawable.switch_left_enabled));
            else
                leftButton.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.switch_left_enabled));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                rightButton.setBackground(getContext().getDrawable(R.drawable.switch_right_disabled));
            else
                rightButton.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.switch_right_disabled));
        } else {
            leftButton.setTextColor(getResources().getColor(R.color.colorPrimary));
            rightButton.setTextColor(getResources().getColor(R.color.md_white));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                leftButton.setBackground(getContext().getDrawable(R.drawable.switch_left_disabled));
            else
                leftButton.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.switch_left_disabled));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                rightButton.setBackground(getContext().getDrawable(R.drawable.switch_right_enabled));
            else
                rightButton.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.switch_right_enabled));
        }

        if(mSwitchToggleListener != null)
            mSwitchToggleListener.onSwitchToggle(mSwitchToggleState);
    }

    private OnClickListener buttonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id) {
                case R.id.leftButton:
                    mSwitchToggleState = SwitchToggleState.LEFT;
                    break;
                case R.id.rightButton:
                    mSwitchToggleState = SwitchToggleState.RIGHT;
                    break;
            }

            toggleSwitch();
        }
    };

    public interface SwitchToggleListener {
        void onSwitchToggle(SwitchToggleState switchToggleState);
    }
}

