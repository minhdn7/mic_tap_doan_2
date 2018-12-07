package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class UnitLogInfo {
    @SerializedName("schema")
    @Setter @Getter
    private String schema;
    @SerializedName("unit")
    @Setter @Getter
    private String unit;
    @SerializedName("parameter")
    @Setter @Getter
    private Object parameter;
}
