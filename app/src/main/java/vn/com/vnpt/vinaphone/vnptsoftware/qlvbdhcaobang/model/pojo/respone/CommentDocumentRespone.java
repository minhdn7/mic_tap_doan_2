package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/12/2017.
 */

public class CommentDocumentRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private String data;
}
