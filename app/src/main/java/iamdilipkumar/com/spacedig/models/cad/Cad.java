package iamdilipkumar.com.spacedig.models.cad;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created on 02/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class Cad {

    @SerializedName("signature")
    @Expose
    private Signature signature;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("fields")
    @Expose
    private List<String> fields = null;
    @SerializedName("data")
    @Expose
    private List<List<String>> data = null;

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

}
