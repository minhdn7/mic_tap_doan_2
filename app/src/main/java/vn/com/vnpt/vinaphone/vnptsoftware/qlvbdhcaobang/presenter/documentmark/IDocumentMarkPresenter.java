package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.documentmark;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentMarkRequest;

/**
 * Created by VietNH on 9/12/2017.
 */

public interface IDocumentMarkPresenter {
    void getCount(DocumentMarkRequest documentMarkRequest);
    void getRecords(DocumentMarkRequest documentMarkRequest);
    void getDetail(int id);
    void getLogs(int id);
    void getAttachFiles(int id);
    void getRelatedDocs(int id);
    void getRelatedFiles(int id);
    void getBitmapDiagram(String insId, String defId);
}
