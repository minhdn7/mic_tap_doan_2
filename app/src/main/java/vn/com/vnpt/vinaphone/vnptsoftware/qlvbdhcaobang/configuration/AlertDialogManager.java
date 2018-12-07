package vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.configuration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import vn.com.vnpt.vinaphone.vnptsoftware.qlvbdhcaobang.R;

/**
 * Created by VietNH on 8/21/2017.
 */

public class AlertDialogManager {

    public static final int ERROR = 1;
    public static final int SUCCESS = 0;
    public static final int WARNING = 2;
    public static final int INFO = 3;
    public static final int UPDATE_STORE = 4;

    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     *               - pass null if you don't want icon
     * */
    public static void showAlertDialog(final Context context, String title, String message, Boolean status, final int type) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        final AlertDialog alertDialog = builder.create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        if (status != null) {
            // Setting alert dialog icon
            switch (type) {
                case SUCCESS:
                    alertDialog.setIcon(R.drawable.success);
                    break;
                case ERROR:
                    alertDialog.setIcon(R.drawable.error);
                    break;
                case WARNING:
                    alertDialog.setIcon(R.drawable.warning);
                    break;
                case INFO:
                    alertDialog.setIcon(R.drawable.info);
                    break;
                case UPDATE_STORE:
                    alertDialog.setIcon(R.drawable.info);
                    break;
            }
        }
        // Setting OK Button
        alertDialog.setButton(context.getString(R.string.CLOSE_BUTTON), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                if (type == UPDATE_STORE) {
                    final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            }
        });
        // Showing Alert Message
        if(!((Activity)context).isFinishing()){
            alertDialog.show();
        }
    }

}
