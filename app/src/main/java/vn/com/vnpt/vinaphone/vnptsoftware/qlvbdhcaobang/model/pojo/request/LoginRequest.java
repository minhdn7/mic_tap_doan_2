package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 4/18/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class LoginRequest {
    @SerializedName("username")
    @Setter @Getter
    private String username;
    @SerializedName("password")
    @Setter @Getter
    private String password;
    @SerializedName("tokenFireBase")
    @Setter @Getter
    private String tokenFireBase;
}
