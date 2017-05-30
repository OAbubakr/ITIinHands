package com.iti.itiinhands.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Branch implements Serializable{
    private  int branchId;
    private String branchName;
    private String branchArabicName;

    private String location;
    private ArrayList<Track> tracks = new ArrayList<>();
    private ArrayList<String> mapImgUrl = new ArrayList<>();
    private ArrayList<Bus> buses = new ArrayList<>();

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    public ArrayList<String> getMapImgUrl() {
        return mapImgUrl;
    }

    public void setMapImgUrl(ArrayList<String> mapImgUrl) {
        this.mapImgUrl = mapImgUrl;
    }

    public ArrayList<Bus> getBuses() {
        return buses;
    }

    public void setBuses(ArrayList<Bus> buses) {
        this.buses = buses;
    }
}

