package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ScheduleDayBoss {
    @SerializedName("codeTime")
    @Setter @Getter
    private String codeTime;
    @SerializedName("place")
    @Setter @Getter
    private String place;
    @SerializedName("participation")
    @Setter @Getter
    private String participation;
    @SerializedName("content")
    @Setter @Getter
    private String content;
}
