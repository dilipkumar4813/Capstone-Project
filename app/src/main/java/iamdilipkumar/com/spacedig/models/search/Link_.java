package iamdilipkumar.com.spacedig.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 01/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class Link_ {

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("rel")
    @Expose
    private String rel;
    @SerializedName("render")
    @Expose
    private String render;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getRender() {
        return render;
    }

    public void setRender(String render) {
        this.render = render;
    }

}
