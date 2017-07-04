package iamdilipkumar.com.spacedig.models;

/**
 * Created on 04/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class MediaOptions {
    private String original;
    private String medium;
    private String small;

    public MediaOptions(String original, String medium, String small) {
        this.original = original;
        this.medium = medium;
        this.small = small;
    }

    public String getOriginal() {
        return original;
    }

    public String getMedium() {
        return medium;
    }

    public String getSmall() {
        return small;
    }
}
