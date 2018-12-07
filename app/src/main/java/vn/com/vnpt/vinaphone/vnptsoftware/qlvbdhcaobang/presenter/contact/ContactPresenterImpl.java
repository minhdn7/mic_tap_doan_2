package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.presenter.contact;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.contact.ContactDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ContactInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ContactRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.syncevent.IContactSync;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ContactPresenterImpl implements IContactPresenter, ICallFinishedListener {
    public IContactSync contactSync;
    public ContactDao contactDao;

    public ContactPresenterImpl(IContactSync contactSync) {
        this.contactSync = contactSync;
        this.contactDao = new ContactDao();
    }

    @Override
    public void getContacts() {
        if (contactSync != null) {
            contactSync.showProgressSync();
            contactDao.onSendGetContactsDao(this);
        }
    }

    @Override
    public void onCallSuccess(Object object) {
        ContactRespone contactRespone = (ContactRespone) object;
        contactSync.hideProgressSync();
        if (contactRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
            contactSync.onSuccessSync(ConvertUtils.fromJSONList(contactRespone.getData(), ContactInfo.class));
        } else {
            contactSync.onErrorSync(new APIError(0, "Có lỗi xảy ra!\nVui lòng thử lại sau!"));
        }
    }

    @Override
    public void onCallError(Object object) {
        contactSync.onErrorSync((APIError) object);
        contactSync.hideProgressSync();
    }

}
