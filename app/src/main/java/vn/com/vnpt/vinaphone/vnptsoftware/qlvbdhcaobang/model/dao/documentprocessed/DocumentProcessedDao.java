package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentprocessed;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentProcessedRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckDocProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckRecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentProcessedRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RecoverDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentProcessedService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/6/2017.
 */

public class DocumentProcessedDao extends BaseDao implements IDocumentProcessedDao {
    private IDocumentProcessedService documentProcessedService;

    @Override
    public void onCountDocumentProcessedDao(DocumentProcessedRequest documentProcessedRequest, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<CountDocumentProcessedRespone> call = documentProcessedService.getCount(BaseService.createAuthenHeaders(), documentProcessedRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onRecordsDocumentProcessedDao(DocumentProcessedRequest documentProcessedRequest, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<DocumentProcessedRespone> call = documentProcessedService.getRecords(BaseService.createAuthenHeaders(), documentProcessedRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onCheckRecoverDocumentDao(String type, int id, ICallFinishedListener iCallFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<CheckRecoverDocumentRespone> call = null;
        switch (type) {
            case Constants.DOCUMENT_RECEIVE:
                call = documentProcessedService.checkReceive(BaseService.createAuthenHeaders(), id);
                break;
            case Constants.DOCUMENT_SEND:
                call = documentProcessedService.checkSend(BaseService.createAuthenHeaders(), id);
                break;
        }
        if (call != null) {
            call(call, iCallFinishedListener);
        }
    }

    @Override
    public void onRecoverDocumentDao(String type, int id, ICallFinishedListener iCallFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<RecoverDocumentRespone> call = null;
        switch (type) {
            case Constants.DOCUMENT_RECEIVE:
                call = documentProcessedService.recoverDocumentReceive(BaseService.createAuthenHeaders(), id);
                break;
            case Constants.DOCUMENT_SEND:
                call = documentProcessedService.recoverDocumentSend(BaseService.createAuthenHeaders(), id);
                break;
        }
        if (call != null) {
            call(call, iCallFinishedListener);
        }
    }

    @Override
    public void onGetDetail(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<DetailDocumentProcessedRespone> call = documentProcessedService.getDetail(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetLogs(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<LogRespone> call = documentProcessedService.getLogs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetAttachFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<AttachFileRespone> call = documentProcessedService.getAttachFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedDocs(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<RelatedDocumentRespone> call = documentProcessedService.getRelatedDocs(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetRelatedFiles(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<RelatedFileRespone> call = documentProcessedService.getRelatedFiles(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onCheckMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<CheckMarkDocumentRespone> call = documentProcessedService.checkMark(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onMarkDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<MarkDocumentRespone> call = documentProcessedService.mark(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

    @Override
    public void onGetBitmapDiagram(String insId, String defId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<ResponseBody> call = documentProcessedService.getBitmapDiagram(BaseService.createAuthenHeaders(), insId, defId);
        call(call, callFinishedListener);
    }

    @Override
    public void onCheckChangeDocDocument(int docId, ICallFinishedListener callFinishedListener) {
        documentProcessedService = BaseService.createService(IDocumentProcessedService.class);
        Call<CheckDocProcessedRespone> call = documentProcessedService.checkChangeDoc(BaseService.createAuthenHeaders(), docId);
        call(call, callFinishedListener);
    }

}
