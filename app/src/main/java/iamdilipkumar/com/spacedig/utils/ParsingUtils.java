package iamdilipkumar.com.spacedig.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import iamdilipkumar.com.spacedig.BuildConfig;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.data.CadColumns;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.data.NeoColumns;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.cad.Cad;
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
import iamdilipkumar.com.spacedig.models.search.Item;
import iamdilipkumar.com.spacedig.models.search.Search;

/**
 * Created on 05/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class ParsingUtils {

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

    public static List<SimpleItemModel> getSearchDetails(Context context, Search search) {
        List<SimpleItemModel> searchItems = new ArrayList<>();

        for (Item item : search.getCollection().getItems()) {
            String id, title, description, shortDescription, imageUrl, downloadUrl;
            id = item.getData().get(0).getNasaId();
            title = item.getData().get(0).getTitle();
            description = item.getData().get(0).getDescription() + "\n\n" +
                    context.getString(R.string.created) + " " + item.getData().get(0).getDateCreated();
            shortDescription = context.getString(R.string.media_type)
                    + " " + item.getData().get(0).getMediaType();

            if (item.getData().get(0).getMediaType().contains("video")) {
                imageUrl = "https://cdn.pixabay.com/photo/2016/01/19/17/29/earth-1149733_960_720.jpg";
            } else {
                imageUrl = item.getLinks().get(0).getHref();
            }

            downloadUrl = item.getHref();

            searchItems.add(new SimpleItemModel(id, title,
                    shortDescription, imageUrl, description, 0, downloadUrl));
        }

        return searchItems;
    }

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

    public static List<SimpleItemModel> getCadDataIntoContentProvider(Cad cad, Context context) {
        List<SimpleItemModel> simpleItemModels = new ArrayList<>();

        for (List<String> item : cad.getData()) {
            String name = "", information = "", shortDescription = "";
            for (int i = 0; i < cad.getFields().size(); i++) {
                if (i == 0) {
                    name = item.get(i);
                }

                if (cad.getFields().get(i).equalsIgnoreCase("dist")) {
                    shortDescription = context.getString(R.string.
                            collision_approach_short_description)
                            + " " + item.get(i);
                }
                information += cad.getFields().get(i) + ": " + item.get(i) + "\n";
            }

            ContentValues cadData = new ContentValues();
            cadData.put(CadColumns.NEO_ID, "");
            cadData.put(CadColumns.NAME, name);
            cadData.put(CadColumns.DESCRIPTION, information);
            cadData.put(CadColumns.SHORT_DESCRIPTION, shortDescription);
            cadData.put(CadColumns.IMAGEURL, "https://pbs.twimg.com/media/CQ5xYRXVEAAeiCy.jpg");
            context.getContentResolver().insert(NasaProvider.CadData.CONTENT_URI, cadData);
        }

        return simpleItemModels;
    }

    public static List<SimpleItemModel> getCadContent(Context context) {
        List<SimpleItemModel> simpleItemModels = new ArrayList<>();

        Cursor cadCursor = context.getContentResolver().query(NasaProvider.CadData.CONTENT_URI,
                null, null, null, null);
        if (cadCursor != null) {
            if (cadCursor.moveToFirst()) {
                do {
                    SimpleItemModel itemModel =
                            new SimpleItemModel(cadCursor.getString(
                                    cadCursor.getColumnIndex(CadColumns.NEO_ID)),
                                    cadCursor.getString(cadCursor.getColumnIndex(CadColumns.NAME)),
                                    cadCursor.getString(cadCursor.getColumnIndex(CadColumns.SHORT_DESCRIPTION)),
                                    cadCursor.getString(cadCursor.getColumnIndex(CadColumns.IMAGEURL)),
                                    cadCursor.getString(cadCursor.getColumnIndex(CadColumns.DESCRIPTION)),
                                    0);
                    simpleItemModels.add(itemModel);
                } while (cadCursor.moveToNext());
            }
            cadCursor.close();
        }

        return simpleItemModels;
    }
}
