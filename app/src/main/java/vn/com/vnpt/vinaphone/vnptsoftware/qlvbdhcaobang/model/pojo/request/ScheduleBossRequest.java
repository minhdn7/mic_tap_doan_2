package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ScheduleBossRequest {
    @SerializedName("startDate") @Setter @Getter private String startDate;
    @SerializedName("endDate") @Setter @Getter private String endDate;
}
