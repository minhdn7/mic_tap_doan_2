package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocument;

/**
 * Created by VietNH on 9/12/2017.
 */
@AllArgsConstructor(suppressConstructorProperties = true)
public class TypeChangeEvent {
    @Getter @Setter private String note;
    @Getter @Setter private int selected;
    @Getter @Setter private List<TypeChangeDocument> typeChangeDocumentList;
}
