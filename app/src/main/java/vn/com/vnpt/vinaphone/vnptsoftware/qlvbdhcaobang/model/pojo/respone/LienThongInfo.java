package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class LienThongInfo {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("name")
    @Setter @Getter
    private String name;
}
