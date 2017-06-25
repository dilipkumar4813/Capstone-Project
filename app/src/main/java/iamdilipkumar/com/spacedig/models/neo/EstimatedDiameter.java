package iamdilipkumar.com.spacedig.models.neo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 25/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class EstimatedDiameter {

    @SerializedName("kilometers")
    @Expose
    private Kilometers kilometers;
    @SerializedName("meters")
    @Expose
    private Meters meters;
    @SerializedName("miles")
    @Expose
    private Miles miles;
    @SerializedName("feet")
    @Expose
    private Feet feet;

    public Kilometers getKilometers() {
        return kilometers;
    }

    public void setKilometers(Kilometers kilometers) {
        this.kilometers = kilometers;
    }

    public Meters getMeters() {
        return meters;
    }

    public void setMeters(Meters meters) {
        this.meters = meters;
    }

    public Miles getMiles() {
        return miles;
    }

    public void setMiles(Miles miles) {
        this.miles = miles;
    }

    public Feet getFeet() {
        return feet;
    }

    public void setFeet(Feet feet) {
        this.feet = feet;
    }

}
