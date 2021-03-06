package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import java.util.List;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.JobPositionInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitInfo;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IFilterPersonView {
    void onSuccessJobPossitions(List<JobPositionInfo> jobPositionInfos);
    void onSuccessUnits(List<UnitInfo> unitInfos);
    void onError(APIError apiError);
}