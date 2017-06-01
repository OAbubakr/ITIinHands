package com.iti.itiinhands.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by salma on 24/05/2017.
 */

public class Course implements Serializable{

    private int courseId;
    private String courseName;
    ArrayList<TrackInstructor> trackInstructors = new ArrayList<>();

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<TrackInstructor> getTrackInstructors() {
        return trackInstructors;
    }

    public void setTrackInstructors(ArrayList<TrackInstructor> trackInstructors) {
        this.trackInstructors = trackInstructors;
    }
}
