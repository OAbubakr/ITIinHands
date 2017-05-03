package com.iti.itiinhands.beans;

import java.util.ArrayList;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Student extends ITIan {
    private ArrayList<StudentCourse> studentCourses = new ArrayList<>();

    public ArrayList<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(ArrayList<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }
}
