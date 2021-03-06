package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ReportDocumentInfo {
    @SerializedName("denChoXuLy")
    @Setter @Getter
    private String denChoXuLy;
    @SerializedName("denQuaHan")
    @Setter @Getter
    private String denQuaHan;
    @SerializedName("denDaXuLy")
    @Setter @Getter
    private String denDaXuLy;
    @SerializedName("denBanHanh")
    @Setter @Getter
    private String denBanHanh;
    @SerializedName("diChoXuLy")
    @Setter @Getter
    private String diChoXuLy;
    @SerializedName("diQuaHan")
    @Setter @Getter
    private String diQuaHan;
    @SerializedName("diDaXuLy")
    @Setter @Getter
    private String diDaXuLy;
    @SerializedName("diBanHanh")
    @Setter @Getter
    private String diBanHanh;
}
