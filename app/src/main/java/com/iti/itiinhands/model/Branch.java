package com.iti.itiinhands.model;

import com.iti.itiinhands.beans.Track;

import java.util.ArrayList;

/**
 * Created by home on 5/29/2017.
 */

public class Branch {

    int branchId;
    String branchName;
    String branchArabicName;
    ArrayList<Track> tracks = new ArrayList<>();


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
