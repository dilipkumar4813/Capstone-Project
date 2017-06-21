package iamdilipkumar.com.spacedig.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 21/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class Rover {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("landing_date")
    @Expose
    private String landingDate;
    @SerializedName("launch_date")
    @Expose
    private String launchDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("max_sol")
    @Expose
    private Integer maxSol;
    @SerializedName("max_date")
    @Expose
    private String maxDate;
    @SerializedName("total_photos")
    @Expose
    private Integer totalPhotos;
    @SerializedName("cameras")
    @Expose
    private List<CameraDetails> cameras = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMaxSol() {
        return maxSol;
    }

    public void setMaxSol(Integer maxSol) {
        this.maxSol = maxSol;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public Integer getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(Integer totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public List<CameraDetails> getCameras() {
        return cameras;
    }

    public void setCameras(List<CameraDetails> cameras) {
        this.cameras = cameras;
    }

}