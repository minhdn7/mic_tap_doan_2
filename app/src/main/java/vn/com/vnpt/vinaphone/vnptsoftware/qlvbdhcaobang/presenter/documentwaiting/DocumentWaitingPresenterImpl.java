package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentwaiting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentwaiting.DocumentWaitingDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckFinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitLogInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata.HandleSyncService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDetailDocumentWaitingView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDocumentDiagramView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IDocumentWaitingView;

/**
 * Created by VietNH on 9/12/2017.
 */

public class DocumentWaitingPresenterImpl implements IDocumentWaitingPresenter, ICallFinishedListener, HandleSyncService.HandleGetRecords {
    public IDocumentWaitingView documentWaitingView;
    public IDetailDocumentWaitingView detailDocumentWaitingView;
    public IDocumentDiagramView documentDiagramView;
    public DocumentWaitingDao documentWaitingDao;

    public DocumentWaitingPresenterImpl(IDocumentWaitingView documentWaitingView) {
        this.documentWaitingView = documentWaitingView;
        this.documentWaitingDao = new DocumentWaitingDao();
    }

    public DocumentWaitingPresenterImpl(IDetailDocumentWaitingView detailDocumentWaitingView) {
        this.detailDocumentWaitingView = detailDocumentWaitingView;
        this.documentWaitingDao = new DocumentWaitingDao();
    }

    public DocumentWaitingPresenterImpl(IDocumentDiagramView documentDiagramView) {
        this.documentDiagramView = documentDiagramView;
        this.documentWaitingDao = new DocumentWaitingDao();
    }

    @Override
    public void getRecords(DocumentWaitingRequest documentWaitingRequest) {
        if (documentWaitingView != null) {
            documentWaitingView.showProgress();
            documentWaitingDao.onRecordsDocumentWaitingDao(documentWaitingRequest, this);
        }
    }

    @Override
    public void getBitmapDiagram(String insId, String defId) {
        if (documentDiagramView != null) {
            documentDiagramView.showProgress();
            documentWaitingDao.onGetBitmapDiagram(insId, defId, this);
        }
    }

