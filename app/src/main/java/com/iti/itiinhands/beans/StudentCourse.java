package com.iti.itiinhands.beans;

/**
 * Created by Sandra on 5/3/2017.
 */

public class StudentCourse extends Course {
    public enum Grade {A, B, C, D, F};
    private Grade grade;

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
