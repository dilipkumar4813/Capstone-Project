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

    public static SimpleItemModel getEpicModel(Epic item, Context context) {
        String[] subParts = item.getImage().split("_");
        String year = subParts[2].substring(0, 4);
        String month = subParts[2].substring(4, 6);
        String day = subParts[2].substring(6, 8);
        String image = "https://api.nasa.gov/EPIC/archive/natural/" + year + "/" + month + "/" +
                day + "/png/" + item.getImage() + ".png?api_key=" + BuildConfig.API;
        String sunPosition = "", aQuaternions = "", ceroidCoordinates = "", lunarPosition = "",
                dscovrPosition = "";
        CentroidCoordinates centroidCoordinates = item.getCentroidCoordinates();
        SunJ2000Position sunJ2000Position = item.getSunJ2000Position();
        LunarJ2000Position lunarJ2000Position = item.getLunarJ2000Position();
        DscovrJ2000Position dscovrJ2000Position = item.getDscovrJ2000Position();
        AttitudeQuaternions attitudeQuaternions = item.getAttitudeQuaternions();

        String earthDate = item.getDate();

        if (attitudeQuaternions != null) {
            aQuaternions = context.getString(R.string.quaternions) + " Q0 - "
                    + attitudeQuaternions.getQ0() + ", Q1 - " +
                    attitudeQuaternions.getQ1() + ", Q2 - " +
                    attitudeQuaternions.getQ2() + ", Q3 - " +
                    attitudeQuaternions.getQ3();
        }

        if (dscovrJ2000Position != null) {
            dscovrPosition = context.getString(R.string.dscovr_position)
                    + " X - " + dscovrJ2000Position.getX()
                    + " Y - " + dscovrJ2000Position.getY()
                    + " Z - " + dscovrJ2000Position.getZ();
        }

        if (lunarJ2000Position != null) {
            lunarPosition = context.getString(R.string.lunar_position)
                    + " X -" + lunarJ2000Position.getX()
                    + " Y -" + lunarJ2000Position.getY()
                    + " Z -" + lunarJ2000Position.getZ();
        }

        if (sunJ2000Position != null) {
            sunPosition = context.getString(R.string.sun_position)
                    + " X - " + sunJ2000Position.getX()
                    + " Y - " + sunJ2000Position.getY()
                    + " Z - " + sunJ2000Position.getZ();
        }

        if (centroidCoordinates != null) {
            ceroidCoordinates = context.getString(R.string.centroid_coordinates) + " "
                    + centroidCoordinates.getLat() + ", "
                    + centroidCoordinates.getLon();
        }

        String description = item.getCaption() + "\n\n" + aQuaternions + "\n\n"
                + ceroidCoordinates + "\n\n" + lunarPosition + "\n\n"
                + dscovrPosition + "\n\n" + sunPosition;

        return new SimpleItemModel("", earthDate,
                ceroidCoordinates, image, description, 0);
    }

    public static SimpleItemModel getRoverModel(MarsRoverPhoto item, Context context) {
        String landingDate = "", launchDate = "", status = "";
        Rover rover = item.getRover();
        if (rover != null) {
            landingDate = context.getString(R.string.landing_date) + " " + rover.getLandingDate();
            launchDate = context.getString(R.string.launch_date) + " " + rover.getLaunchDate();
            status = context.getString(R.string.status) + " " + rover.getStatus();
        }
        String fullDescription = landingDate + "\n" + launchDate + "\n\n" + status;
        return new SimpleItemModel(String.valueOf(item.getId()),
                item.getCamera().getFullName(), item.getCamera().getName(),
                item.getImgSrc(), fullDescription, 0);
    }
}
