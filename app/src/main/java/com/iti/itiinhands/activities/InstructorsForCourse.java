package com.iti.itiinhands.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.Course_InstrAdapter;
import com.iti.itiinhands.adapters.Instru_CoursesAdapter;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.Collections;
import java.util.List;

public class InstructorsForCourse extends AppCompatActivity implements NetworkResponse{
    NetworkManager networkManager;
    private NetworkResponse myRef;
    public RecyclerView SCourses_RV;
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors_for_course);
        this.setTitle("Instructors");
        networkManager = NetworkManager.getInstance(this);
        myRef = this;
        int id = getIntent().getIntExtra("courseId",0);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);
        if (networkManager.isOnline())
            networkManager.getInstructorCourses(myRef, id);
        else
            onFailure();
        SCourses_RV = (RecyclerView) findViewById(R.id.instructorlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        SCourses_RV.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onResponse(Response response) {
        if (response!=null&&this!=null && response.getStatus().equals(Response.SUCCESS)) {
            List<Instructor> list = DataSerializer.convert(response.getResponseData(), new TypeToken<List<Instructor>>() {
            }.getType());
            Log.i("courselist", "courselist");
            if (this != null) {
                Course_InstrAdapter course_instrAdapter = new Course_InstrAdapter(this,list);
                SCourses_RV.setAdapter(course_instrAdapter);
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
