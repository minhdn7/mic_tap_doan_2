package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

import okhttp3.ResponseBody;
import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;

public class FileUtils {

    private Context mContext;

    public FileUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void openPDF(File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Uri path = Uri.fromFile(file);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            mContext.startActivity(pdfIntent);
        } catch(ActivityNotFoundException e) {
            Toast.makeText(mContext, mContext.getString(R.string.NO_SUPPORT_PDF_ERROR), Toast.LENGTH_SHORT).show();
        }
    }

    public void openExcel(File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Uri path = Uri.fromFile(file);
        Intent excelIntent = new Intent(Intent.ACTION_VIEW);
        excelIntent.setDataAndType(path, "application/vnd.ms-excel");
        excelIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            mContext.startActivity(excelIntent);
        } catch(ActivityNotFoundException e) {
            Toast.makeText(mContext, mContext.getString(R.string.NO_SUPPORT_EXCEL_ERROR), Toast.LENGTH_SHORT).show();
        }
    }

    public void openWord(File file) {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Uri path = Uri.fromFile(file);
        Intent wordIntent = new Intent(Intent.ACTION_VIEW);
        wordIntent.setDataAndType(path, "application/msword");
        wordIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            mContext.startActivity(wordIntent);
        } catch(ActivityNotFoundException e) {
            Toast.makeText(mContext, mContext.getString(R.string.NO_SUPPORT_WORD_ERROR), Toast.LENGTH_SHORT).show();
        }
    }

    public File writeResponseBodyToDisk(ResponseBody body, String filename) {
        try {
            // todo change the file location/name according to your needs
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);
            int count = 0;
            while (file.exists()) {
                int i = filename.lastIndexOf(".");
                count++;
                String filenameNew = filename.substring(0, i) + "(" + String.valueOf(count) + ")" + "." + filename.substring(i + 1, filename.length());
                file = new File(mContext.getExternalFilesDir(null) + File.separator + filenameNew);
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }
                outputStream.flush();
                return file;
            } catch (IOException e) {
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    public String validateExtension(String filename) {
        if (filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.DOC)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.DOCX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.XLS)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.XLSX)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.PDF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.JPG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.JPEG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.PNG)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.GIF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.TIFF)
                || filename.toUpperCase().endsWith(vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.common.Constants.BMP)) {
            return filename.trim().toUpperCase();
        }
        return null;
    }

}
