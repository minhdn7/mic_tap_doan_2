package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.documentsearch;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentAdvanceSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FieldDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PriorityDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentSearchService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 9/5/2017.
 */

public class DocumentSearchDao extends BaseDao implements IDocumentSearchDao {
    private IDocumentSearchService documentSearchService;

    @Override
    public void onGetTypes(ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<TypeDocumentRespone> call = documentSearchService.getTypes(BaseService.createAuthenHeaders());
        call(call, callFinishedListener);
    }

    @Override
    public void onGetFields(ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<FieldDocumentRespone> call = documentSearchService.getFields(BaseService.createAuthenHeaders());
        call(call, callFinishedListener);
    }

    @Override
    public void onGetPrioritys(ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<PriorityDocumentRespone> call = documentSearchService.getPrioritys(BaseService.createAuthenHeaders());
        call(call, callFinishedListener);
    }

    @Override
    public void onCountDocumentSearchDao(DocumentSearchRequest documentSearchRequest, ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<CountDocumentSearchRespone> call = documentSearchService.getCount(BaseService.createAuthenHeaders(), documentSearchRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onRecordsDocumentSearchDao(DocumentSearchRequest documentSearchRequest, ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<DocumentSearchRespone> call = documentSearchService.getRecords(BaseService.createAuthenHeaders(), documentSearchRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onCountDocumentAdvanceSearchDao(DocumentAdvanceSearchRequest documentAdvanceSearchRequest, ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<CountDocumentAdvanceSearchRespone> call = documentSearchService.getCountAdvance(BaseService.createAuthenHeaders(), documentAdvanceSearchRequest);
        call(call, callFinishedListener);
    }

    @Override
    public void onRecordsDocumentAdvanceSearchDao(DocumentAdvanceSearchRequest documentAdvanceSearchRequest, ICallFinishedListener callFinishedListener) {
        documentSearchService = BaseService.createService(IDocumentSearchService.class);
        Call<DocumentAdvanceSearchRespone> call = documentSearchService.getRecordsAdvance(BaseService.createAuthenHeaders(), documentAdvanceSearchRequest);
        call(call, callFinishedListener);
    }

}
