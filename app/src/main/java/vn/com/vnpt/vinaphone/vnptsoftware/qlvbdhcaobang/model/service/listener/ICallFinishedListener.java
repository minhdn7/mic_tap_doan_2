package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener;

/**
 * Created by VietNH on 4/10/2017.
 */

public interface ICallFinishedListener {
    void onCallSuccess(Object object);
    void onCallError(Object object);
}
