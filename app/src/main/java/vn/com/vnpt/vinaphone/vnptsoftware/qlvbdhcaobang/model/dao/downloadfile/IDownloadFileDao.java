package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.downloadfile;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IDownloadFileDao {
    void onDownloadFileDao(int id, ICallFinishedListener iCallFinishedListener);
    void onGetUrlFileDao(int id, ICallFinishedListener iCallFinishedListener);
    void onDownloadFileChiDaoDao(DownloadChiDaoRequest downloadChiDaoRequest, ICallFinishedListener iCallFinishedListener);

}
