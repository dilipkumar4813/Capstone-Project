package iamdilipkumar.com.spacedig.models.epic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 22/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class AttitudeQuaternions {

    @SerializedName("q0")
    @Expose
    private Double q0;
    @SerializedName("q1")
    @Expose
    private Double q1;
    @SerializedName("q2")
    @Expose
    private Double q2;
    @SerializedName("q3")
    @Expose
    private Double q3;

    public Double getQ0() {
        return q0;
    }

    public void setQ0(Double q0) {
        this.q0 = q0;
    }

    public Double getQ1() {
        return q1;
    }

    public void setQ1(Double q1) {
        this.q1 = q1;
    }

    public Double getQ2() {
        return q2;
    }

    public void setQ2(Double q2) {
        this.q2 = q2;
    }

    public Double getQ3() {
        return q3;
    }

    public void setQ3(Double q3) {
        this.q3 = q3;
    }

}
