package iamdilipkumar.com.spacedig.models.epic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 22/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class Epic {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("centroid_coordinates")
    @Expose
    private CentroidCoordinates centroidCoordinates;
    @SerializedName("dscovr_j2000_position")
    @Expose
    private DscovrJ2000Position dscovrJ2000Position;
    @SerializedName("lunar_j2000_position")
    @Expose
    private LunarJ2000Position lunarJ2000Position;
    @SerializedName("sun_j2000_position")
    @Expose
    private SunJ2000Position sunJ2000Position;
    @SerializedName("attitude_quaternions")
    @Expose
    private AttitudeQuaternions attitudeQuaternions;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("coords")
    @Expose
    private String coords;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public CentroidCoordinates getCentroidCoordinates() {
        return centroidCoordinates;
    }

    public void setCentroidCoordinates(CentroidCoordinates centroidCoordinates) {
        this.centroidCoordinates = centroidCoordinates;
    }

    public DscovrJ2000Position getDscovrJ2000Position() {
        return dscovrJ2000Position;
    }

    public void setDscovrJ2000Position(DscovrJ2000Position dscovrJ2000Position) {
        this.dscovrJ2000Position = dscovrJ2000Position;
    }

    public LunarJ2000Position getLunarJ2000Position() {
        return lunarJ2000Position;
    }

    public void setLunarJ2000Position(LunarJ2000Position lunarJ2000Position) {
        this.lunarJ2000Position = lunarJ2000Position;
    }

    public SunJ2000Position getSunJ2000Position() {
        return sunJ2000Position;
    }

    public void setSunJ2000Position(SunJ2000Position sunJ2000Position) {
        this.sunJ2000Position = sunJ2000Position;
    }

    public AttitudeQuaternions getAttitudeQuaternions() {
        return attitudeQuaternions;
    }

    public void setAttitudeQuaternions(AttitudeQuaternions attitudeQuaternions) {
        this.attitudeQuaternions = attitudeQuaternions;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

}
