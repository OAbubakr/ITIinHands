package com.iti.itiinhands.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.adapters.Instru_CoursesAdapter;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.List;

public class CoursesForInstructors extends AppCompatActivity implements NetworkResponse{
    NetworkManager networkManager;
    private NetworkResponse myRef;
    public RecyclerView SCourses_RV;
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_for_instructors);
        this.setTitle("Courses");
        networkManager = NetworkManager.getInstance(this);
        myRef = this;
        int id = getIntent().getIntExtra("instId",0);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);
        if (networkManager.isOnline())
            networkManager.getInstructorCourses(myRef, id);
        else
            onFailure();
        SCourses_RV = (RecyclerView) findViewById(R.id.courseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SCourses_RV.setLayoutManager(linearLayoutManager);
    }




    @Override
    public void onResponse(Response response) {
        if (response!=null&&this!=null && response.getStatus().equals(Response.SUCCESS)) {
            List<Course> list = DataSerializer.convert(response.getResponseData(), new TypeToken<List<Course>>() {
            }.getType());
            Log.i("courselist", "courselist");
            if (this != null) {
                Instru_CoursesAdapter courseAdapter = new Instru_CoursesAdapter(this ,list);
                SCourses_RV.setAdapter(courseAdapter);
            }
            spinner.setVisibility(View.GONE);
        } else onFailure();
    }

    @Override
    public void onFailure() {
        new NetworkUtilities().networkFailure(this);

        spinner.setVisibility(View.GONE);
    }
}
