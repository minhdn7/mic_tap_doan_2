package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentexpired;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentExpiredRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/6/2017.
 */

public interface IDocumentExpiredDao {
    void onCountDocumentExpiredDao(DocumentExpiredRequest documentExpiredRequest, ICallFinishedListener iCallFinishedListener);
    void onRecordsDocumentExpiredDao(DocumentExpiredRequest documentExpiredRequest, ICallFinishedListener iCallFinishedListener);
    void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener);
    void onGetDetail(int docId, ICallFinishedListener callFinishedListener);
    void onGetLogs(int docId, ICallFinishedListener callFinishedListener);
    void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener);
    void onMarkDocument(int docId, ICallFinishedListener callFinishedListener);
}
