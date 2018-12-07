package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.exchangeHandles;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.CommentDocumentRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DocumentWaitingRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendCommentRequest;

/**
 * Created by VietNH on 9/6/2017.
 */

public interface IExchangeHandlesPresenter {
    void getExchangeHandles(int id, int page, int number);

    void sendComment(SendCommentRequest sendCommentRequest);
}
