package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by VietNH on 9/25/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class PersonChiDaoDeleteEvent {
    @Getter @Setter private List<String> emails;
}
