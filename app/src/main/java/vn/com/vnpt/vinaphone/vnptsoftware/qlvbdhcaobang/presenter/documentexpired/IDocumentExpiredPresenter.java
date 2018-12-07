package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentexpired;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentExpiredRequest;

/**
 * Created by VietNH on 9/12/2017.
 */

public interface IDocumentExpiredPresenter {
    void getCount(DocumentExpiredRequest documentExpiredRequest);
    void getRecords(DocumentExpiredRequest documentExpiredRequest);
    void getBitmapDiagram(String insId, String defId);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void mark(int id);
}
