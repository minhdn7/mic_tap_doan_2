package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 4/18/2017.
 */
public class TypeChangeDocumentRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private Object data;
}
