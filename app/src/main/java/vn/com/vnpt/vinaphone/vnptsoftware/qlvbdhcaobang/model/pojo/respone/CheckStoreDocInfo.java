package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class CheckStoreDocInfo {
    @SerializedName("type")
    @Setter @Getter
    private String type;
    @SerializedName("parameter")
    @Setter @Getter
    private ParamCheckStoreDocInfo paramCheckStoreDocInfo;
}
