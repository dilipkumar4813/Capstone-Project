package iamdilipkumar.com.spacedig.models.neo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 25/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class OrbitalData {

    @SerializedName("orbit_id")
    @Expose
    private String orbitId;
    @SerializedName("orbit_determination_date")
    @Expose
    private String orbitDeterminationDate;
    @SerializedName("orbit_uncertainty")
    @Expose
    private String orbitUncertainty;
    @SerializedName("minimum_orbit_intersection")
    @Expose
    private String minimumOrbitIntersection;
    @SerializedName("jupiter_tisserand_invariant")
    @Expose
    private String jupiterTisserandInvariant;
    @SerializedName("epoch_osculation")
    @Expose
    private String epochOsculation;
    @SerializedName("eccentricity")
    @Expose
    private String eccentricity;
    @SerializedName("semi_major_axis")
    @Expose
    private String semiMajorAxis;
    @SerializedName("inclination")
    @Expose
    private String inclination;
    @SerializedName("ascending_node_longitude")
    @Expose
    private String ascendingNodeLongitude;
    @SerializedName("orbital_period")
    @Expose
    private String orbitalPeriod;
    @SerializedName("perihelion_distance")
    @Expose
    private String perihelionDistance;
    @SerializedName("perihelion_argument")
    @Expose
    private String perihelionArgument;
    @SerializedName("aphelion_distance")
    @Expose
    private String aphelionDistance;
    @SerializedName("perihelion_time")
    @Expose
    private String perihelionTime;
    @SerializedName("mean_anomaly")
    @Expose
    private String meanAnomaly;
    @SerializedName("mean_motion")
    @Expose
    private String meanMotion;
    @SerializedName("equinox")
    @Expose
    private String equinox;

    public String getOrbitId() {
        return orbitId;
    }

    public void setOrbitId(String orbitId) {
        this.orbitId = orbitId;
    }

    public String getOrbitDeterminationDate() {
        return orbitDeterminationDate;
    }

    public void setOrbitDeterminationDate(String orbitDeterminationDate) {
        this.orbitDeterminationDate = orbitDeterminationDate;
    }

    public String getOrbitUncertainty() {
        return orbitUncertainty;
    }

    public void setOrbitUncertainty(String orbitUncertainty) {
        this.orbitUncertainty = orbitUncertainty;
    }

    public String getMinimumOrbitIntersection() {
        return minimumOrbitIntersection;
    }

    public void setMinimumOrbitIntersection(String minimumOrbitIntersection) {
        this.minimumOrbitIntersection = minimumOrbitIntersection;
    }

    public String getJupiterTisserandInvariant() {
        return jupiterTisserandInvariant;
    }

    public void setJupiterTisserandInvariant(String jupiterTisserandInvariant) {
        this.jupiterTisserandInvariant = jupiterTisserandInvariant;
    }

    public String getEpochOsculation() {
        return epochOsculation;
    }

    public void setEpochOsculation(String epochOsculation) {
        this.epochOsculation = epochOsculation;
    }

    public String getEccentricity() {
        return eccentricity;
    }

    public void setEccentricity(String eccentricity) {
        this.eccentricity = eccentricity;
    }

    public String getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public void setSemiMajorAxis(String semiMajorAxis) {
        this.semiMajorAxis = semiMajorAxis;
    }

    public String getInclination() {
        return inclination;
    }

    public void setInclination(String inclination) {
        this.inclination = inclination;
    }

    public String getAscendingNodeLongitude() {
        return ascendingNodeLongitude;
    }

    public void setAscendingNodeLongitude(String ascendingNodeLongitude) {
        this.ascendingNodeLongitude = ascendingNodeLongitude;
    }

    public String getOrbitalPeriod() {
        return orbitalPeriod;
    }

    public void setOrbitalPeriod(String orbitalPeriod) {
        this.orbitalPeriod = orbitalPeriod;
    }

    public String getPerihelionDistance() {
        return perihelionDistance;
    }

    public void setPerihelionDistance(String perihelionDistance) {
        this.perihelionDistance = perihelionDistance;
    }

    public String getPerihelionArgument() {
        return perihelionArgument;
    }

    public void setPerihelionArgument(String perihelionArgument) {
        this.perihelionArgument = perihelionArgument;
    }

    public String getAphelionDistance() {
        return aphelionDistance;
    }

    public void setAphelionDistance(String aphelionDistance) {
        this.aphelionDistance = aphelionDistance;
    }

    public String getPerihelionTime() {
        return perihelionTime;
    }

    public void setPerihelionTime(String perihelionTime) {
        this.perihelionTime = perihelionTime;
    }

    public String getMeanAnomaly() {
        return meanAnomaly;
    }

    public void setMeanAnomaly(String meanAnomaly) {
        this.meanAnomaly = meanAnomaly;
    }

    public String getMeanMotion() {
        return meanMotion;
    }

    public void setMeanMotion(String meanMotion) {
        this.meanMotion = meanMotion;
    }

    public String getEquinox() {
        return equinox;
    }

    public void setEquinox(String equinox) {
        this.equinox = equinox;
    }

}
