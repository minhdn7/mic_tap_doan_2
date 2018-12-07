package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.version;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.version.VersionDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CheckVersionRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckVersionResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ICheckVersionView;

/**
 * Created by VietNH on 8/29/2017.
 */

public class VersionPresenterImpl implements IVersionPresenter, ICallFinishedListener {
    public ICheckVersionView checkVersionView;
    public VersionDao versionDao;

    public VersionPresenterImpl(ICheckVersionView checkVersionView) {
        this.checkVersionView = checkVersionView;
        this.versionDao = new VersionDao();
    }

    @Override
    public void checkVersion(CheckVersionRequest checkVersionRequest) {
        if (checkVersionView != null) {
            checkVersionView.showProgress();
            versionDao.onCheckVersionDao(checkVersionRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        CheckVersionResponse checkVersionResponse = (CheckVersionResponse) object;
        //checkVersionView.hideProgress();
        if (checkVersionResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            checkVersionView.onSuccess();
        } else {
            checkVersionView.onGoToUpdateStore(checkVersionResponse.getResponeAPI().getMessage());
        }
    }

    @Override
    public void onCallError(Object object) {
        checkVersionView.hideProgress();
        try {
            if (((APIError) object).getMessage().contains("Ứng dụng đang được tiến hành nâng")) {
                checkVersionView.onGoToUpdateStore(((APIError) object).getMessage());
            } else {
                checkVersionView.onError((APIError) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
            checkVersionView.onError((APIError) object);
        }

//        checkVersionView.onSuccess();
    }

}
