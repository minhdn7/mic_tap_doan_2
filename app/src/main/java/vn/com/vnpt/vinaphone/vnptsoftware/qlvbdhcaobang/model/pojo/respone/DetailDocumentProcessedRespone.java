package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.DetailDocumentProcessed;

/**
 * Created by VietNH on 8/23/2017.
 */

public class DetailDocumentProcessedRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private DetailDocumentProcessed data;
}
