package com.iti.itiinhands.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.iti.itiinhands.model.TrackInstructor;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String name= getIntent().getStringExtra("course name");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        setTitleColor(android.R.color.white);


       // this.setTitle("Instructors");
        networkManager = NetworkManager.getInstance(this);
        myRef = this;
        int id = getIntent().getIntExtra("courseId",0);
        Log.i("course id", String.valueOf(id));
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);
        if (networkManager.isOnline())
            networkManager.getCourseInstructors(myRef, id);
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
            List<TrackInstructor> list = DataSerializer.convert(response.getResponseData(), new TypeToken<List<TrackInstructor>>() {
            }.getType());
            Log.i("courselist", String.valueOf(list.size()));
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
