package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentmark;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentMarkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/6/2017.
 */

public interface IDocumentMarkDao {
    void onCountDocumentMarkDao(DocumentMarkRequest documentMarkRequest, ICallFinishedListener iCallFinishedListener);
    void onRecordsDocumentMarkDao(DocumentMarkRequest documentMarkRequest, ICallFinishedListener iCallFinishedListener);
    void onGetDetail(int docId, ICallFinishedListener callFinishedListener);
    void onGetLogs(int docId, ICallFinishedListener callFinishedListener);
    void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener);
    void onMarkDocument(int docId, ICallFinishedListener callFinishedListener);
}
