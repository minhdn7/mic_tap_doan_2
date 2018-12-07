package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.countdocument;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.changedocument.ChangeDocumentDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.CountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.model.NumberCountDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocument;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.TypeChangeDocumentRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent.ICountDocumentView;

public class CountDocumentPresenterImpl implements ICountDocumentPresenter, ICallFinishedListener {
    public ICountDocumentView countDocumentView;
    public ChangeDocumentDao countDocumentDao;

    public CountDocumentPresenterImpl(ICountDocumentView changeDocumentView) {
        this.countDocumentView = changeDocumentView;
        this.countDocumentDao = new ChangeDocumentDao();
    }


    @Override
    public void onCallSuccess(Object object) {
        if (object instanceof NumberCountDocument) {
            NumberCountDocument typeChangeDocumentRespone = (NumberCountDocument) object;
            countDocumentView.onSuccessCountDoc(typeChangeDocumentRespone);
        }

    }

    @Override
    public void onCallError(Object object) {
        if (countDocumentView != null) {
            if (object instanceof APIError) {
                APIError apiError = (APIError) object;
                if (apiError.getCode() == Constants.RESPONE_INVALID) {
                    countDocumentView.onError(apiError);
                } else {
                    countDocumentView.onError(new APIError(0, "changeFail"));
                }
            } else {
                countDocumentView.onError(new APIError(0, "changeFail"));
            }
        }
    }

    @Override
    public void getCountDocument() {
        countDocumentDao.onGetCountDocumentDao(this);
    }
}