    @Override
    public void getDetail(int id) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onGetDetail(id, this);
        }
    }

    @Override
    public void getLogs(int id) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onGetLogs(id, this);
        }
    }

    @Override
    public void getAttachFiles(int id) {
        if (detailDocumentWaitingView != null) {
            documentWaitingDao.onGetAttachFiles(id, this);
        }
    }

    @Override
    public void getRelatedDocs(int id) {
        if (detailDocumentWaitingView != null) {
            documentWaitingDao.onGetRelatedDocs(id, this);
        }
    }

    @Override
    public void getRelatedFiles(int id) {
        if (detailDocumentWaitingView != null) {
            documentWaitingDao.onGetRelatedFiles(id, this);
        }
    }

    @Override
    public void comment(CommentDocumentRequest commentDocumentRequest) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onCommentDocument(commentDocumentRequest, this);
        }
    }

    @Override
    public void mark(int id) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onMarkDocument(id, this);
        }
    }

    @Override
    public void finish(int id) {
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.showProgress();
            documentWaitingDao.onFinish(id, this);
        }
    }

    @Override
    public void checkFinish(int id) {
        if (detailDocumentWaitingView != null) {
            documentWaitingDao.onCheckFinishDocument(id, this);
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
        if (object instanceof DetailDocumentWaitingRespone) {
            detailDocumentWaitingView.hideProgress();
            DetailDocumentWaitingRespone detailDocumentWaitingRespone = (DetailDocumentWaitingRespone) object;
            if (detailDocumentWaitingRespone != null && detailDocumentWaitingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentWaitingView.onSuccess(detailDocumentWaitingRespone.getData());
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof AttachFileRespone) {
            detailDocumentWaitingView.hideProgress();
            AttachFileRespone attachFileRespone = (AttachFileRespone) object;
            if (attachFileRespone != null && attachFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentWaitingView.onSuccess(ConvertUtils.fromJSONList(attachFileRespone.getData(), AttachFileInfo.class));
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof RelatedDocumentRespone) {
            detailDocumentWaitingView.hideProgress();
            RelatedDocumentRespone relatedDocumentRespone = (RelatedDocumentRespone) object;
            if (relatedDocumentRespone != null && relatedDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentWaitingView.onSuccess(ConvertUtils.fromJSONList(relatedDocumentRespone.getData(), RelatedDocumentInfo.class));
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof LogRespone) {
            detailDocumentWaitingView.hideProgress();
            LogRespone logRespone = (LogRespone) object;
            if (logRespone != null && logRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentWaitingView.onSuccess(ConvertUtils.fromJSONList(logRespone.getData(), UnitLogInfo.class));
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof RelatedFileRespone) {
            detailDocumentWaitingView.hideProgress();
            RelatedFileRespone relatedFileRespone = (RelatedFileRespone) object;
            if (relatedFileRespone != null && relatedFileRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                detailDocumentWaitingView.onSuccess(ConvertUtils.fromJSONList(relatedFileRespone.getData(), RelatedFileInfo.class));
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof CommentDocumentRespone) {
            detailDocumentWaitingView.hideProgress();
            CommentDocumentRespone commentDocumentRespone = (CommentDocumentRespone) object;
            if (commentDocumentRespone != null && commentDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (commentDocumentRespone.getData() != null && commentDocumentRespone.getData().startsWith(Constants.COMMENTED)) {
                    detailDocumentWaitingView.onComment();
                } else {
                    detailDocumentWaitingView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
                }
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof MarkDocumentRespone) {
            detailDocumentWaitingView.hideProgress();
            MarkDocumentRespone markDocumentRespone = (MarkDocumentRespone) object;
            if (markDocumentRespone != null && markDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (markDocumentRespone.getData() != null && markDocumentRespone.getData().equals(Constants.MARKED_SUCCESS)) {
                    detailDocumentWaitingView.onMark();
                } else {
                    detailDocumentWaitingView.onError(new APIError(0, "Lỗi đánh dấu văn bản"));
                }
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Lỗi đánh dấu văn bản"));
            }
        }
        if (object instanceof FinishDocumentRespone) {
            detailDocumentWaitingView.hideProgress();
            FinishDocumentRespone finishDocumentRespone = (FinishDocumentRespone) object;
            if (finishDocumentRespone != null && finishDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (finishDocumentRespone.getData() != null) {
                    detailDocumentWaitingView.onFinish();
                } else {
                    detailDocumentWaitingView.onError(new APIError(0, "Lỗi kết thúc văn bản"));
                }
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Lỗi kết thúc văn bản"));
            }
        }
        if (object instanceof CheckFinishDocumentRespone) {
            detailDocumentWaitingView.hideProgress();
            CheckFinishDocumentRespone checkFinishDocumentRespone = (CheckFinishDocumentRespone) object;
            if (checkFinishDocumentRespone != null && checkFinishDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                if (checkFinishDocumentRespone.getData() != null && checkFinishDocumentRespone.getData().equals(Constants.IS_FINISH)) {
                    detailDocumentWaitingView.onCheckFinish(true);
                } else {
                    detailDocumentWaitingView.onCheckFinish(false);
                }
            } else {
                detailDocumentWaitingView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (documentWaitingView != null) {
            documentWaitingView.hideProgress();
            documentWaitingView.onError((APIError) object);
        }
        if (detailDocumentWaitingView != null) {
            detailDocumentWaitingView.hideProgress();
            detailDocumentWaitingView.onError((APIError) object);
        }
        if (documentDiagramView != null) {
            documentDiagramView.hideProgress();
            documentDiagramView.onError((APIError) object);
        }
    }

    @Override
    public void onSuccessGetRecords(Object object) {
        documentWaitingView.hideProgress();
        DocumentWaitingRespone documentWaitingRespone = (DocumentWaitingRespone) object;
        if (documentWaitingRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            documentWaitingView.onSuccessRecords(ConvertUtils.fromJSONList(documentWaitingRespone.getData(), DocumentWaitingInfo.class));
        } else {
            documentWaitingView.onError(new APIError(0, ""));
        }
    }

    @Override
    public void onErrorGetRecords(Object object) {
        if (documentWaitingView != null) {
            documentWaitingView.hideProgress();
            documentWaitingView.onError((APIError) object);
        }
    }
}
