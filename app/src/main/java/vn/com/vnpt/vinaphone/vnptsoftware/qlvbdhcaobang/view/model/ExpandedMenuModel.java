package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/20/2017.
 */
public class ExpandedMenuModel {
    @Getter @Setter private int iconImg = -1;
    @Getter @Setter private String iconName = "";
    @Getter @Setter private int number = 0;

    public ExpandedMenuModel(int iconImg, String iconName) {
        this.iconImg = iconImg;
        this.iconName = iconName;
        this.number = 0;
    }

    public ExpandedMenuModel(int iconImg, String iconName, int number) {
        this.iconImg = iconImg;
        this.iconName = iconName;
        this.number = number;
    }
}
