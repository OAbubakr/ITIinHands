package com.iti.itiinhands.beans;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Instructor extends AppUser {
    private String title;
    private String phone;
    private Track trackSupervision;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Track getTrackSupervision() {
        return trackSupervision;
    }

    public void setTrackSupervision(Track trackSupervision) {
        this.trackSupervision = trackSupervision;
    }
}
