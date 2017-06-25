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
public class Neo {

    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("page")
    @Expose
    private Page page;
    @SerializedName("near_earth_objects")
    @Expose
    private List<NearEarthObject> nearEarthObjects = null;

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<NearEarthObject> getNearEarthObjects() {
        return nearEarthObjects;
    }

    public void setNearEarthObjects(List<NearEarthObject> nearEarthObjects) {
        this.nearEarthObjects = nearEarthObjects;
    }

}
