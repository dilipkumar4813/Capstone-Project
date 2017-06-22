package iamdilipkumar.com.spacedig.models;

/**
 * Created on 20/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SimpleItemModel {

    private String id;
    private String imageUrl;
    private String name, information;
    private int imageRes;

    public SimpleItemModel(String name, String information, int image) {
        this.name = name;
        this.information = information;
        this.imageRes = image;
    }

    public SimpleItemModel(String pId, String imgUrl, String name, String information, int image) {
        this.id = pId;
        this.imageUrl = imgUrl;
        this.name = name;
        this.information = information;
        this.imageRes = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
