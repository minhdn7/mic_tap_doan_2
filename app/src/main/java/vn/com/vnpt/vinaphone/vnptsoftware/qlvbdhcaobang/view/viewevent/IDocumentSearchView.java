package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentSearch;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentSearchInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IDocumentSearchView {
    void onSuccessRecords(List<DocumentSearchInfo> documentSearchInfos);
    void onError(APIError apiError);
    void onSuccessCount(CountDocumentSearch countDocumentSearch);
    void showProgress();
    void hideProgress();
}