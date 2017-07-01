package iamdilipkumar.com.spacedig.models.search;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 01/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class Item {

    @SerializedName("links")
    @Expose
    private List<Link_> links = null;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Link_> getLinks() {
        return links;
    }

    public void setLinks(List<Link_> links) {
        this.links = links;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
