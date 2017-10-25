package model;

public class ImageLabel {

    public static int COUNTER = 0;

    private int id;
    private String path;
    private String label;

    public ImageLabel()
    {
        this.id = ++COUNTER;
    }

    public ImageLabel(String path, String label) {
        this.id = ++COUNTER;

        this.path = path;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
