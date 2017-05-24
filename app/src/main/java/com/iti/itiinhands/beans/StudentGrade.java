package com.iti.itiinhands.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentGrade {

    @SerializedName("courseName")
    @Expose
    private String courseName;
    @SerializedName("courseID")
    @Expose
    private Integer courseID;
    @SerializedName("gradeName")
    @Expose
    private String gradeName;
    @SerializedName("finishedCourse")
    @Expose
    private Integer finishedCourse;
    @SerializedName("evaluatedCourse")
    @Expose
    private Integer evaluatedCourse;
    @SerializedName("courseManualID")
    @Expose
    private Integer courseManualID;
    @SerializedName("courseDescription")
    @Expose
    private String courseDescription;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getFinishedCourse() {
        return finishedCourse;
    }

    public void setFinishedCourse(Integer finishedCourse) {
        this.finishedCourse = finishedCourse;
    }

    public Integer getEvaluatedCourse() {
        return evaluatedCourse;
    }

    public void setEvaluatedCourse(Integer evaluatedCourse) {
        this.evaluatedCourse = evaluatedCourse;
    }

    public Integer getCourseManualID() {
        return courseManualID;
    }

    public void setCourseManualID(Integer courseManualID) {
        this.courseManualID = courseManualID;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

}
