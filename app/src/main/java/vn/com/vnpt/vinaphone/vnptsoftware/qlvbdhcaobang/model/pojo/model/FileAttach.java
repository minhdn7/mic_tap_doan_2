package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by VietNH on 9/5/17.
 */
@Data
public class FileAttach {
    @SerializedName("id") @Getter @Setter private Integer id;
    @SerializedName("name")  @Getter @Setter private String name;
    @SerializedName("attachmentId")  @Getter @Setter private Integer attachmentId;
    @SerializedName("creator")  @Getter @Setter private String creator;
    @SerializedName("isKy")  @Getter @Setter private String isKy;

}
