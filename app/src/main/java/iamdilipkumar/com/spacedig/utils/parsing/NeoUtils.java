package iamdilipkumar.com.spacedig.utils.parsing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.data.NeoColumns;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.neo.CloseApproachDatum;
import iamdilipkumar.com.spacedig.models.neo.EstimatedDiameter;
import iamdilipkumar.com.spacedig.models.neo.NearEarthObject;
import iamdilipkumar.com.spacedig.models.neo.OrbitalData;
import iamdilipkumar.com.spacedig.models.neo.RelativeVelocity;

/**
 * Created on 02/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class NeoUtils {

    public static void getNeoModel(NearEarthObject item, Context context) {
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

        ContentValues neoData = new ContentValues();
        neoData.put(NeoColumns.NEO_ID, item.getNeoReferenceId());
        neoData.put(NeoColumns.NAME, item.getName());
        neoData.put(NeoColumns.DESCRIPTION, description);
        neoData.put(NeoColumns.SHORT_DESCRIPTION, hazardous);
        neoData.put(NeoColumns.IMAGEURL, "http://star.arm.ac.uk/impact-hazard/DOOMS_DAY.JPG");
        context.getContentResolver().insert(NasaProvider.NeoData.CONTENT_URI, neoData);
    }

    public static List<SimpleItemModel> getNeoList(Context context) {
        List<SimpleItemModel> itemModels = new ArrayList<>();
        Cursor neoCursor = context.getContentResolver().query(NasaProvider.NeoData.CONTENT_URI,
                null, null, null, null);
        if (neoCursor != null) {
            if (neoCursor.moveToFirst()) {
                do {
                    SimpleItemModel itemModel =
                            new SimpleItemModel(neoCursor.getString(
                                    neoCursor.getColumnIndex(NeoColumns.NEO_ID)),
                                    neoCursor.getString(neoCursor.getColumnIndex(NeoColumns.NAME)),
                                    neoCursor.getString(neoCursor.getColumnIndex(NeoColumns.SHORT_DESCRIPTION)),
                                    neoCursor.getString(neoCursor.getColumnIndex(NeoColumns.IMAGEURL)),
                                    neoCursor.getString(neoCursor.getColumnIndex(NeoColumns.DESCRIPTION)),
                                    0);
                    itemModels.add(itemModel);
                } while (neoCursor.moveToNext());
            }
            neoCursor.close();
        }

        return itemModels;
    }
}
