package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.changedocument;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.CountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.NumberCountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.JobPositionRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LienThongRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChangeDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonListRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IChangeDocumentService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/23/2017.
 */

public class ChangeDocumentDao extends BaseDao implements IChangeDocumentDao {
    private IChangeDocumentService changeDocumentService;

    @Override
    public void onSendGetTypeChangeDocumentDao(TypeChangeDocRequest typeChangeDocumentRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<TypeChangeDocumentRespone> call = changeDocumentService.getTypeChange(BaseService.createAuthenHeaders(), typeChangeDocumentRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetPersonProcessDao(ListProcessRequest listProcessRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonListRespone> call = changeDocumentService.getPersonProcess(BaseService.createAuthenHeaders(), listProcessRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetPersonSendDao(SearchPersonRequest searchPersonRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonListRespone> call = changeDocumentService.getPersonSend(BaseService.createAuthenHeaders(), searchPersonRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetPersonNotifyDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonListRespone> call = changeDocumentService.getPersonNotify(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onChangeSendDao(ChangeDocumentSendRequest changeDocumentSendRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeSend(BaseService.createAuthenHeaders(), changeDocumentSendRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onChangeReceiveDao(ChangeDocumentReceiveRequest changeDocumentReceiveRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeReceive(BaseService.createAuthenHeaders(), changeDocumentReceiveRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onChangeProcessDao(ChangeProcessRequest changeProcessRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeProcess(BaseService.createAuthenHeaders(), changeProcessRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onChangeNotifyDao(ChangeNotifyRequest changeNotifyRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeNotify(BaseService.createAuthenHeaders(), changeNotifyRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onChangeDirectDao(ChangeDocumentDirectRequest changeDocumentDirectRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeDirect(BaseService.createAuthenHeaders(), changeDocumentDirectRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetJobPossitionDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<JobPositionRespone> call = changeDocumentService.getJobPossitions(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetUnitDao(int type, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        if (type == 0) {
            Call<UnitRespone> call = changeDocumentService.getUnits(BaseService.createAuthenHeaders());
            call(call, iCallFinishedListener);
        }
        if (type == 1) {
            Call<UnitRespone> call = changeDocumentService.getUnitClerks(BaseService.createAuthenHeaders());
            call(call, iCallFinishedListener);
        }
    }

    @Override
    public void onGetLienThongBHDao(int id, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<LienThongRespone> call = changeDocumentService.getLienThongBH(BaseService.createAuthenHeaders(), id);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetLienTHongXLDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<LienThongRespone> call = changeDocumentService.getLienThongXL(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onChangeDocNotifyDao(ChangeDocumentNotifyRequest changeDocumentNotifyRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<ChangeDocumentRespone> call = changeDocumentService.changeNotify(BaseService.createAuthenHeaders(), changeDocumentNotifyRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetGroupPersonCNDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonGroupChangeDocRespone> call = changeDocumentService.getGroupPersonCN(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetGroupPersonDVDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonGroupChangeDocRespone> call = changeDocumentService.getGroupPersonDV(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetPersonSendProcessDao(SearchPersonRequest searchPersonRequest, ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<PersonListRespone> call = changeDocumentService.getPersonSendProcess(BaseService.createAuthenHeaders(), searchPersonRequest);
        call(call, iCallFinishedListener);
    }

    @Override
    public void onGetCountDocumentDao(ICallFinishedListener iCallFinishedListener) {
        changeDocumentService = BaseService.createService(IChangeDocumentService.class);
        Call<NumberCountDocument> call = changeDocumentService.getCountDocument(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }

}
