package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IHistoryDocumentView {
    void onSuccess(List<UnitLogInfo> unitLogInfoList);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}