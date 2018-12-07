package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentmark;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentMarkRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentMarkRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentMarkService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentNotificationService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/6/2017.
 */

public class DocumentMarkDao extends BaseDao implements IDocumentMarkDao {
    private IDocumentMarkService documentMarkService;

    @Override
    public void onCountDocumentMarkDao(DocumentMarkRequest documentMarkRequest, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<CountDocumentMarkRespone> call = documentMarkService.getCount(BaseService.createAuthenHeaders(), documentMarkRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onRecordsDocumentMarkDao(DocumentMarkRequest documentMarkRequest, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<DocumentMarkRespone> call = documentMarkService.getRecords(BaseService.createAuthenHeaders(), documentMarkRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<DetailDocumentMarkRespone> call = documentMarkService.getDetail(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<LogRespone> call = documentMarkService.getLogs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<AttachFileRespone> call = documentMarkService.getAttachFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<RelatedDocumentRespone> call = documentMarkService.getRelatedDocs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<RelatedFileRespone> call = documentMarkService.getRelatedFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<ResponseBody> call = documentMarkService.getBitmapDiagram(BaseService.createAuthenHeaders(), insId, defId);
        call(call, callFinishedListener);
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentMarkService = BaseService.createService(IDocumentMarkService.class);
        Call<MarkDocumentRespone> call = documentMarkService.mark(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

}
