package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.chidao;

import okhttp3.MultipartBody;
import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoGuiRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoNhanRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonInGroupChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.PersonReceiveChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ReplyChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SavePersonChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SendChiDaoRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChiDaoFlowRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DeleteChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.DetailChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.FileChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonInGroupChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonReceiveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ReplyChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SaveChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SavePersonChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.SendChiDaoRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UploadResponse;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IChiDaoService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ChiDaoDao extends BaseDao implements IChiDaoDao {
    private IChiDaoService chiDaoService;

    @Override
    public void onGetChiDaoNhanDao(ChiDaoNhanRequest chiDaoNhanRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<ChiDaoRespone> call = chiDaoService.getChiDaoNhan(BaseService.createAuthenHeaders(), chiDaoNhanRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetChiDaoGuiDao(ChiDaoGuiRequest chiDaoGuiRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<ChiDaoRespone> call = chiDaoService.getChiDaoGui(BaseService.createAuthenHeaders(), chiDaoGuiRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetWebViewFileDao(String ulrFile, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<GetViewFileObj> call = chiDaoService.getWebViewFile(BaseService.createAuthenHeaders(), ulrFile);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onUploadFileDao(MultipartBody.Part[] files, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<UploadResponse> call = chiDaoService.uploadFiles(BaseService.createMultipartHeaders(), files);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onSaveChiDao(ChiDaoRequest chiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<SaveChiDaoRespone> call = null;
        if (chiDaoRequest.getId() != null && !chiDaoRequest.getId().trim().equals("")) {
            call = chiDaoService.editChiDao(BaseService.createAuthenHeaders(), chiDaoRequest);
        } else {
            call = chiDaoService.createChiDao(BaseService.createAuthenHeaders(), chiDaoRequest);
        }
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetPersonChiDao(PersonChiDaoRequest personChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonChiDaoRespone> call = chiDaoService.getPersons(BaseService.createAuthenHeaders(), personChiDaoRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onSavePersonChiDao(SavePersonChiDaoRequest savePersonChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<SavePersonChiDaoRespone> call = chiDaoService.savePersons(BaseService.createAuthenHeaders(), savePersonChiDaoRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetPersonReceiveChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonReceiveChiDaoRespone> call = chiDaoService.getPersonsReceive(BaseService.createAuthenHeaders(), personReceiveChiDaoRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetPersonGroupChiDao(ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonGroupChiDaoRespone> call = chiDaoService.getPersonsGroup(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onSendChiDao(SendChiDaoRequest sendChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<SendChiDaoRespone> call = chiDaoService.send(BaseService.createAuthenHeaders(), sendChiDaoRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetFlow(String id, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<ChiDaoFlowRespone> call = chiDaoService.getFlows(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetFile(String id, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<FileChiDaoRespone> call = chiDaoService.getFiles(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onDelete(String id, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<DeleteChiDaoRespone> call = chiDaoService.delete(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetDetail(String id, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<DetailChiDaoRespone> call = chiDaoService.getDetail(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onPersonInGroupChiDao(PersonInGroupChiDaoRequest personInGroupChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonInGroupChiDaoRespone> call = chiDaoService.getPersonInGroup(BaseService.createAuthenHeaders(), personInGroupChiDaoRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onPersonReplyChiDao(ReplyChiDaoRequest replyChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<ReplyChiDaoRespone> call = chiDaoService.getPersonReply(BaseService.createAuthenHeaders(), replyChiDaoRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetPersonReceivedChiDao(PersonReceiveChiDaoRequest personReceiveChiDaoRequest, ICallFinishedListener iCallFinishedListener) {
        chiDaoService = BaseService.createService(IChiDaoService.class);
        Call<PersonReceiveChiDaoRespone> call = chiDaoService.getPersonsReceived(BaseService.createAuthenHeaders(), personReceiveChiDaoRequest);
        call(call, iCallFinishedListener);
    }

}
