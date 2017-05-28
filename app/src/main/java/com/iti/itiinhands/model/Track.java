package com.iti.itiinhands.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by salma on 24/05/2017.
 */

public class Track implements Serializable{

    private int trackId;
    private int platformIntakeId;
    private String trackName;
    private String platformName;
    private ArrayList<Course> courses = new ArrayList<>();

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getPlatformIntakeId() {
        return platformIntakeId;
    }

    public void setPlatformIntakeId(int platformIntakeId) {
        this.platformIntakeId = platformIntakeId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
