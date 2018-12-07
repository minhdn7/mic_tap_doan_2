package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentnotification;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentNotificationRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/6/2017.
 */

public interface IDocumentNotificationDao {
    void onCountDocumentNotificationDao(DocumentNotificationRequest documentNotificationRequest, ICallFinishedListener iCallFinishedListener);
    void onRecordsDocumentNotificationDao(DocumentNotificationRequest documentNotificationRequest, ICallFinishedListener iCallFinishedListener);
    void onGetDetail(int docId, ICallFinishedListener callFinishedListener);
    void onGetLogs(int docId, ICallFinishedListener callFinishedListener);
    void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener);
    void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener);
    void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener);
    void onMarkDocument(int docId, ICallFinishedListener callFinishedListener);
}
