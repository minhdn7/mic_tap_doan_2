package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/7/2017.
 */

public class LoginInfo {
    @SerializedName("username")
    @Setter @Getter
    private String username;
    @SerializedName("password")
    @Setter @Getter
    private String password;
    @SerializedName("agent")
    @Setter @Getter
    private String agent;
    @SerializedName("agentInfo")
    @Setter @Getter
    private String agentInfo;
    @SerializedName("fullName")
    @Setter @Getter
    private String fullName;
    @SerializedName("avatar")
    @Setter @Getter
    private String avatar;
    @SerializedName("unitName")
    @Setter @Getter
    private String unitName;
    @SerializedName("token")
    @Setter @Getter
    private String token;
    @SerializedName("kho")
    @Setter
    @Getter
    private List<String> listKhoVanBan;
    @SerializedName("switchUser")
    @Setter
    @Getter
    private List<UserSwitch> listSwitchUser;

    public class UserSwitch {
        @SerializedName("userid")
        @Setter @Getter
        private String userid;
        @SerializedName("name")
        @Setter @Getter
        private String name;
    }

}
