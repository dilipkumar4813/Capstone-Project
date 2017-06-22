package iamdilipkumar.com.spacedig.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;

/**
 * Created on 22/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class DialogUtils {

    private static Dialog buildCustomDialog(Activity activity) {
        final Dialog customDialog = new Dialog(activity);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_custom);
        customDialog.setCanceledOnTouchOutside(false);

        customDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        return customDialog;
    }

    public static void noNetworkPreActionDialog(Activity activity) {
        final Dialog networkDialog = buildCustomDialog(activity);

        Button btnCancel = (Button) networkDialog.findViewById(R.id.btn_no);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkDialog.dismiss();
            }
        });

        Button btnYes = (Button) networkDialog.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkDialog.dismiss();
                activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });

        networkDialog.show();
    }

    public static void singleButtonDialog(Activity activity, String title, String message) {
        final Dialog singleDialog = buildCustomDialog(activity);

        TextView titleText = ButterKnife.findById(singleDialog,R.id.tv_dialog_title);
        titleText.setText(title);

        TextView messageText = ButterKnife.findById(singleDialog,R.id.tv_dialog_message);
        messageText.setText(message);

        Button btnCancel = ButterKnife.findById(singleDialog, R.id.btn_no);
        btnCancel.setVisibility(View.INVISIBLE);

        Button btnYes = ButterKnife.findById(singleDialog, R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleDialog.dismiss();
            }
        });

        singleDialog.show();
    }
}
