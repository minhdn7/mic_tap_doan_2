package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/21/2017.
 */

public class TypeChangeDocument {
    @SerializedName("type")
    @Setter @Getter private int type;
    @SerializedName("nextStep")
    @Setter @Getter private String nextStep;
    @SerializedName("approvedValue")
    @Setter @Getter private String approvedValue;
    @SerializedName("name")
    @Setter @Getter private String name;
    @SerializedName("formId")
    @Setter @Getter private String formId;
    @SerializedName("roleId")
    @Setter @Getter private String roleId;
    @SerializedName("job")
    @Setter @Getter private String job;

    public TypeChangeDocument() {
    }

    public TypeChangeDocument(String job) {
        this.job = job;
    }
}
