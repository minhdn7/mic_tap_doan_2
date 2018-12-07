package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.downloadfile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDownloadService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public class DownloadFileDao extends BaseDao implements IDownloadFileDao {
    private IDownloadService downloadService;

    @Override
    public void onDownloadFileDao(int id, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<ResponseBody> call = downloadService.download(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onDownloadFileChiDaoDao(DownloadChiDaoRequest downloadChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<ResponseBody> call = downloadService.download(BaseService.createAuthenHeaders(), downloadChiDaoRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetUrlFileDao(int id, ICallFinishedListener iCallFinishedListener) {
        downloadService = BaseService.createService(IDownloadService.class);
        Call<GetViewFileObj> call = downloadService.getUrlFileDoc(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }
}
