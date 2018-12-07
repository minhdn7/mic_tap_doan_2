package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.ConvertUtils;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.AlertDialogManager;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.Application;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration.ConnectionDetector;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.downloadfile.DownloadFileDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.dao.login.LoginDao;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.APIError;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.AttachFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.GetViewFileObj;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.LoginRespone;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.pojo.respone.RelatedFileInfo;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.model.service.listener.ICallFinishedListener;

/**
 * Created by VietNH on 8/31/2017.
 */

public class RelatedFileAdapter extends ArrayAdapter<RelatedFileInfo> {
    private Context context;
    private int resource;
    private List<RelatedFileInfo> attachFileInfoList;
    private ConnectionDetector connectionDetector;
    private ICallFinishedListener callFinishedListener;
    private ProgressDialog progressDialog;
    private LoginDao loginDao;
    private DownloadFileDao downloadFileDao;

    public RelatedFileAdapter(Context context, int resource, List<RelatedFileInfo> attachFileInfoList) {
        super(context, resource, attachFileInfoList);
        this.context = context;
        this.resource = resource;
        this.attachFileInfoList = attachFileInfoList;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(this.resource, null);
        LinearLayout itemAttackFile = (LinearLayout) view.findViewById(R.id.itemAttackFile);
        ImageView ic_file = (ImageView) view.findViewById(R.id.ic_file);
        TextView filename = (TextView) view.findViewById(R.id.filename);
        filename.setTypeface(Application.getApp().getTypeface());
        final RelatedFileInfo attachFileInfo = attachFileInfoList.get(position);
        filename.setText(attachFileInfo.getName());
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.DOC) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.DOCX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_doc));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.XLS) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.XLSX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_xls));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PPT) || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PPTX)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ppt));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PDF)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pdf));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.ZIP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_zip));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.RAR)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_rar));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TXT)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_txt));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.MPP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_mpp));
        }
        if (attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.JPEG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.PNG)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.GIF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.TIFF)
                || attachFileInfo.getName().trim().toUpperCase().endsWith(Constants.BMP)) {
            ic_file.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_image));
        }
        itemAttackFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLinkViewAttach(attachFileInfo);
            }
        });
        return view;
    }

    private void downloadFile(final RelatedFileInfo attachFileInfo) {
        connectionDetector = new ConnectionDetector(context);
        progressDialog = new ProgressDialog(context);
        downloadFileDao = new DownloadFileDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                if (object instanceof ResponseBody) {
                    ResponseBody responseBody = (ResponseBody) object;
                    progressDialog.dismiss();
                    if (writeResponseBodyToDisk(responseBody, attachFileInfo.getName())) {
                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.TITLE_SUCCESS), context.getString(R.string.DOWNLOAD_FILE_SUCCESS), true, AlertDialogManager.SUCCESS);
                    } else {
                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.DOWNLOAD_TITLE_ERROR), context.getString(R.string.DOWNLOAD_FILE_ERROR), true, AlertDialogManager.ERROR);
                    }
                }
                if (object instanceof LoginRespone) {
                    LoginRespone loginRespone = (LoginRespone) object;
                    if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                        LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
                        if (connectionDetector.isConnectingToInternet()) {
                            downloadFileDao.onDownloadFileDao(attachFileInfo.getId(), this);
                        } else {
                            AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                        }
                    }
                }
            }

            @Override
            public void onCallError(Object object) {
                APIError apiError = (APIError) object;
                if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                    if (connectionDetector.isConnectingToInternet()) {
                        loginDao = new LoginDao();
                        loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), callFinishedListener);
                    } else {
                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                    }
                } else {
                    AlertDialogManager.showAlertDialog(context, context.getString(R.string.DOWNLOAD_TITLE_ERROR), context.getString(R.string.DOWNLOAD_FILE_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        };
        progressDialog.setMessage(context.getString(R.string.DOWNLOADING_REQUEST));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        downloadFileDao.onDownloadFileDao(attachFileInfo.getId(), callFinishedListener);
    }

    private boolean writeResponseBodyToDisk(ResponseBody body, String filename) {
        try {
            // todo change the file location/name according to your needs
            File fileDownload = new File(context.getExternalFilesDir(null) + File.separator + filename);
            int count = 0;
            while (fileDownload.exists()) {
                int i = filename.lastIndexOf(".");
                count++;
                String filenameNew = filename.substring(0, i) + "(" + String.valueOf(count) + ")" + "." + filename.substring(i + 1, filename.length());
                fileDownload = new File(context.getExternalFilesDir(null) + File.separator + filenameNew);
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(fileDownload);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void getLinkViewAttach(final RelatedFileInfo attachFileInfo) {
        progressDialog = new ProgressDialog(context);
        downloadFileDao = new DownloadFileDao();
        callFinishedListener = new ICallFinishedListener() {
            @Override
            public void onCallSuccess(Object object) {
                if (object instanceof GetViewFileObj) {
                    progressDialog.dismiss();
                    //Show pdfViewer
                    if (((GetViewFileObj) object).getStatus().getCode().equalsIgnoreCase("0")) {
                        webViewDialog((GetViewFileObj) object, attachFileInfo.getName());
                    }

                }
                if (object instanceof LoginRespone) {
                    LoginRespone loginRespone = (LoginRespone) object;
                    if (loginRespone.getResponeAPI().getCode().equals(Constants.RESPONE_SUCCESS)) {
                        LoginInfo loginInfo = ConvertUtils.fromJSON(loginRespone.getData(), LoginInfo.class);
                        Application.getApp().getAppPrefs().setToken(loginInfo.getToken());
                        if (connectionDetector.isConnectingToInternet()) {
                            downloadFileDao.onGetUrlFileDao(attachFileInfo.getId(), this);
                        } else {
                            AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                        }
                    }
                }
            }

            @Override
            public void onCallError(Object object) {
                APIError apiError = (APIError) object;
                if (apiError.getCode() == Constants.RESPONE_UNAUTHEN) {
                    if (connectionDetector.isConnectingToInternet()) {
                        loginDao = new LoginDao();
                        loginDao.onSendLoginDao(Application.getApp().getAppPrefs().getAccount(), callFinishedListener);
                    } else {
                        AlertDialogManager.showAlertDialog(context, context.getString(R.string.NETWORK_TITLE_ERROR), context.getString(R.string.NO_INTERNET_ERROR), true, AlertDialogManager.ERROR);
                    }
                } else {
                    AlertDialogManager.showAlertDialog(context, context.getString(R.string.DOWNLOAD_TITLE_ERROR), context.getString(R.string.DOWNLOAD_FILE_ERROR), true, AlertDialogManager.ERROR);
                }
            }
        };
        progressDialog.setMessage(context.getString(R.string.DOWNLOADING_REQUEST));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        downloadFileDao.onGetUrlFileDao(attachFileInfo.getId(), callFinishedListener);
    }

    private void webViewDialog(GetViewFileObj responseBody, String fileName) {

        final Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.layout_dialog_view_file);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title_label);
        ImageView ivCloseDialog = (ImageView) dialog.findViewById(R.id.ivCloseDialog);
        WebView webViewFile = (WebView) dialog.findViewById(R.id.web_view_file);
        tvTitle.setText(fileName);
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        webViewFile.getSettings().setJavaScriptEnabled(true);
        webViewFile.getSettings().setPluginState(WebSettings.PluginState.ON);
        //---you need this to prevent the webview from
        // launching another browser when a url
        // redirection occurs---
        webViewFile.setWebViewClient(new Callback());

        String pdfURL = responseBody.getData();
        webViewFile.loadUrl(pdfURL);
        dialog.show();
    }

    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            return (false);
        }
    }
}
