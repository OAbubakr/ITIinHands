package com.iti.itiinhands.model;

import java.io.Serializable;

/**
 * Created by salma on 24/05/2017.
 */

public class Course implements Serializable{

    private int courseId;
    private String courseName;

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
}
