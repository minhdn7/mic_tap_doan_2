package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model;

import android.view.View;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/25/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonProcessCheck {
    @Getter @Setter private View view;
    @Getter @Setter private String id;
    @Getter @Setter private int unitId;
}
