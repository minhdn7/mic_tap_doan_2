package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/6/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class DocumentNotificationParameter {
    @SerializedName("uyQuyen")
    @Getter @Setter
    private String uyQuyen;
    @SerializedName("param")
    @Getter @Setter
    private String param;
    @SerializedName("status")
    @Getter
    @Setter
    private String status;
}
