package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ListProcessRequest {
    @SerializedName("func")
    @Setter @Getter
    private String func;
    @SerializedName("roleId")
    @Setter @Getter
    private String roleId;
    @SerializedName("approved")
    @Setter @Getter
    private String approved;
    @SerializedName("docId")
    @Setter @Getter
    private String docId;
    @SerializedName("donViSoanThao")
    @Setter @Getter
    private String donViSoanThao;
}
