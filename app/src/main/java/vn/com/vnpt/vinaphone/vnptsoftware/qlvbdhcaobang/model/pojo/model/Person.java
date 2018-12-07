package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(suppressConstructorProperties = true)
public class Person {
    @Setter @Getter private String id;
    @Setter @Getter private String name;
    @Setter @Getter private String unit;
    @Setter @Getter private String avatar;
    @Setter @Getter private boolean xlc;
    @Setter @Getter private boolean dxl;
    @Setter @Getter private boolean xem;
    @Setter @Getter private int unitId;
}
