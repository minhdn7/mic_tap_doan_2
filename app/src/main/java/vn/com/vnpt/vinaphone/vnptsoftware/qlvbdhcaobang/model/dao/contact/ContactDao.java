package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.contact;

import retrofit2.Call;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.BaseDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.ContactRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.api.IContactService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/29/2017.
 */

public class ContactDao extends BaseDao implements IContactDao {
    private IContactService contactService;

    @Override
    public void onSendGetContactsDao(ICallFinishedListener iCallFinishedListener) {
        contactService = BaseService.createService(IContactService.class);
        Call<ContactRespone> call = contactService.getContacts(BaseService.createAuthenHeaders());
        call(call, iCallFinishedListener);
    }
}
