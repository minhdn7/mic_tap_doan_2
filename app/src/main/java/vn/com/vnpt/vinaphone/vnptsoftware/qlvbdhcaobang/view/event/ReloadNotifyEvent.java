package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linhl on 10/10/2018.
 */

public class ReloadNotifyEvent {
    @Getter
    @Setter
    private boolean reload;

    public ReloadNotifyEvent(boolean reload) {
        this.reload = reload;
    }
}
