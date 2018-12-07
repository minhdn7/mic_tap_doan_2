package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class PersonChiDao {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("fullName")
    @Setter @Getter
    private String fullName;
    @SerializedName("parentId")
    @Setter @Getter
    private String parentId;
    @SerializedName("unitId")
    @Setter @Getter
    private String unitId;
    @SerializedName("email")
    @Setter @Getter
    private String email;
    @SerializedName("chucVu")
    @Setter @Getter
    private String chucVu;
}
