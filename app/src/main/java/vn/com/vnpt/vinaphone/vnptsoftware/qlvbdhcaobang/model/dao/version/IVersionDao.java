package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.version;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IVersionDao {
    void onCheckVersionDao(CheckVersionRequest checkVersionRequest, ICallFinishedListener iCallFinishedListener);
}
