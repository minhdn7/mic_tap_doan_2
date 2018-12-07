package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/7/2017.
 */

public class CountDocumentExpired {
    @SerializedName("pageNo")
    @Setter @Getter
    private int pageNo;
    @SerializedName("pageRec")
    @Setter @Getter
    private int pageRec;
}
