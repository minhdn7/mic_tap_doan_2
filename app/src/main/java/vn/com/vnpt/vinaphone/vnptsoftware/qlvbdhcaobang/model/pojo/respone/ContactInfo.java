package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ContactInfo {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("userName")
    @Setter @Getter
    private String username;
    @SerializedName("parentId")
    @Setter @Getter
    private String parentId;
    @SerializedName("dataInfo")
    @Setter @Getter
    private String dataInfo;
    @SerializedName("position")
    @Setter @Getter
    private String position;
    @SerializedName("unitName")
    @Setter @Getter
    private String unitName;
}
