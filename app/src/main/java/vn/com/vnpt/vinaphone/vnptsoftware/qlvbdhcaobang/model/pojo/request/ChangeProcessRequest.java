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
public class ChangeProcessRequest {
    @SerializedName("docId")
    @Setter @Getter
    private String docId;
    @SerializedName("primaryProcess")
    @Setter @Getter
    private String primaryProcess;
    @SerializedName("coevalProcess")
    @Setter @Getter
    private String coevalProcess;
    @SerializedName("referProcess")
    @Setter @Getter
    private String referProcess;
    @SerializedName("primaryInternal")
    @Setter @Getter
    private String primaryInternal;
    @SerializedName("coevalInternal")
    @Setter @Getter
    private String coevalInternal;
    @SerializedName("referInternal")
    @Setter @Getter
    private String referInternal;
    @SerializedName("comment")
    @Setter @Getter
    private String comment;
    @SerializedName("type")
    @Setter @Getter
    private String type;
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
