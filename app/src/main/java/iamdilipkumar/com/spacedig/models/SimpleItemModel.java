package iamdilipkumar.com.spacedig.models;

/**
 * Created on 20/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SimpleItemModel {

    private String mName, mInformation;
    private int mImage;

    public SimpleItemModel(String name, String information, int image) {
        this.mName = name;
        this.mInformation = information;
        this.mImage = image;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmInformation() {
        return mInformation;
    }

    public void setmInformation(String mInformation) {
        this.mInformation = mInformation;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }
}
