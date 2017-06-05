package com.iti.itiinhands.model;

import com.iti.itiinhands.model.Track;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by home on 5/29/2017.
 */

public class Branch implements Serializable {

    private int branchId;
    private String branchName;
    private String branchArabicName;
    private String address;
    private String phones;
    private String email;
    private boolean hasMaps;
    private ArrayList<Track> tracks = new ArrayList<>();

    public Branch(String branchName, String address, String phones, String email, int branchId, boolean hasMaps) {
        this.branchName = branchName;
        this.address = address;
        this.phones = phones;
        this.email = email;
        this.branchId = branchId;
        this.hasMaps = hasMaps;
    }

    public boolean isHasMaps() {
        return hasMaps;
    }

    public void setHasMaps(boolean hasMaps) {
        this.hasMaps = hasMaps;
    }

    public String getEmails() {
        return email;
    }

    public void setEmails(String emails) {
        this.email = emails;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
