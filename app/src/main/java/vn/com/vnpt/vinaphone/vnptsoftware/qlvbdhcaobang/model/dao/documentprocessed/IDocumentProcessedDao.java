package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentprocessed;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentProcessedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/6/2017.
 */

public interface IDocumentProcessedDao {
    void onCountDocumentProcessedDao(DocumentProcessedRequest documentProcessedRequest, ICallFinishedListener iCallFinishedListener);
    void onRecordsDocumentProcessedDao(DocumentProcessedRequest documentProcessedRequest, ICallFinishedListener iCallFinishedListener);
    void onCheckRecoverDocumentDao(String type, int id, ICallFinishedListener iCallFinishedListener);
    void onRecoverDocumentDao(String type, int id, ICallFinishedListener iCallFinishedListener);
    void onGetDetail(int docId, ICallFinishedListener callFinishedListener);
    void onGetLogs(int docId, ICallFinishedListener callFinishedListener);
    void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener);
    void onCheckMarkDocument(int docId, ICallFinishedListener callFinishedListener);
    void onMarkDocument(int docId, ICallFinishedListener callFinishedListener);
    void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener);
    void onCheckChangeDocDocument(int docId, ICallFinishedListener callFinishedListener);
}
