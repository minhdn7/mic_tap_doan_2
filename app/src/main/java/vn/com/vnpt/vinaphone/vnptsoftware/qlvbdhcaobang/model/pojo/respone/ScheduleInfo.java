package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ScheduleInfo {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("title")
    @Setter @Getter
    private String title;
    @SerializedName("chuTri")
    @Setter @Getter
    private String chuTri;
    @SerializedName("position")
    @Setter @Getter
    private String position;
    @SerializedName("startTime")
    @Setter @Getter
    private String startTime;
    @SerializedName("endTime")
    @Setter @Getter
    private String endTime;
    @SerializedName("type")
    @Setter @Getter
    private String type;
}
