package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/14/2017.
 */

public class NotifyInfo {
    @SerializedName("id") @Setter @Getter private String id;
    @SerializedName("link") @Setter @Getter private String link;
    @SerializedName("type") @Setter @Getter private String type;
    @SerializedName("createdDate") @Setter @Getter private String createdDate;
    @SerializedName("createdUser") @Setter @Getter private String createdUser;
    @SerializedName("title") @Setter @Getter private String title;
    @SerializedName("avatar") @Setter @Getter private String avatar;
}
