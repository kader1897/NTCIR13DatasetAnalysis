package model;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class Location {

    public static int COUNTER = 0;

    private int id;
    private String name = null, link = null;
    private double latitude = 0;
    private double longitude = 0;


    public Location() {
        this.id = ++COUNTER;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getNameForQuery() {
        return name == null ? null : name.replaceAll("'","''");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
