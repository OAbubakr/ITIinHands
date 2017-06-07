package com.iti.itiinhands.beans;

/**
 * Created by Sandra on 5/3/2017.
 */

public class InstructorEvaluation {

    private int courseId;
    private String courseName;
    private String eval;


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

    public String getEval() {
        return eval;
    }

    public void setEval(String eval) {
        this.eval = eval;
    }
}