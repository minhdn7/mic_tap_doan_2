package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.chidao;

import okhttp3.MultipartBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoGuiRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.DownloadChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReplyChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendChiDaoRequest;

/**
 * Created by VietNH on 8/29/2017.
 */

public interface IChiDaoPresenter {
    void getChiDaoNhan(ChiDaoNhanRequest chiDaoNhanRequest);
    void getChiDaoGui(ChiDaoGuiRequest chiDaoGuiRequest);
    void getWebViewFile(String urlPath);
    void uploadFiles(MultipartBody.Part[] files);
    void saveChiDao(ChiDaoRequest chiDaoRequest);
    void getPersonChiDao(PersonChiDaoRequest personChiDaoRequest);
    void savePersonChiDao(SavePersonChiDaoRequest savePersonChiDaoRequest);
    void getPersonReceiveChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest);
    void getPersonGroupChiDao();
    void send(SendChiDaoRequest sendChiDaoRequest);
    void getFlowChiDao(String id);
    void getFileChiDao(String id);
    void getDetailChiDao(String id);
    void downloadFileChiDao(DownloadChiDaoRequest downloadChiDaoRequest);
    void getPersonIngroup(PersonInGroupChiDaoRequest personInGroupChiDaoRequest);
    void getPersonReply(ReplyChiDaoRequest replyChiDaoRequest);
    void getPersonReceivedChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest);
}
