package com.iti.itiinhands.model;

/**
 * Created by omari on 5/22/2017.
 */

public class SessionModel {
    private long sessionDate;
    private long id;
    private String courseName;
    private String roomName;
    private String instructorName;
    private String sessionPercentage;

    public String getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    private String sessionTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(long sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getSessionPercentage() {
        return sessionPercentage;
    }

    public void setSessionPercentage(String sessionPercentage) {
        this.sessionPercentage = sessionPercentage;
    }


}
