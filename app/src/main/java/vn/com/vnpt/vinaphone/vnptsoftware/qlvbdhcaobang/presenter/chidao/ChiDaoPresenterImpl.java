package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao;

import org.greenrobot.eventbus.EventBus;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.chidao.ChiDaoDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.downloadfile.DownloadFileDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoGuiRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReplyChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChiDaoFlowRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FileChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonInGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonReceiveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReplyChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SaveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SavePersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SendChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.event.ShowProgressEvent;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChiDaoView;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ChiDaoPresenterImpl implements IChiDaoPresenter, ICallFinishedListener {
    public IChiDaoView chiDaoView;
    public ChiDaoDao chiDaoDao;
    public DownloadFileDao downloadFileDao;

    public ChiDaoPresenterImpl(IChiDaoView chiDaoView) {
        this.chiDaoView = chiDaoView;
        this.chiDaoDao = new ChiDaoDao();
        this.downloadFileDao = new DownloadFileDao();
    }

    @Override
    public void getChiDaoNhan(ChiDaoNhanRequest chiDaoNhanRequest) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onGetChiDaoNhanDao(chiDaoNhanRequest, this);
        }
    }

    @Override
    public void getChiDaoGui(ChiDaoGuiRequest chiDaoGuiRequest) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onGetChiDaoGuiDao(chiDaoGuiRequest, this);
        }
    }

    @Override
    public void getWebViewFile(String urlPath) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onGetWebViewFileDao(urlPath, this);
        }
    }

    @Override
    public void uploadFiles(MultipartBody.Part[] files) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onUploadFileDao(files, this);
        }
    }

    @Override
    public void saveChiDao(ChiDaoRequest chiDaoRequest) {
        if (chiDaoView != null) {
            ShowProgressEvent showProgressEvent = EventBus.getDefault().getStickyEvent(ShowProgressEvent.class);
            if (showProgressEvent != null && showProgressEvent.isShow()) {
                chiDaoView.showProgress();
            }
            chiDaoDao.onSaveChiDao(chiDaoRequest, this);
        }
    }

    @Override
    public void getPersonChiDao(PersonChiDaoRequest personChiDaoRequest) {
        if (chiDaoView != null) {
            chiDaoDao.onGetPersonChiDao(personChiDaoRequest, this);
        }
    }

    @Override
    public void savePersonChiDao(SavePersonChiDaoRequest savePersonChiDaoRequest) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onSavePersonChiDao(savePersonChiDaoRequest, this);
        }
    }

    @Override
    public void getPersonReceiveChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onGetPersonReceiveChiDao(personReceiveChiDaoRequest, this);
        }
    }

    @Override
    public void getPersonGroupChiDao() {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onGetPersonGroupChiDao(this);
        }
    }

    @Override
    public void send(SendChiDaoRequest sendChiDaoRequest) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onSendChiDao(sendChiDaoRequest, this);
        }
    }

    @Override
    public void getFlowChiDao(String id) {
        if (chiDaoView != null) {
            //chiDaoView.showProgress();
            chiDaoDao.onGetFlow(id, this);
        }
    }

    @Override
    public void getFileChiDao(String id) {
        if (chiDaoView != null) {
            chiDaoDao.onGetFile(id, this);
        }
    }

    @Override
    public void getDetailChiDao(String id) {
        if (chiDaoView != null) {
            //chiDaoView.showProgress();
            chiDaoDao.onGetDetail(id, this);
        }
    }

    @Override
    public void downloadFileChiDao(DownloadChiDaoRequest downloadChiDaoRequest) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            downloadFileDao.onDownloadFileChiDaoDao(downloadChiDaoRequest, this);
        }
    }

    @Override
    public void getPersonIngroup(PersonInGroupChiDaoRequest personInGroupChiDaoRequest) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onPersonInGroupChiDao(personInGroupChiDaoRequest, this);
        }
    }

    @Override
    public void getPersonReply(ReplyChiDaoRequest replyChiDaoRequest) {
        if (chiDaoView != null) {
            //chiDaoView.showProgress();
            chiDaoDao.onPersonReplyChiDao(replyChiDaoRequest, this);
        }
    }

    @Override
    public void getPersonReceivedChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest) {
        if (chiDaoView != null) {
            chiDaoView.showProgress();
            chiDaoDao.onGetPersonReceivedChiDao(personReceiveChiDaoRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof ChiDaoRespone) {
            chiDaoView.hideProgress();
            ChiDaoRespone chiDaoRespone = (ChiDaoRespone) object;
            if (chiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(ConvertUtils.fromJSONList(chiDaoRespone.getData(), Object.class));
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof GetViewFileObj) {
            chiDaoView.hideProgress();
            GetViewFileObj chiDaoRespone = (GetViewFileObj) object;
            if (chiDaoRespone.getStatus().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(object);
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof UploadResponse) {
            UploadResponse uploadResponse = (UploadResponse) object;
            if (uploadResponse.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess();
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof SaveChiDaoRespone) {
            chiDaoView.hideProgress();
            SaveChiDaoRespone saveChiDaoRespone = (SaveChiDaoRespone) object;
            if (saveChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(saveChiDaoRespone.getId());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
            ShowProgressEvent showProgressEvent = EventBus.getDefault().getStickyEvent(ShowProgressEvent.class);
            if (showProgressEvent != null && showProgressEvent.isShow()) {
                EventBus.getDefault().removeStickyEvent(ShowProgressEvent.class);
            }
        }
        if (object instanceof PersonChiDaoRespone) {
            chiDaoView.hideProgress();
            PersonChiDaoRespone personChiDaoRespone = (PersonChiDaoRespone) object;
            if (personChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(personChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof SavePersonChiDaoRespone) {
            chiDaoView.hideProgress();
            SavePersonChiDaoRespone savePersonChiDaoRespone = (SavePersonChiDaoRespone) object;
            if (savePersonChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(savePersonChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof PersonReceiveChiDaoRespone) {
            chiDaoView.hideProgress();
            PersonReceiveChiDaoRespone personReceiveChiDaoRespone = (PersonReceiveChiDaoRespone) object;
            if (personReceiveChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(personReceiveChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof PersonGroupChiDaoRespone) {
            PersonGroupChiDaoRespone personGroupChiDaoRespone = (PersonGroupChiDaoRespone) object;
            if (personGroupChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(personGroupChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof SendChiDaoRespone) {
            chiDaoView.hideProgress();
            SendChiDaoRespone sendChiDaoRespone = (SendChiDaoRespone) object;
            if (sendChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(sendChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ChiDaoFlowRespone) {
            ChiDaoFlowRespone chiDaoFlowRespone = (ChiDaoFlowRespone) object;
            if (chiDaoFlowRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(chiDaoFlowRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof FileChiDaoRespone) {
            //chiDaoView.hideProgress();
            FileChiDaoRespone fileChiDaoRespone = (FileChiDaoRespone) object;
            if (fileChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(fileChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof DetailChiDaoRespone) {
            DetailChiDaoRespone detailChiDaoRespone = (DetailChiDaoRespone) object;
            if (detailChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(detailChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ResponseBody) {
            chiDaoView.hideProgress();
            ResponseBody responseBody = (ResponseBody) object;
            if (responseBody != null) {
                chiDaoView.onSuccess(responseBody);
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof PersonInGroupChiDaoRespone) {
            chiDaoView.hideProgress();
            PersonInGroupChiDaoRespone personInGroupChiDaoRespone = (PersonInGroupChiDaoRespone) object;
            if (personInGroupChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(personInGroupChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ReplyChiDaoRespone) {
            ReplyChiDaoRespone replyChiDaoRespone = (ReplyChiDaoRespone) object;
            if (replyChiDaoRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                chiDaoView.onSuccess(replyChiDaoRespone.getData());
            } else {
                chiDaoView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        chiDaoView.hideProgress();
        chiDaoView.onError((APIError) object);
    }

}
