package iamdilipkumar.com.spacedig.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.ShareCompat;

import iamdilipkumar.com.spacedig.R;

/**
 * Created on 22/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class CommonUtils {

    public static boolean checkNetworkConnectivity(Context context) {
        boolean networkAvailable = false;

        ConnectivityManager conMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            networkAvailable = true;
        }

        return networkAvailable;
    }

    public static void shareData(Activity activity, String message) {
        ShareCompat.IntentBuilder shareBuilder = ShareCompat.IntentBuilder.from(activity);
        shareBuilder.setType(activity.getString(R.string.share_type));
        shareBuilder.setText(message);
        shareBuilder.setChooserTitle(activity.getString(R.string.share_title));
        shareBuilder.startChooser();
    }
}
