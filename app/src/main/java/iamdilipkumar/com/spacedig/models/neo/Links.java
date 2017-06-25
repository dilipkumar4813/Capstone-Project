package iamdilipkumar.com.spacedig.models.neo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 25/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class Links {

    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("self")
    @Expose
    private String self;

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

}
