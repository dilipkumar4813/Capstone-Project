package iamdilipkumar.com.spacedig.models.neo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 25/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class NearEarthObject {

    @SerializedName("links")
    @Expose
    private Links_ links;
    @SerializedName("neo_reference_id")
    @Expose
    private String neoReferenceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nasa_jpl_url")
    @Expose
    private String nasaJplUrl;
    @SerializedName("absolute_magnitude_h")
    @Expose
    private Double absoluteMagnitudeH;
    @SerializedName("estimated_diameter")
    @Expose
    private EstimatedDiameter estimatedDiameter;
    @SerializedName("is_potentially_hazardous_asteroid")
    @Expose
    private Boolean isPotentiallyHazardousAsteroid;
    @SerializedName("close_approach_data")
    @Expose
    private List<CloseApproachDatum> closeApproachData = null;
    @SerializedName("orbital_data")
    @Expose
    private OrbitalData orbitalData;

    public Links_ getLinks() {
        return links;
    }

    public void setLinks(Links_ links) {
        this.links = links;
    }

    public String getNeoReferenceId() {
        return neoReferenceId;
    }

    public void setNeoReferenceId(String neoReferenceId) {
        this.neoReferenceId = neoReferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNasaJplUrl() {
        return nasaJplUrl;
    }

    public void setNasaJplUrl(String nasaJplUrl) {
        this.nasaJplUrl = nasaJplUrl;
    }

    public Double getAbsoluteMagnitudeH() {
        return absoluteMagnitudeH;
    }

    public void setAbsoluteMagnitudeH(Double absoluteMagnitudeH) {
        this.absoluteMagnitudeH = absoluteMagnitudeH;
    }

    public EstimatedDiameter getEstimatedDiameter() {
        return estimatedDiameter;
    }

    public void setEstimatedDiameter(EstimatedDiameter estimatedDiameter) {
        this.estimatedDiameter = estimatedDiameter;
    }

    public Boolean getIsPotentiallyHazardousAsteroid() {
        return isPotentiallyHazardousAsteroid;
    }

    public void setIsPotentiallyHazardousAsteroid(Boolean isPotentiallyHazardousAsteroid) {
        this.isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid;
    }

    public List<CloseApproachDatum> getCloseApproachData() {
        return closeApproachData;
    }

    public void setCloseApproachData(List<CloseApproachDatum> closeApproachData) {
        this.closeApproachData = closeApproachData;
    }

    public OrbitalData getOrbitalData() {
        return orbitalData;
    }

    public void setOrbitalData(OrbitalData orbitalData) {
        this.orbitalData = orbitalData;
    }

}
