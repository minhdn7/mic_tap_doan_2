package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ScheduleBossInfo {
    @SerializedName("dayOfWeek")
    @Setter @Getter
    private String dayOfWeek;
    @SerializedName("date")
    @Setter @Getter
    private String date;
    @SerializedName("scheduleBosses")
    @Setter @Getter
    private List<ScheduleBoss> scheduleBosses;
}
