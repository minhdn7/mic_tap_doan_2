package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/7/2017.
 */

public class ExchangeHandlesInfo {
    @SerializedName("username") @Setter @Getter private String username;
    @SerializedName("fullname") @Setter @Getter private String fullname;
    @SerializedName("comment") @Setter @Getter private String comment;
    @SerializedName("time") @Setter @Getter private String time;
}
