package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 4/10/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class HeaderAPI {
    @Getter @Setter private String key;
    @Getter @Setter private String value;
}