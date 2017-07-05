package iamdilipkumar.com.spacedig.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 20/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SimpleItemModel implements Parcelable {

    private String id;
    private String imageUrl;
    private String name, information;
    private String shortDescription;
    private String videoDownloadUrl;
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

    public SimpleItemModel(String pId, String name, String sDescription, String imgUrl, String information, int image) {
        this.id = pId;
        this.name = name;
        this.shortDescription = sDescription;
        this.imageUrl = imgUrl;
        this.information = information;
        this.imageRes = image;
    }

    public SimpleItemModel(String pId, String name, String sDescription, String imgUrl, String information, int image, String downloadUrl) {
        this.id = pId;
        this.name = name;
        this.shortDescription = sDescription;
        this.imageUrl = imgUrl;
        this.information = information;
        this.imageRes = image;
        this.videoDownloadUrl = downloadUrl;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoDownloadUrl() {
        return videoDownloadUrl;
    }

    protected SimpleItemModel(Parcel in) {
        id = in.readString();
        imageUrl = in.readString();
        name = in.readString();
        information = in.readString();
        shortDescription = in.readString();
        imageRes = in.readInt();
        videoDownloadUrl = in.readString();
    }

    public static final Creator<SimpleItemModel> CREATOR = new Creator<SimpleItemModel>() {
        @Override
        public SimpleItemModel createFromParcel(Parcel in) {
            return new SimpleItemModel(in);
        }

        @Override
        public SimpleItemModel[] newArray(int size) {
            return new SimpleItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeString(information);
        dest.writeString(shortDescription);
        dest.writeInt(imageRes);
        dest.writeString(videoDownloadUrl);
    }
}
