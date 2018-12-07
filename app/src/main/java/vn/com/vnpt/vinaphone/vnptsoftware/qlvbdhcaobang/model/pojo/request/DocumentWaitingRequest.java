package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/6/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class DocumentWaitingRequest {
    @SerializedName("pageNo")
    @Getter @Setter private String pageNo;
    @SerializedName("pageRec")
    @Getter @Setter private String pageRec;
    @SerializedName("param")
    @Getter @Setter private String parameter;
    @SerializedName("kho")
    @Getter
    @Setter
    private String kho;
}
