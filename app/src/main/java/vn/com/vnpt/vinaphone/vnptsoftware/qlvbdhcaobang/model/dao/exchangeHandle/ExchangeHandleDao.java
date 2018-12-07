package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.exchangeHandle;

import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.Contact;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendCommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckCommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckFinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CheckMarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CommentDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.CountDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailDocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DocumentWaitingRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ExchangeHandlesRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FinishDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LogRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.MarkDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SendCommentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SigningRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IDocumentWaitingService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata.HandleSyncService;

import static vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.HeaderConfiguration.AUTHORIZATION_HEADER;

/**
 * Created by VietNH on 9/5/2017.
 */

public class ExchangeHandleDao extends BaseDao implements IExchangeHandleDao {
    private IDocumentWaitingService documentWaitingService;

    @Override
    public void onGetExchangeHandles(int docId, int page, int number, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<ExchangeHandlesRespone> call = documentWaitingService.getlistcomment(BaseService.createAuthenHeaders(), docId, page, number);
        call(call, callFinishedListener);
    }

    @Override
    public void onSendExchangeHandles(SendCommentRequest sendCommentRequest, ICallFinishedListener callFinishedListener) {
        documentWaitingService = BaseService.createService(IDocumentWaitingService.class);
        Call<SendCommentRespone> call = documentWaitingService.sendlistcomment(BaseService.createAuthenHeaders(),sendCommentRequest);
        call(call, callFinishedListener);
    }
}
