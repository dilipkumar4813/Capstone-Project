package iamdilipkumar.com.spacedig.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 04/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class EarthImagery {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("cloud_score")
    @Expose
    private Double cloudScore;
    @SerializedName("id")
    @Expose
    private String id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getCloudScore() {
        return cloudScore;
    }

    public void setCloudScore(Double cloudScore) {
        this.cloudScore = cloudScore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
