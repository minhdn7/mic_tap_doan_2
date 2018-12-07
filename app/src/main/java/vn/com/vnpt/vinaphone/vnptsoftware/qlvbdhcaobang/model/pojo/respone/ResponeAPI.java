package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 7/4/2017.
 */

public class ResponeAPI {
    @SerializedName("code")
    @Setter @Getter
    private String code;

    @SerializedName("message")
    @Setter @Getter
    private String message;
}
