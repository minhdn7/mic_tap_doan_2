package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by linhl on 10/25/2018.
 */

public class NumberCountDocument {

    @Expose
    @Getter
    @Setter
    @SerializedName("data")
    public Data data;
    @Expose
    @Getter
    @Setter
    @SerializedName("status")
    public Status status;

    public static class Data {
        @Expose
        @Getter
        @Setter
        @SerializedName("vanBanChoPheDuyet")
        public int vanBanChoPheDuyet;
        @Expose
        @Getter @Setter
        @SerializedName("danhDau")
        public int danhDau;
        @Expose
        @Getter @Setter
        @SerializedName("xemDeBiet")
        public int xemDeBiet;
        @Expose
        @Getter @Setter
        @SerializedName("vanBanDenChoXuLy")
        public int vanBanDenChoXuLy;
        @Expose
        @Getter @Setter
        @SerializedName("vanBanDen")
        public int vanBanDen;
        @Expose
        @Getter @Setter
        @SerializedName("vanBanDi")
        public int vanBanDi;
    }

    public static class Status {
        @Expose
        @Getter @Setter
        @SerializedName("message")
        public String message;
        @Expose
        @Getter @Setter
        @SerializedName("code")
        public String code;
    }
}
