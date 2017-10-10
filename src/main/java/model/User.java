package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kader.belli on 21.08.2017.
 */
public class User {

    public static int COUNTER = 0;

    private int id;
    private String userId = null;
    private List<Day> days;

    public User() {
        this.id = ++COUNTER;
        days = new ArrayList<>();
    }

    public User(String userId) {
        this.id = ++COUNTER;
        this.userId = userId;
        this.days = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    public void addDay(Day day)
    {
        this.days.add(day);
    }
}
