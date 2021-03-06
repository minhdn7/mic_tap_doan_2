package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class SearchPersonRequest {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("name")
    @Setter @Getter
    private String name;
    @SerializedName("jobPosition")
    @Setter @Getter
    private String jobPosition;
}
