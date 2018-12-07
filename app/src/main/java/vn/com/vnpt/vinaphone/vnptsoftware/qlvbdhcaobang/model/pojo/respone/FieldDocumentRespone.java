package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/23/2017.
 */

public class FieldDocumentRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private Object data;
}
