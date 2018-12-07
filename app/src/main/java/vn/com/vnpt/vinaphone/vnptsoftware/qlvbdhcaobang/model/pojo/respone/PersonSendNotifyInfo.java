package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 8/29/2017.
 */

public class PersonSendNotifyInfo {
    @SerializedName("id")
    @Setter @Getter
    private String id;
    @SerializedName("name")
    @Setter @Getter
    private String name;
    @SerializedName("parentId")
    @Setter @Getter
    private String parentId;
    @SerializedName("level")
    @Setter @Getter
    private int level;
    @Setter @Getter
    private boolean isTrace;
    @Setter @Getter
    private List<PersonSendNotifyInfo> childrenList;

}
