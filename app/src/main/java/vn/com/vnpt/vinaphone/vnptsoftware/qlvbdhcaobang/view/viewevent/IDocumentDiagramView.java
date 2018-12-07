package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.view.viewevent;

import android.graphics.Bitmap;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;

/**
 * Created by VietNH on 4/12/2017.
 */

public interface IDocumentDiagramView {
    void onError(APIError apiError);
    void showProgress();
    void hideProgress();
    void onSuccessDiagram(Bitmap bitmap);
}