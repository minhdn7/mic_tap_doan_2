package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changedocument;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocRequest;

/**
 * Created by VietNH on 8/23/2017.
 */

public interface IChangeDocumentPresenter {
    void getTypeChangeDocument(TypeChangeDocRequest typeChangeDocumentRequest);
    void getPersonProcess(ListProcessRequest listProcessRequest);
    void getPersonSend(SearchPersonRequest searchPersonRequest);
    void getPersonNotify();
    void getJobPossitions();
    void getUnits(int type);
    void changeSend(ChangeDocumentSendRequest changeDocumentSendRequest);
    void changeReceive(ChangeDocumentReceiveRequest changeDocumentReceiveRequest);
    void changeProcess(ChangeProcessRequest changeProcessRequest);
    void changeNotify(ChangeNotifyRequest changeNotifyRequest);
    void changeDirect(ChangeDocumentDirectRequest changeDocumentDirectRequest);
    void getLienThongXL();
    void getLienThongBH(int docId);
    void changeDocNotify(ChangeDocumentNotifyRequest changeDocumentNotifyRequest);
    void getPersonGroupCN();
    void getPersonGroupDV();
    void getPersonSendProcess(SearchPersonRequest searchPersonRequest);
}
