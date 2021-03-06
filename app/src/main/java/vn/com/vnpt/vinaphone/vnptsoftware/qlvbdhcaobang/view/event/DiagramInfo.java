package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/11/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class DiagramInfo {
    @SerializedName("insid") @Setter @Getter private String insId;
    @SerializedName("defid") @Setter @Getter private String defId;
    @SerializedName("type") @Setter @Getter private String type;
}
