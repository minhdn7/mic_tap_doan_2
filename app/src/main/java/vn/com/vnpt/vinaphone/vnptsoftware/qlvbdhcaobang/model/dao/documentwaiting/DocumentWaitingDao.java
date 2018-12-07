package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentwaiting;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckCommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckFinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentWaitingService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata.HandleSyncService;

/**
 * Created by VietNH on 9/5/2017.
 */

public class DocumentWaitingDao extends BaseDao implements IDocumentWaitingDao {
    private IDocumentWaitingService documentWaitingService;

    @Override
    public void onCountDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetCount handleGetCount) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CountDocumentWaitingRespone> call = documentWaitingService.getCount(BaseService.createAuthenHeaders(), documentWaitingRequest);
        call(call, handleGetCount);
    }

    @Override
    public void onRecordsDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetRecords handleGetRecords) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<DocumentWaitingRespone> call = documentWaitingService.getAll(BaseService.createAuthenHeaders(), documentWaitingRequest);
        call(call, handleGetRecords);
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<ResponseBody> call = documentWaitingService.getBitmapDiagram(BaseService.createAuthenHeaders(), insId, defId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<DetailDocumentWaitingRespone> call = documentWaitingService.getDetail(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<LogRespone> call = documentWaitingService.getLogs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<AttachFileRespone> call = documentWaitingService.getAttachFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<RelatedDocumentRespone> call = documentWaitingService.getRelatedDocs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<RelatedFileRespone> call = documentWaitingService.getRelatedFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onCheckMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CheckMarkDocumentRespone> call = documentWaitingService.checkMark(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<MarkDocumentRespone> call = documentWaitingService.mark(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onCheckCommentDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CheckCommentDocumentRespone> call = documentWaitingService.checkComment(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onCommentDocument(CommentDocumentRequest commentDocumentRequest, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CommentDocumentRespone> call = documentWaitingService.comment(BaseService.createAuthenHeaders(), commentDocumentRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onSigningDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<SigningRespone> call = documentWaitingService.signDoc(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onFinish(int id, ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<FinishDocumentRespone> call = documentWaitingService.finish(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onCheckFinishDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<CheckFinishDocumentRespone> call = documentWaitingService.checkFinish(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onKyVanBan(int docId, int fileid, ICallFinishedListener iCallFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<SigningRespone> call = documentWaitingService.signdocument(BaseService.createAuthenHeaders(), docId, fileid);
        call(call, iCallFinishedListener);
    }


}
