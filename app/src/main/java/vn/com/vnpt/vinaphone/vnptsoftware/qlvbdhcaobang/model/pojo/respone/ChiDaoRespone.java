package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/6/2017.
 */

public class ChiDaoRespone extends StatusRespone {
    @SerializedName("data")
    @Setter @Getter
    private List<Object> data;
}
