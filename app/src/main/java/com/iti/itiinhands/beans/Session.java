package com.iti.itiinhands.beans;

import java.util.Date;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Session {
    public enum SessionType{lab, lecture}

    private Date startTime;
    private String location;
    private Course course;
    private SessionType sessionType;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }
}
