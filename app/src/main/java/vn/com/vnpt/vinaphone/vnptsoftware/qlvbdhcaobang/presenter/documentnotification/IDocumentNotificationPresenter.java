package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentnotification;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentNotificationRequest;

/**
 * Created by VietNH on 9/12/2017.
 */

public interface IDocumentNotificationPresenter {
    void getCount(DocumentNotificationRequest documentNotificationRequest);
    void getRecords(DocumentNotificationRequest documentNotificationRequest);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void getBitmapDiagram(String insId, String defId);
    void mark(int id);
}
