package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocument;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IGetTypeChangeDocumentView {
    void onSuccessTypeChange(List<TypeChangeDocument> typeChangeDocumentList);
    void onErrorTypeChange(APIError apiError);
    void showProgress();
    void hideProgress();
}