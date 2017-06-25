package iamdilipkumar.com.spacedig.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v4.app.ShareCompat;

import iamdilipkumar.com.spacedig.BuildConfig;
import iamdilipkumar.com.spacedig.R;
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

    public static SimpleItemModel getNeoModel(NearEarthObject item, Context context) {
        String description = "", estDiameter = "", orbData = "";
        String closeApproach = context.getString(R.string.close_approach);
        String hazardous = context.getString(R.string.hazardous_asteroid);

        if (item.getIsPotentiallyHazardousAsteroid()) {
            hazardous += " true";
        } else {
            hazardous += " false";
        }


        for (CloseApproachDatum closeItem : item.getCloseApproachData()) {
            RelativeVelocity relativeVelocity = closeItem.getRelativeVelocity();
            closeApproach += "\n" + closeItem.getCloseApproachDate() +
                    closeItem.getOrbitingBody() + "\n\n" + context.getString(R.string.relative_velocity)
                    + "\n" + context.getString(R.string.kilometers_per_hour) + " " + relativeVelocity.getKilometersPerHour()
                    + "\n" + context.getString(R.string.kilometers_per_second) + " " + relativeVelocity.getKilometersPerSecond()
                    + "\n" + context.getString(R.string.miles_per_hour) + " " + relativeVelocity.getMilesPerHour();
        }

        EstimatedDiameter estimatedDiameter = item.getEstimatedDiameter();
        if (item.getEstimatedDiameter() != null) {
            estDiameter = context.getString(R.string.estimated_diameter) +
                    "\n" + context.getString(R.string.feet) + " " +
                    estimatedDiameter.getFeet().getEstimatedDiameterMax()
                    + "\n" + context.getString(R.string.kilometers) + " " +
                    estimatedDiameter.getKilometers().getEstimatedDiameterMax() + "\n" +
                    context.getString(R.string.meters) + " " +
                    estimatedDiameter.getMeters().getEstimatedDiameterMax()
                    + "\n" + context.getString(R.string.miles) + " " +
                    estimatedDiameter.getMiles().getEstimatedDiameterMax();
        }

        OrbitalData orbitalData = item.getOrbitalData();
        if (orbitalData != null) {
            orbData = context.getString(R.string.orbital_data)
                    + "\n" + context.getString(R.string.orbit_determination_date) + " " + orbitalData.getOrbitDeterminationDate()
                    + "\n" + context.getString(R.string.orbit_uncertainty) + " " + orbitalData.getOrbitUncertainty()
                    + "\n" + context.getString(R.string.minimum_orbit_intersection) + " " + orbitalData.getMinimumOrbitIntersection()
                    + "\n" + context.getString(R.string.jupiter_tisserand_invariant) + " " + orbitalData.getJupiterTisserandInvariant()
                    + "\n" + context.getString(R.string.epoch_osculation) + " " + orbitalData.getEpochOsculation()
                    + "\n" + context.getString(R.string.eccentricity) + " " + orbitalData.getEccentricity()
                    + "\n" + context.getString(R.string.semi_major_axis) + " " + orbitalData.getSemiMajorAxis()
                    + "\n" + context.getString(R.string.inclination) + " " + orbitalData.getInclination()
                    + "\n" + context.getString(R.string.ascending_node_longitude) + " " + orbitalData.getAscendingNodeLongitude()
                    + "\n" + context.getString(R.string.orbital_period) + " " + orbitalData.getOrbitalPeriod()
                    + "\n" + context.getString(R.string.perihelion_distance) + " " + orbitalData.getPerihelionDistance()
                    + "\n" + context.getString(R.string.aphelion_distance) + " " + orbitalData.getAphelionDistance()
                    + "\n" + context.getString(R.string.perihelion_time) + " " + orbitalData.getPerihelionTime()
                    + "\n" + context.getString(R.string.mean_anomaly) + " " + orbitalData.getMeanAnomaly() +
                    "\n" + context.getString(R.string.mean_motion) + " " + orbitalData.getMeanMotion() +
                    "\n" + context.getString(R.string.equinox) + " " + orbitalData.getEquinox();
        }

        description = context.getString(R.string.absolute_magnitude) + " "
                + item.getAbsoluteMagnitudeH() + "\n" + hazardous + "\n\n" + closeApproach + "\n\n" 
                + estDiameter + "\n\n" + orbData;

        return new SimpleItemModel(item.getNeoReferenceId(), item.getName(),
                hazardous, "http://star.arm.ac.uk/impact-hazard/DOOMS_DAY.JPG", description, 0);
    }
}
