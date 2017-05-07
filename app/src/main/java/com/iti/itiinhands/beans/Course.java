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


    private boolean courseEvaluate; // to define if course is evaluated or not yet
    private boolean courseComplete; // to define if this course is taken or not
    private boolean gradeOnSystem; // to define if this grade is on system or not

    /*  get gradeOnSystem value */
    public boolean isGradeOnSystem() {
        return gradeOnSystem;
    }
    /*  set gradeOnSystem value */
    public void setGradeOnSystem(boolean gradeOnSystem) {
        this.gradeOnSystem = gradeOnSystem;
    }

    /*  get courseEvaluate value */
    public boolean isCourseEvaluate() {
        return courseEvaluate;
    }
    /*  set courseEvaluate value */
    public void setCourseEvaluate(boolean courseEvaluate) {
        this.courseEvaluate = courseEvaluate;
    }
    /*  get courseComplete value */
    public boolean isCourseComplete() {
        return courseComplete;
    }
    /*  set courseComplete value */
    public void setCourseComplete(boolean courseComplete) {
        this.courseComplete = courseComplete;
    }

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

