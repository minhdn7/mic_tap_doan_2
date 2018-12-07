package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/14/2017.
 */

public class RelatedFileInfo {
    @SerializedName("id") @Setter @Getter private int id;
    @SerializedName("name") @Setter @Getter private String name;
    @SerializedName("attachmentId") @Setter @Getter private int attachmentId;
    @SerializedName("creator") @Setter @Getter private String creator;
}
