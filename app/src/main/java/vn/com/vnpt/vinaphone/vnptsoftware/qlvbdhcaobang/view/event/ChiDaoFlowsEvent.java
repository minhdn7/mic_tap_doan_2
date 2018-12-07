package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.ChiDaoFlow;

/**
 * Created by VietNH on 9/25/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class ChiDaoFlowsEvent {
    @Getter @Setter private List<ChiDaoFlow> chiDaoFlows;
}
