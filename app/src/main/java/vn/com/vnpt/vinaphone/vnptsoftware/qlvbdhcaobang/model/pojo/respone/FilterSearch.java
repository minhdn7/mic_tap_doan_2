package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/20/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class FilterSearch {
    @SerializedName("code") @Setter @Getter
    private String code;
    @SerializedName("name") @Setter @Getter
    private String name;
}
