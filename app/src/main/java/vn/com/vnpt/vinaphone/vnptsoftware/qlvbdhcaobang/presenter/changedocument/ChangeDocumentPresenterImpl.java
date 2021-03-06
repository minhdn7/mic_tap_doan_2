package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.changedocument;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.changedocument.ChangeDocumentDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentDirectRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentReceiveRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeDocumentSendRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeNotifyRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ChangeProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.ListProcessRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.SearchPersonRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.request.TypeChangeDocRequest;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.JobPositionInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.JobPositionRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LienThongInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LienThongRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChangeDocInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonGroupChangeDocRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.PersonListRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.UnitRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IFilterPersonView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.IGetTypeChangeDocumentView;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ISelectPersonView;

/**
 * Created by VietNH on 8/23/2017.
 */

public class ChangeDocumentPresenterImpl implements IChangeDocumentPresenter, ICallFinishedListener {
    public IChangeDocumentView changeDocumentView;
    public IGetTypeChangeDocumentView getTypeChangeDocumentView;
    public ISelectPersonView selectPersonView;
    public IFilterPersonView filterPersonView;
    public ChangeDocumentDao changeDocumentDao;

    public ChangeDocumentPresenterImpl(IChangeDocumentView changeDocumentView, IGetTypeChangeDocumentView getTypeChangeDocumentView) {
        this.getTypeChangeDocumentView = getTypeChangeDocumentView;
        this.changeDocumentView = changeDocumentView;
        this.changeDocumentDao = new ChangeDocumentDao();
    }

    public ChangeDocumentPresenterImpl(IGetTypeChangeDocumentView getTypeChangeDocumentView) {
        this.getTypeChangeDocumentView = getTypeChangeDocumentView;
        this.changeDocumentDao = new ChangeDocumentDao();
    }

    public ChangeDocumentPresenterImpl(IChangeDocumentView changeDocumentView) {
        this.changeDocumentView = changeDocumentView;
        this.changeDocumentDao = new ChangeDocumentDao();
    }

    public ChangeDocumentPresenterImpl(ISelectPersonView selectPersonView, IFilterPersonView filterPersonView) {
        this.selectPersonView = selectPersonView;
        this.filterPersonView = filterPersonView;
        this.changeDocumentDao = new ChangeDocumentDao();
    }

    @Override
    public void getTypeChangeDocument(TypeChangeDocRequest typeChangeDocumentRequest) {
        if (getTypeChangeDocumentView != null) {
            getTypeChangeDocumentView.showProgress();
            changeDocumentDao.onSendGetTypeChangeDocumentDao(typeChangeDocumentRequest, this);
        }
    }

    @Override
    public void getPersonProcess(ListProcessRequest listProcessRequest) {
        if (selectPersonView != null) {
            selectPersonView.showProgress();
            changeDocumentDao.onGetPersonProcessDao(listProcessRequest, this);
        }
    }

    @Override
    public void getPersonSend(SearchPersonRequest searchPersonRequest) {
        if (selectPersonView != null) {
            selectPersonView.showProgress();
            changeDocumentDao.onGetPersonSendDao(searchPersonRequest, this);
        }
    }

    @Override
    public void getPersonNotify() {
        if (selectPersonView != null) {
            selectPersonView.showProgress();
            changeDocumentDao.onGetPersonNotifyDao(this);
        }
    }

    @Override
    public void getJobPossitions() {
        if (filterPersonView != null) {
            changeDocumentDao.onGetJobPossitionDao(this);
        }
    }

    @Override
    public void getUnits(int type) {
        if (filterPersonView != null) {
            changeDocumentDao.onGetUnitDao(type,this);
        }
    }

