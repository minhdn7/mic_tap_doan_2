package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by VietNH on 8/28/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class CountDocument {
    @SerializedName("vanBanDi") @Getter @Setter private Integer vanBanDi;
    @SerializedName("vanBanDen") @Getter @Setter private Integer vanBanDen;
    @SerializedName("vanBanDenChoXuLy") @Getter @Setter private Integer vanBanDenChoXuLy;
    @SerializedName("xemDeBiet") @Getter @Setter private Integer xemDeBiet;
    @SerializedName("danhDau") @Getter @Setter private Integer danhDau;
    @SerializedName("vanBanChoPheDuyet") @Getter @Setter private Integer vanBanChoPheDuyet;
}
