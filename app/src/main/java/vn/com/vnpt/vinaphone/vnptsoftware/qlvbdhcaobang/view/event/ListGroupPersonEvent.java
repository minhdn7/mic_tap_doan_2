package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChangeDocInfo;

/**
 * Created by VietNH on 9/11/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ListGroupPersonEvent {
    @Setter @Getter private List<PersonGroupChangeDocInfo> personGroupChangeDocInfos;
}
