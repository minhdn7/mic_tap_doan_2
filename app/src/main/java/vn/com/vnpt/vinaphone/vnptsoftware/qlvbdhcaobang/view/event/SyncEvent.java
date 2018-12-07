package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/12/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class SyncEvent {
    @Getter @Setter private boolean isSync;
    @Getter @Setter private boolean isSyncSchedule;
}
