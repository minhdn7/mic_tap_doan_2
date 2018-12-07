package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao;

import java.io.IOException;
import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ErrorDef;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.BaseService;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.service.syncdata.HandleSyncService;

/**
 * Created by VietNH on 4/14/2017.
 */

public class BaseDao {

    public static <T> void call(Call<T> call, final ICallFinishedListener finishedListener) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.code() == Constants.RESPONE_SUCCESS_HANDLER) {
                    finishedListener.onCallSuccess(response.body());
                } else {
                    finishedListener.onCallError(BaseService.parseErrorHandler(response));
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    finishedListener.onCallError(new APIError(0, ErrorDef.MESSAGE_TIMEOUT_NETWORK));
                } else {
                    if (t instanceof IOException) {
                        finishedListener.onCallError(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    } else {
                        finishedListener.onCallError(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    }
                }
            }
        });
    }

    public static <T> void call(Call<T> call, final HandleSyncService.HandleGetCount handleGetCount) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.code() == Constants.RESPONE_SUCCESS_HANDLER) {
                    handleGetCount.onSuccessGetCount(response.body());
                } else {
                    handleGetCount.onErrorGetCount(BaseService.parseErrorHandler(response));
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    handleGetCount.onErrorGetCount(new APIError(0, ErrorDef.MESSAGE_TIMEOUT_NETWORK));
                } else {
                    if (t instanceof IOException) {
                        handleGetCount.onErrorGetCount(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    } else {
                        handleGetCount.onErrorGetCount(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    }
                }
            }
        });
    }

    public static <T> void call(Call<T> call, final HandleSyncService.HandleGetRecords handleGetRecords) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.code() == Constants.RESPONE_SUCCESS_HANDLER) {
                    handleGetRecords.onSuccessGetRecords(response.body());
                } else {
                    handleGetRecords.onErrorGetRecords(BaseService.parseErrorHandler(response));
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    handleGetRecords.onErrorGetRecords(new APIError(0, ErrorDef.MESSAGE_TIMEOUT_NETWORK));
                } else {
                    if (t instanceof IOException) {
                        handleGetRecords.onErrorGetRecords(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    } else {
                        handleGetRecords.onErrorGetRecords(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    }
                }
            }
        });
    }

    public static <T> void call(Call<T> call, final HandleSyncService.HandleGetSchedules handleGetSchedules) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.code() == Constants.RESPONE_SUCCESS_HANDLER) {
                    handleGetSchedules.onSuccessGetSchedules(response.body());
                } else {
                    handleGetSchedules.onErrorGetSchedules(BaseService.parseErrorHandler(response));
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    handleGetSchedules.onErrorGetSchedules(new APIError(0, ErrorDef.MESSAGE_TIMEOUT_NETWORK));
                } else {
                    if (t instanceof IOException) {
                        handleGetSchedules.onErrorGetSchedules(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    } else {
                        handleGetSchedules.onErrorGetSchedules(new APIError(0, ErrorDef.MESSAGE_CONNECTION_SERVER));
                    }
                }
            }
        });
    }


}