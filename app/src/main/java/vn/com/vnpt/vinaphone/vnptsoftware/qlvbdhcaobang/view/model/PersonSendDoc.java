package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/8/17.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonSendDoc {
    @Getter @Setter private String id;
    @Getter @Setter private String name;
    @Getter @Setter private String type;
}
