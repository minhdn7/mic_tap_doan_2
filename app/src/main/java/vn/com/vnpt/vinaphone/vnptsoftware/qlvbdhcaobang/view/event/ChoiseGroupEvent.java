package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/12/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ChoiseGroupEvent {
    @Getter @Setter private String groups;
    @Getter @Setter private String type;
}
