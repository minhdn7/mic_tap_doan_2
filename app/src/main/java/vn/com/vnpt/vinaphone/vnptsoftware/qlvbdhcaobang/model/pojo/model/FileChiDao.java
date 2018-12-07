package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by VietNH on 8/28/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class FileChiDao {
    @Getter @Setter private String name;
    @Getter @Setter private String hdd;
    @Getter @Setter private int attachmentId;
}
