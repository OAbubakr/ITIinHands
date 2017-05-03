package com.iti.itiinhands.beans;

import java.util.ArrayList;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Track {
    private String trackId;
    private String trackName;
    private Instructor trackSupervisor;
    private Branch trackBranch;
    private ArrayList<Student> trackStudents = new ArrayList<>();
    private ArrayList<Instructor> trackInstructors = new ArrayList<>();
    private ArrayList<Course> courseList = new ArrayList<>();
    private String description;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public Instructor getTrackSupervisor() {
        return trackSupervisor;
    }

    public void setTrackSupervisor(Instructor trackSupervisor) {
        this.trackSupervisor = trackSupervisor;
    }

    public Branch getTrackBranch() {
        return trackBranch;
    }

    public void setTrackBranch(Branch trackBranch) {
        this.trackBranch = trackBranch;
    }

    public ArrayList<Student> getTrackStudents() {
        return trackStudents;
    }

    public void setTrackStudents(ArrayList<Student> trackStudents) {
        this.trackStudents = trackStudents;
    }

    public ArrayList<Instructor> getTrackInstructors() {
        return trackInstructors;
    }

    public void setTrackInstructors(ArrayList<Instructor> trackInstructors) {
        this.trackInstructors = trackInstructors;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
