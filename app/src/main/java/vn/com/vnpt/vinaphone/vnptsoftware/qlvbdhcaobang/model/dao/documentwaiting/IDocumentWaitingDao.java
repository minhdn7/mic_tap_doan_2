package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentwaiting;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata.HandleSyncService;

/**
 * Created by VietNH on 9/6/2017.
 */

public interface IDocumentWaitingDao {
    void onCountDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetCount handleGetCount);
    void onRecordsDocumentWaitingDao(DocumentWaitingRequest documentWaitingRequest, HandleSyncService.HandleGetRecords handleGetRecords);
    void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener);
    void onGetDetail(int docId, ICallFinishedListener callFinishedListener);
    void onGetLogs(int docId, ICallFinishedListener callFinishedListener);
    void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener);
    void onCheckMarkDocument(int docId, ICallFinishedListener callFinishedListener);
    void onMarkDocument(int docId, ICallFinishedListener callFinishedListener);
    void onCheckCommentDocument(int docId, ICallFinishedListener callFinishedListener);
    void onCommentDocument(CommentDocumentRequest commentDocumentRequest, ICallFinishedListener callFinishedListener);
    void onSigningDocument(int docId, ICallFinishedListener callFinishedListener);
    void onFinish(int id, ICallFinishedListener iCallFinishedListener);
    void onCheckFinishDocument(int docId, ICallFinishedListener callFinishedListener);
    void onKyVanBan(int docId, int fileid, ICallFinishedListener iCallFinishedListener);
}
