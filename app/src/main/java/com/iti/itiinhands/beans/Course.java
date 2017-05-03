package com.iti.itiinhands.beans;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Course {
    private int courseId;
    private String courseName;
    private String description;
    private Instructor mainInstructor;
    private ArrayList<Instructor> labAssistants = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instructor getMainInstructor() {
        return mainInstructor;
    }

    public void setMainInstructor(Instructor mainInstructor) {
        this.mainInstructor = mainInstructor;
    }

    public ArrayList<Instructor> getLabAssistants() {
        return labAssistants;
    }

    public void setLabAssistants(ArrayList<Instructor> labAssistants) {
        this.labAssistants = labAssistants;
    }
}

