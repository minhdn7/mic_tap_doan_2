package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ParamCheckStoreDocInfo {
    @SerializedName("isChuTri")
    @Setter @Getter
    private String isChuTri;
    @SerializedName("isCheck")
    @Setter @Getter
    private String isCheck;
    @SerializedName("processKey")
    @Setter @Getter
    private String processKey;
    @SerializedName("congVanDenDi")
    @Setter @Getter
    private String congVanDenDi;

    @SerializedName("idThongTin")
    @Setter @Getter
    private String idThongTin;
}
