package com.iti.itiinhands.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by salma on 24/05/2017.
 */

public class Branch implements Serializable{

    private  int branchId;
    private String branchName;
    private String branchArabicName;
    private ArrayList<Track> tracks = new ArrayList<>();

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchArabicName() {
        return branchArabicName;
    }

    public void setBranchArabicName(String branchArabicName) {
        this.branchArabicName = branchArabicName;
    }

    public ArrayList<Track> getTracks() {
        return tracks;
    }

    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }
}
