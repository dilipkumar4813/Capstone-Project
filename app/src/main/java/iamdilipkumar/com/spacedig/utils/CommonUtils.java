package iamdilipkumar.com.spacedig.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.support.v4.app.ShareCompat;

import java.util.ArrayList;
import java.util.List;

import iamdilipkumar.com.spacedig.BuildConfig;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.data.NeoColumns;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.epic.AttitudeQuaternions;
import iamdilipkumar.com.spacedig.models.epic.CentroidCoordinates;
import iamdilipkumar.com.spacedig.models.epic.DscovrJ2000Position;
import iamdilipkumar.com.spacedig.models.epic.Epic;
import iamdilipkumar.com.spacedig.models.epic.LunarJ2000Position;
import iamdilipkumar.com.spacedig.models.epic.SunJ2000Position;
import iamdilipkumar.com.spacedig.models.neo.CloseApproachDatum;
import iamdilipkumar.com.spacedig.models.neo.EstimatedDiameter;
import iamdilipkumar.com.spacedig.models.neo.NearEarthObject;
import iamdilipkumar.com.spacedig.models.neo.OrbitalData;
import iamdilipkumar.com.spacedig.models.neo.RelativeVelocity;
import iamdilipkumar.com.spacedig.models.rover.MarsRoverPhoto;
import iamdilipkumar.com.spacedig.models.rover.Rover;

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
