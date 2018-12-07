package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.NotifyInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface INotifyView {
    void onSuccess(List<NotifyInfo> notifyInfos);
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
}