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
public class ChiDaoGui {
    @Getter @Setter private String id;
    @Getter @Setter private String tieuDe;
    @Getter @Setter private String parentId;
    @Getter @Setter private String ngayTao;
    @Getter @Setter private String daGui;
    @Getter @Setter private int fileCount;
}