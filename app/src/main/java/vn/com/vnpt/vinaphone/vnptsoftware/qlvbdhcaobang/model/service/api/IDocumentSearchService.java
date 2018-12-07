package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentAdvanceSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentSearchRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentAdvanceSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentSearchRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FieldDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PriorityDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.url.ServiceUrl;

/**
 * Created by VietNH on 4/10/2017.
 */

public interface IDocumentSearchService {
    @GET(ServiceUrl.GET_LIST_TYPE_DOC_URL)
    Call<TypeDocumentRespone> getTypes(@HeaderMap Map<String, String> headers);
    @GET(ServiceUrl.GET_LIST_FIELD_DOC_URL)
    Call<FieldDocumentRespone> getFields(@HeaderMap Map<String, String> headers);
    @GET(ServiceUrl.GET_LIST_PRIORITY_DOC_URL)
    Call<PriorityDocumentRespone> getPrioritys(@HeaderMap Map<String, String> headers);
    @POST(ServiceUrl.GET_COUNT_DOC_SEARCH_URL)
    Call<CountDocumentSearchRespone> getCount(@HeaderMap Map<String, String> headers, @Body DocumentSearchRequest documentSearchRequest);
    @POST(ServiceUrl.GET_DOC_SEARCH_URL)
    Call<DocumentSearchRespone> getRecords(@HeaderMap Map<String, String> headers, @Body DocumentSearchRequest documentSearchRequest);
    @POST(ServiceUrl.GET_COUNT_DOC_ADVANCE_SEARCH_URL)
    Call<CountDocumentAdvanceSearchRespone> getCountAdvance(@HeaderMap Map<String, String> headers, @Body DocumentAdvanceSearchRequest documentAdvanceSearchRequest);
    @POST(ServiceUrl.GET_DOC_ADVANCE_SEARCH_URL)
    Call<DocumentAdvanceSearchRespone> getRecordsAdvance(@HeaderMap Map<String, String> headers, @Body DocumentAdvanceSearchRequest documentAdvanceSearchRequest);
}