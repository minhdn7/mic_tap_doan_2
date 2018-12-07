package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.syncevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ContactInfo;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IContactSync {
    void onSuccessSync(List<ContactInfo> contactInfos);
    void onErrorSync(APIError apiError);
    void showProgressSync();
    void hideProgressSync();
}