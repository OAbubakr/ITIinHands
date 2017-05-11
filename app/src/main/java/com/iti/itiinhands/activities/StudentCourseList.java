package com.iti.itiinhands.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.beans.StudentCourse;

import java.util.ArrayList;
import java.util.List;

public class StudentCourseList extends AppCompatActivity {

    private List<StudentCourse> studentCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_list);



        final RecyclerView SCourses_RV = (RecyclerView) findViewById(R.id.rvStudentCourses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SCourses_RV.setLayoutManager(linearLayoutManager);
        CourseAdapter courseAdapter = new CourseAdapter(this,getCourses());

        SCourses_RV.setAdapter(courseAdapter);

    }


    public List<StudentCourse> getCourses(){
        List<StudentCourse> list_studentCourses = new ArrayList<>();

        StudentCourse c1 = new StudentCourse();
        c1.setCourseName("course1");
        c1.setCourseId(1);
        c1.setCourseComplete(false);
        c1.setCourseEvaluate(true);
        c1.setGrade(StudentCourse.Grade.A);
        c1.setGradeOnSystem(true);

        StudentCourse c2 = new StudentCourse();
        c2.setCourseName("course2");
        c2.setCourseId(2);
        c2.setCourseComplete(true);
        c2.setCourseEvaluate(true);
        c2.setGrade(StudentCourse.Grade.C);
        c2.setGradeOnSystem(false);

        StudentCourse c3 = new StudentCourse();
        c3.setCourseName("course3");
        c3.setCourseId(3);
        c3.setCourseComplete(true);
        c3.setCourseEvaluate(false);
        c3.setGrade(StudentCourse.Grade.F);
        c3.setGradeOnSystem(false);

        StudentCourse c4 = new StudentCourse();
        c4.setCourseName("course4");
        c4.setCourseId(4);
        c4.setCourseComplete(true);
        c4.setCourseEvaluate(true);
        c4.setGrade(StudentCourse.Grade.B);
        c4.setGradeOnSystem(true);

        list_studentCourses.add(c1);
        list_studentCourses.add(c2);
        list_studentCourses.add(c3);
        list_studentCourses.add(c4);

        return list_studentCourses;
    }
}
