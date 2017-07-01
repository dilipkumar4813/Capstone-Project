package iamdilipkumar.com.spacedig.models.cad;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 01/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class Collection {

    @SerializedName("links")
    @Expose
    private List<Link> links = null;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
