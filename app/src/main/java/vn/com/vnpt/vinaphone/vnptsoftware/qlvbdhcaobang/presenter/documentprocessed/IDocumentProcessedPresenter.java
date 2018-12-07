package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentprocessed;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentProcessedRequest;

/**
 * Created by VietNH on 9/12/2017.
 */

public interface IDocumentProcessedPresenter {
    void getCount(DocumentProcessedRequest documentProcessedRequest);
    void getRecords(DocumentProcessedRequest documentProcessedRequest);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void checkRecover(String type, int id);
    void recover(String type, int id);
    void getBitmapDiagram(String insId, String defId);
    void mark(int id);
    void checkChange(int id);
}
