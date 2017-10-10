package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class Minute {

    public static int COUNTER = 0;

    private int id = 0;
    private Location location = null;
    private String activity = null;
    private BodyMetrics bodyMetrics = null;
    private List<Image> images;
    private List<PhoneImage> phoneImages;
    private Music music = null;

    public Minute() {
        COUNTER++;
        this.images = new ArrayList<>();
        this.phoneImages = new ArrayList<>();
    }

    public Minute(int id) {
        this.id = id;
        this.images = new ArrayList<>();
        this.phoneImages = new ArrayList<>();
        COUNTER++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public BodyMetrics getBodyMetrics() {
        return bodyMetrics;
    }

    public void setBodyMetrics(BodyMetrics bodyMetrics) {
        this.bodyMetrics = bodyMetrics;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image, boolean isPhoneImage)
    {
        if(isPhoneImage)
            this.phoneImages.add((PhoneImage) image);
        else
            this.images.add(image);
    }

    public List<PhoneImage> getPhoneImages() {
        return phoneImages;
    }

    public void setPhoneImages(List<PhoneImage> phoneImages) {
        this.phoneImages = phoneImages;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
