package model;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class Log {

    public static int COUNTER = 0;

    protected int id;
    protected String time = null;
    protected String content = null;   // Food for Food-Logs, Drink for Drink-Logs, Mood for Health-Logs

    public Log() {
        this.id = ++Log.COUNTER;
    }

    public Log(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getContentForQuery() {
        return content == null ? null : content.replaceAll("'","''");
    }

    public void setContent(String content) {
        this.content = content;
    }
}
