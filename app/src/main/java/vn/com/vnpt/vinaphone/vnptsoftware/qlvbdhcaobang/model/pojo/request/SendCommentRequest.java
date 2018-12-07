package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/6/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class SendCommentRequest {
    @SerializedName("docId")
    @Getter @Setter private Integer docId;
    @SerializedName("comments")
    @Getter @Setter private String comments;
}
