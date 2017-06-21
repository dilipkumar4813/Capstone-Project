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
public class MarsRover {

    @SerializedName("photos")
    @Expose
    private List<MarsRoverPhoto> photos = null;

    public List<MarsRoverPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MarsRoverPhoto> photos) {
        this.photos = photos;
    }

}
