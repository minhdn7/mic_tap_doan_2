package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model;

import android.view.View;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/25/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonSendNotifyCheck {
    @Getter @Setter private View view;
    @Getter @Setter private String id;
    @Getter @Setter private String parentId;
    @Getter @Setter private String name;
    @Getter @Setter private String avatar;
    @Getter @Setter private boolean notParent;
}
