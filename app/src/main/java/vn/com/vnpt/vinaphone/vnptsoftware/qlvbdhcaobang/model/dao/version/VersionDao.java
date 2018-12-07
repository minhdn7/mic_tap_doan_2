package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.version;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckVersionResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IVersionService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public class VersionDao extends BaseDao implements IVersionDao {
    private IVersionService versionService;

    @Override
    public void onCheckVersionDao(CheckVersionRequest checkVersionRequest, ICallFinishedListener iCallFinishedListener) {
        versionService = BaseService.createService(IVersionService.class);
        Call<CheckVersionResponse> call = versionService.checkVersion(BaseService.createAuthenHeaders(), checkVersionRequest);
        call(call, iCallFinishedListener);
    }
}
