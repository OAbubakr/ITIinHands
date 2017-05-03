package com.iti.itiinhands.beans;

import java.util.HashMap;

/**
 * Created by Sandra on 5/3/2017.
 */

abstract class ITIan extends AppUser {
    private HashMap<String,String> professionalProfiles = new HashMap<>();
    private int trackId;
    private int intake;

    public HashMap<String, String> getProfessionalProfiles() {
        return professionalProfiles;
    }

    public void setProfessionalProfiles(HashMap<String, String> professionalProfiles) {
        this.professionalProfiles = professionalProfiles;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getIntake() {
        return intake;
    }

    public void setIntake(int intake) {
        this.intake = intake;
    }
}
