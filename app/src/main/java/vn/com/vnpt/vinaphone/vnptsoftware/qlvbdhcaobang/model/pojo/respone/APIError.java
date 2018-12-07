package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 4/14/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class APIError {
    @Getter @Setter private int code;
    @Getter @Setter private String message;
}