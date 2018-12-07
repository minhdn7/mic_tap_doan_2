package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentnotification;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentNotificationRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentNotificationRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentNotificationService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentProcessedService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/6/2017.
 */

public class DocumentNotificationDao extends BaseDao implements IDocumentNotificationDao {
    private IDocumentNotificationService documentNotificationService;

    @Override
    public void onCountDocumentNotificationDao(DocumentNotificationRequest documentNotificationRequest, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<CountDocumentNotificationRespone> call = documentNotificationService.getCount(BaseService.createAuthenHeaders(), documentNotificationRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onRecordsDocumentNotificationDao(DocumentNotificationRequest documentNotificationRequest, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<DocumentNotificationRespone> call = documentNotificationService.getRecords(BaseService.createAuthenHeaders(), documentNotificationRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<DetailDocumentNotificationRespone> call = documentNotificationService.getDetail(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<LogRespone> call = documentNotificationService.getLogs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<AttachFileRespone> call = documentNotificationService.getAttachFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<RelatedDocumentRespone> call = documentNotificationService.getRelatedDocs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<RelatedFileRespone> call = documentNotificationService.getRelatedFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<ResponseBody> call = documentNotificationService.getBitmapDiagram(BaseService.createAuthenHeaders(), insId, defId);
        call(call, callFinishedListener);
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentNotificationService = BaseService.createService(IDocumentNotificationService.class);
        Call<MarkDocumentRespone> call = documentNotificationService.mark(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

}
