package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.exchangeHandle;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendCommentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SigningDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata.HandleSyncService;

/**
 * Created by VietNH on 9/6/2017.
 */

public interface IExchangeHandleDao {
    void onGetExchangeHandles(int docId, int page, int number, ICallFinishedListener callFinishedListener);

    void onSendExchangeHandles(SendCommentRequest sendCommentRequest, ICallFinishedListener callFinishedListener);

}
