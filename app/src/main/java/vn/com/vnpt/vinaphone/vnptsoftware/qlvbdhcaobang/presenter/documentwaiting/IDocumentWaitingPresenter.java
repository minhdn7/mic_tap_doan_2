package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentwaiting;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;

/**
 * Created by VietNH on 9/6/2017.
 */

public interface IDocumentWaitingPresenter {
    void getRecords(DocumentWaitingRequest documentWaitingRequest);
    void getBitmapDiagram(String insId, String defId);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void comment(CommentDocumentRequest commentDocumentRequest);
    void mark(int id);
    void finish(int id);
    void checkFinish(int id);

}