    @Override
    public void changeSend(ChangeDocumentSendRequest changeDocumentSendRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeSendDao(changeDocumentSendRequest, this);
        }
    }

    @Override
    public void changeReceive(ChangeDocumentReceiveRequest changeDocumentReceiveRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeReceiveDao(changeDocumentReceiveRequest, this);
        }
    }

    @Override
    public void changeProcess(ChangeProcessRequest changeProcessRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeProcessDao(changeProcessRequest, this);
        }
    }

    @Override
    public void changeNotify(ChangeNotifyRequest changeNotifyRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeNotifyDao(changeNotifyRequest, this);
        }
    }

    @Override
    public void changeDirect(ChangeDocumentDirectRequest changeDocumentDirectRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeDirectDao(changeDocumentDirectRequest, this);
        }
    }

    @Override
    public void getLienThongXL() {
        if (selectPersonView != null) {
            //selectPersonView.showProgress();
            changeDocumentDao.onGetLienTHongXLDao(this);
        }
    }

    @Override
    public void getLienThongBH(int docId) {
        if (selectPersonView != null) {
            //selectPersonView.showProgress();
            changeDocumentDao.onGetLienThongBHDao(docId, this);
        }
    }

    @Override
    public void changeDocNotify(ChangeDocumentNotifyRequest changeDocumentNotifyRequest) {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onChangeDocNotifyDao(changeDocumentNotifyRequest, this);
        }
    }

    @Override
    public void getPersonGroupCN() {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onGetGroupPersonCNDao(this);
        }
    }

    @Override
    public void getPersonGroupDV() {
        if (changeDocumentView != null) {
            changeDocumentView.showProgress();
            changeDocumentDao.onGetGroupPersonDVDao(this);
        }
    }

    @Override
    public void getPersonSendProcess(SearchPersonRequest searchPersonRequest) {
        if (selectPersonView != null) {
            selectPersonView.showProgress();
            changeDocumentDao.onGetPersonSendProcessDao(searchPersonRequest, this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof TypeChangeDocumentRespone) {
            getTypeChangeDocumentView.hideProgress();
            TypeChangeDocumentRespone typeChangeDocumentRespone = (TypeChangeDocumentRespone) object;
            if (typeChangeDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                getTypeChangeDocumentView.onSuccessTypeChange(ConvertUtils.fromJSONList(typeChangeDocumentRespone.getData(), TypeChangeDocument.class));
            } else {
                getTypeChangeDocumentView.onErrorTypeChange(new APIError(0, "getTypeChange"));
            }
        }
        if (object instanceof PersonListRespone) {
            selectPersonView.hideProgress();
            PersonListRespone personListRespone = (PersonListRespone) object;
            if (personListRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                selectPersonView.onSuccess(personListRespone.getData());
            } else {
                selectPersonView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof JobPositionRespone) {
            selectPersonView.hideProgress();
            JobPositionRespone jobPositionRespone = (JobPositionRespone) object;
            if (jobPositionRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                filterPersonView.onSuccessJobPossitions(ConvertUtils.fromJSONList(jobPositionRespone.getData(), JobPositionInfo.class));
            } else {
                filterPersonView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof UnitRespone) {
            selectPersonView.hideProgress();
            UnitRespone unitRespone = (UnitRespone) object;
            if (unitRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                filterPersonView.onSuccessUnits(ConvertUtils.fromJSONList(unitRespone.getData(), UnitInfo.class));
            } else {
                filterPersonView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof ChangeDocumentRespone) {
            changeDocumentView.hideProgress();
            ChangeDocumentRespone changeDocumentRespone = (ChangeDocumentRespone) object;
            if (changeDocumentRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)
                    && changeDocumentRespone.getData().equals(Constants.CHANGE_DOC_SUCCESS)) {
                changeDocumentView.onSuccessChangeDoc();
            } else {
                changeDocumentView.onError(new APIError(0, "changeFail"));
            }
        }
        if (object instanceof LienThongRespone) {
            //selectPersonView.hideProgress();
            LienThongRespone lienThongRespone = (LienThongRespone) object;
            if (lienThongRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                selectPersonView.onSuccessLienThong(ConvertUtils.fromJSONList(lienThongRespone.getData(), LienThongInfo.class));
            } else {
                selectPersonView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
        if (object instanceof PersonGroupChangeDocRespone) {
            changeDocumentView.hideProgress();
            PersonGroupChangeDocRespone personGroupChangeDocRespone = (PersonGroupChangeDocRespone) object;
            if (personGroupChangeDocRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                changeDocumentView.onSuccessGroupPerson(ConvertUtils.fromJSONList(personGroupChangeDocRespone.getData(), PersonGroupChangeDocInfo.class));
            } else {
                changeDocumentView.onError(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
            }
        }
    }

    @Override
    public void onCallError(Object object) {
        if (changeDocumentView != null) {
            changeDocumentView.hideProgress();
            if (object instanceof APIError) {
                APIError apiError = (APIError) object;
                if (apiError.getCode() == Constants.RESPONE_INVALID) {
                    changeDocumentView.onError(apiError);
                } else {
                    changeDocumentView.onError(new APIError(0, "changeFail"));
                }
            } else {
                changeDocumentView.onError(new APIError(0, "changeFail"));
            }
        }
        if (getTypeChangeDocumentView != null) {
            getTypeChangeDocumentView.hideProgress();
            getTypeChangeDocumentView.onErrorTypeChange(new APIError(0, "getTypeChange"));
        }
        if (selectPersonView != null) {
            selectPersonView.hideProgress();
            selectPersonView.onError((APIError) object);
        }
        if (filterPersonView != null) {
            filterPersonView.onError((APIError) object);
        }
    }

}
