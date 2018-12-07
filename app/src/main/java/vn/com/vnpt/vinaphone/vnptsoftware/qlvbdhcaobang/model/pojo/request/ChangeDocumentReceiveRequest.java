package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by VietNH on 8/23/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class ChangeDocumentReceiveRequest {
    @SerializedName("docId")
    @Setter @Getter
    private String docId;
    @SerializedName("chuTri")
    @Setter @Getter
    private String chuTri;
    @SerializedName("approvedValue")
    @Setter @Getter
    private String approvedValue;
    @SerializedName("dongXuLy")
    @Setter @Getter
    private String dongXuLy;
    @SerializedName("strAction")
    @Setter @Getter
    private String strAction;
    @SerializedName("processDefinitionId")
    @Setter @Getter
    private String processDefinitionId;
    @SerializedName("isBanHanh")
    @Setter @Getter
    private String isBanHanh;
    @SerializedName("dongGui")
    @Setter @Getter
    private String dongGui;
    @SerializedName("comment")
    @Setter @Getter
    private String comment;
    @SerializedName("sendType")
    @Setter @Getter
    private String sendType;

    @SerializedName("sms")
    @Setter @Getter
    private int sms;

    @SerializedName("job")
    @Setter @Getter
    private int job;

    @SerializedName("hanXuLy")
    @Setter @Getter
    private String hanXuLy;
}
