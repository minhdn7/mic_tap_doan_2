package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ShowProgressEvent {
    @Getter @Setter private boolean show;
}
