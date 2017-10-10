package model;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class Image {

    public static int COUNTER = 0;

    private int id;
    private String imageId = null;
    private String path = null;

    public Image() {
        this.id = ++COUNTER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
