package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentexpired;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentExpiredRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentExpiredRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentExpiredService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentMarkService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/6/2017.
 */

public class DocumentExpiredDao extends BaseDao implements IDocumentExpiredDao {
    private IDocumentExpiredService documentExpiredService;

    @Override
    public void onCountDocumentExpiredDao(DocumentExpiredRequest documentExpiredRequest, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<CountDocumentExpiredRespone> call = documentExpiredService.getCount(BaseService.createAuthenHeaders(), documentExpiredRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onRecordsDocumentExpiredDao(DocumentExpiredRequest documentExpiredRequest, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<DocumentExpiredRespone> call = documentExpiredService.getRecords(BaseService.createAuthenHeaders(), documentExpiredRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<ResponseBody> call = documentExpiredService.getBitmapDiagram(BaseService.createAuthenHeaders(), insId, defId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<DetailDocumentExpiredRespone> call = documentExpiredService.getDetail(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<LogRespone> call = documentExpiredService.getLogs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<AttachFileRespone> call = documentExpiredService.getAttachFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<RelatedDocumentRespone> call = documentExpiredService.getRelatedDocs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<RelatedFileRespone> call = documentExpiredService.getRelatedFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentExpiredService = BaseService.createService(IDocumentExpiredService.class);
        Call<MarkDocumentRespone> call = documentExpiredService.mark(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

}
