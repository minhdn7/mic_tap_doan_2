package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LienThongInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface ISelectPersonView {
    void onSuccess(Object listPerson);
    void onError(APIError apiError);
    void onSuccessLienThong(List<LienThongInfo> lienThongInfos);
    void showProgress();
    void hideProgress();
}