package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.beans.StudentCourse;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import java.util.List;

public class StudentCourseList extends AppCompatActivity implements NetworkResponse{

    private List<StudentCourse> studentCourses;
    NetworkManager networkManager;
    private NetworkResponse myRef;
    public RecyclerView SCourses_RV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_list);
        networkManager = NetworkManager.getInstance(getApplicationContext());
        myRef= this;
        networkManager.getStudentsGrades(myRef,6761);
        SCourses_RV  = (RecyclerView) findViewById(R.id.rvStudentCourses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SCourses_RV.setLayoutManager(linearLayoutManager);


    }

    @Override
    public void onResponse(Object object) {
        List<StudentGrade> list = (List<StudentGrade>) object;
        CourseAdapter courseAdapter = new CourseAdapter(this, list);
        SCourses_RV.setAdapter(courseAdapter);
    }

    @Override
    public void onFailure() {

    }
}
