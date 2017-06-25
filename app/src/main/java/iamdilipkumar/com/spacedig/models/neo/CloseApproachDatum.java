package iamdilipkumar.com.spacedig.models.neo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 25/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class CloseApproachDatum {

    @SerializedName("close_approach_date")
    @Expose
    private String closeApproachDate;
    @SerializedName("epoch_date_close_approach")
    @Expose
    private Integer epochDateCloseApproach;
    @SerializedName("relative_velocity")
    @Expose
    private RelativeVelocity relativeVelocity;
    @SerializedName("miss_distance")
    @Expose
    private MissDistance missDistance;
    @SerializedName("orbiting_body")
    @Expose
    private String orbitingBody;

    public String getCloseApproachDate() {
        return closeApproachDate;
    }

    public void setCloseApproachDate(String closeApproachDate) {
        this.closeApproachDate = closeApproachDate;
    }

    public Integer getEpochDateCloseApproach() {
        return epochDateCloseApproach;
    }

    public void setEpochDateCloseApproach(Integer epochDateCloseApproach) {
        this.epochDateCloseApproach = epochDateCloseApproach;
    }

    public RelativeVelocity getRelativeVelocity() {
        return relativeVelocity;
    }

    public void setRelativeVelocity(RelativeVelocity relativeVelocity) {
        this.relativeVelocity = relativeVelocity;
    }

    public MissDistance getMissDistance() {
        return missDistance;
    }

    public void setMissDistance(MissDistance missDistance) {
        this.missDistance = missDistance;
    }

    public String getOrbitingBody() {
        return orbitingBody;
    }

    public void setOrbitingBody(String orbitingBody) {
        this.orbitingBody = orbitingBody;
    }

}
