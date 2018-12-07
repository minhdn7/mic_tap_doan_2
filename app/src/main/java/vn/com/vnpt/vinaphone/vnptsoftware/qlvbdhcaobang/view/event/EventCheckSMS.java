package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linhl on 8/17/2018.
 */

public class EventCheckSMS {
    public EventCheckSMS(boolean isShow) {
        IsShow = isShow;
    }

    @Setter
    @Getter
    private boolean IsShow;
}
