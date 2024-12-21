package com.deificindia.indifun.Utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.deificindia.indifun.R;

public class Indifun {
    public static void toast(Context activity, String message, String status) {
        Toast toast = new Toast(activity.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);

        //inflate view
        //View custom_view = activity.getLayoutInflater().inflate(R.layout.toast, null);
        View custom_view = LayoutInflater.from(activity).inflate(R.layout.toast, null, false);
        ((TextView) custom_view.findViewById(R.id.message)).setText(message);
        if (status.equals("e")){
            ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_close);
            ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(activity.getResources().getColor(R.color.error_notification));
        }
        else if (status.equals("s")){
            ((ImageView) custom_view.findViewById(R.id.icon)).setImageResource(R.drawable.ic_done);
            ((CardView) custom_view.findViewById(R.id.parent_view)).setCardBackgroundColor(activity.getResources().getColor(R.color.success));
        }

        toast.setView(custom_view);
        toast.show();
    }

    public static boolean isOnline(final Activity context, boolean message) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        if (message)
        {
//            final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//            dialog.setTitle(context.getString(R.string.app_name));
//            dialog.setCancelable(false);
//            dialog.setMessage(context.getString(R.string.check_connection));
//            dialog.setPositiveButton(context.getString(R.string.settings), new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    dialog.dismiss();
//                    context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
//                }
//            });
//            dialog.setNegativeButton(context.getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();

            return false;
        }
        return false;
    }


    public final static boolean isValidEmail(CharSequence inputEmail) {
        if (inputEmail == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
        }
    }


    public static void hideKeyboard(Context mContext) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        View focus = ((Activity) mContext).getCurrentFocus();

        if (focus != null) {

            inputManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
