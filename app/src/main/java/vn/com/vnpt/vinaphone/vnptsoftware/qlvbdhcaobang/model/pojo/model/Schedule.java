package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by VietNH on 8/28/2017.
 */
@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Schedule extends RealmObject {
    @PrimaryKey @Setter @Getter private String id;
    @Setter @Getter private String title;
    @Setter @Getter private String chuTri;
    @Setter @Getter private String position;
    @Setter @Getter private Date startTime;
    @Setter @Getter private Date endTime;
    @Setter @Getter private String type;
}
