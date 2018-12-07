package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentnotification;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentnotification.DocumentNotificationDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentNotificationRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentNotification;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentNotificationInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDetailDocumentNotificationView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDocumentDiagramView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDocumentNotificationView;

/**
 * Created by VietNH on 9/12/2017.
 */

public class DocumentNotificationPresenterImpl implements IDocumentNotificationPresenter, ICallFinishedListener {
    public IDocumentNotificationView documentNotificationView;
    public IDetailDocumentNotificationView detailDocumentNotificationView;
    public IDocumentDiagramView documentDiagramView;
    public DocumentNotificationDao documentNotificationDao;

    public DocumentNotificationPresenterImpl(IDocumentNotificationView documentNotificationView) {
        this.documentNotificationView = documentNotificationView;
        this.documentNotificationDao = new DocumentNotificationDao();
    }

    public DocumentNotificationPresenterImpl(IDetailDocumentNotificationView detailDocumentNotificationView) {
        this.detailDocumentNotificationView = detailDocumentNotificationView;
        this.documentNotificationDao = new DocumentNotificationDao();
    }

    public DocumentNotificationPresenterImpl(IDocumentDiagramView documentDiagramView) {
        this.documentDiagramView = documentDiagramView;
        this.documentNotificationDao = new DocumentNotificationDao();
    }

    @Override
    public void getBitmapDiagram(String insId, String defId) {
        if (documentDiagramView != null) {
            documentDiagramView.showProgress();
            documentNotificationDao.onGetBitmapDiagram(insId, defId, this);
        }
    }

    @Override
    public void mark(int id) {
        if (detailDocumentNotificationView != null) {
            detailDocumentNotificationView.showProgress();
            documentNotificationDao.onMarkDocument(id, this);
        }
    }

    @Override
    public void getCount(DocumentNotificationRequest documentNotificationRequest) {
        if (documentNotificationView != null) {
            documentNotificationView.showProgress();
            documentNotificationDao.onCountDocumentNotificationDao(documentNotificationRequest, this);
        }
    }

    @Override
    public void getRecords(DocumentNotificationRequest documentNotificationRequest) {
        if (documentNotificationView != null) {
            documentNotificationView.showProgress();
            documentNotificationDao.onRecordsDocumentNotificationDao(documentNotificationRequest, this);
        }
    }

    @Override
    public void getDetail(int id) {
        if (detailDocumentNotificationView != null) {
            detailDocumentNotificationView.showProgress();
            documentNotificationDao.onGetDetail(id, this);
        }
    }

    @Override
    public void getLogs(int id) {
        if (detailDocumentNotificationView != null) {
            documentNotificationDao.onGetLogs(id, this);
        }
    }

    @Override
    public void getAttachFiles(int id) {
        if (detailDocumentNotificationView != null) {
            documentNotificationDao.onGetAttachFiles(id, this);
        }
    }

    @Override
    public void getRelatedDocs(int id) {
        if (detailDocumentNotificationView != null) {
            documentNotificationDao.onGetRelatedDocs(id, this);
        }
    }

    @Override
    public void getRelatedFiles(int id) {
        if (detailDocumentNotificationView != null) {
            documentNotificationDao.onGetRelatedFiles(id, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof ResponseBody) {
            documentDiagramView.hideProgress();
            ResponseBody responseBody = (ResponseBody) object;
            if (responseBody != null) {
                Bitmap bm = BitmapFactory.decodeStream(responseBody.byteStream());
                documentDiagramView.onSuccessDiagram(bm);
            } else {
                documentDiagramView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof CountDocumentNotificationRespone) {
            documentNotificationView.hideProgress();
            CountDocumentNotificationRespone countDocumentNotificationRespone = (CountDocumentNotificationRespone) object;
            if (countDocumentNotificationRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentNotificationView.onSuccessCount(ConvertUtils.fromJSON(countDocumentNotificationRespone.getData(), CountDocumentNotification.class));
            } else {
                documentNotificationView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof DocumentNotificationRespone) {
            documentNotificationView.hideProgress();
            DocumentNotificationRespone documentNotificationRespone = (DocumentNotificationRespone) object;
            if (documentNotificationRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                documentNotificationView.onSuccessRecords(ConvertUtils.fromJSONList(documentNotificationRespone.getData(), DocumentNotificationInfo.class));
            } else {
                documentNotificationView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof DetailDocumentNotificationRespone) {
            DetailDocumentNotificationRespone detailDocumentNotificationRespone = (DetailDocumentNotificationRespone) object;
            if (detailDocumentNotificationRespone != null && detailDocumentNotificationRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentNotificationView.onSuccess(detailDocumentNotificationRespone.getData());
            } else {
                detailDocumentNotificationView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof AttachFileRespone) {
            AttachFileRespone attachFileRespone = (AttachFileRespone) object;
            if (attachFileRespone != null && attachFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentNotificationView.onSuccess(ConvertUtils.fromJSONList(attachFileRespone.getData(), AttachFileInfo.class));
            } else {
                detailDocumentNotificationView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof RelatedFileRespone) {
            RelatedFileRespone relatedFileRespone = (RelatedFileRespone) object;
            if (relatedFileRespone != null && relatedFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentNotificationView.onSuccess(ConvertUtils.fromJSONList(relatedFileRespone.getData(), RelatedFileInfo.class));
            } else {
                detailDocumentNotificationView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof RelatedDocumentRespone) {
            RelatedDocumentRespone relatedDocumentRespone = (RelatedDocumentRespone) object;
            if (relatedDocumentRespone != null && relatedDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentNotificationView.onSuccess(ConvertUtils.fromJSONList(relatedDocumentRespone.getData(), RelatedDocumentInfo.class));
            } else {
                detailDocumentNotificationView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof LogRespone) {
            detailDocumentNotificationView.hideProgress();
            LogRespone logRespone = (LogRespone) object;
            if (logRespone != null && logRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentNotificationView.onSuccess(ConvertUtils.fromJSONList(logRespone.getData(), UnitLogInfo.class));
            } else {
                detailDocumentNotificationView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof MarkDocumentRespone) {
            detailDocumentNotificationView.hideProgress();
            MarkDocumentRespone markDocumentRespone = (MarkDocumentRespone) object;
            if (markDocumentRespone != null && markDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (markDocumentRespone.getData() != null && markDocumentRespone.getData().equals(Constants.MARKED_SUCCESS)) {
                    detailDocumentNotificationView.onMark();
                } else {
                    detailDocumentNotificationView.onError(new APIError(0, "Lỗi đánh dấu văn bản"));
                }
            } else {
                detailDocumentNotificationView.onError(new APIError(0, "Lỗi đánh dấu văn bản"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (documentNotificationView != null) {
            documentNotificationView.hideProgress();
            documentNotificationView.onError((APIError) object);
        }
        if (detailDocumentNotificationView != null) {
            detailDocumentNotificationView.hideProgress();
            detailDocumentNotificationView.onError((APIError) object);
        }
        if (documentDiagramView != null) {
            documentDiagramView.hideProgress();
            documentDiagramView.onError((APIError) object);
        }
    }

}
