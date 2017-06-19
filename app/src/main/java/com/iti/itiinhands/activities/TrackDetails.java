package com.iti.itiinhands.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.InstructorsAdapter;
import com.iti.itiinhands.adapters.TrackCoursesAdapter;

import com.iti.itiinhands.beans.Instructor;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.Track;
import com.iti.itiinhands.model.TrackInstructor;
import com.iti.itiinhands.model.schedule.SessionModel;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;

public class TrackDetails extends AppCompatActivity implements NetworkResponse {
    RecyclerView instructorsRecyclerView;
    RecyclerView.Adapter instructorsAdapter;
    RecyclerView.LayoutManager instructorsLayoutManager;

    RecyclerView coursesRecyclerView;
    RecyclerView.Adapter coursesAdapter;
    RecyclerView.LayoutManager coursesLayoutManager;
    NetworkManager networkManager;
    Track track;
    TextView noInstructorsTV;

    private ProgressBar spinner;
    ArrayList<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        track = (Track) getIntent().getSerializableExtra("trackObject");
        toolbar.setTitle(track.getTrackName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        networkManager = NetworkManager.getInstance(getApplicationContext());


        instructorsRecyclerView = (RecyclerView) findViewById(R.id.instructorsRV);
        coursesRecyclerView = (RecyclerView) findViewById(R.id.coursesRV);
        noInstructorsTV = (TextView) findViewById(R.id.noInstructorsTV);
        noInstructorsTV.setVisibility(View.GONE);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        instructorsRecyclerView.setHasFixedSize(true);
        coursesRecyclerView.setHasFixedSize(true);

        //setting the layout manager
        instructorsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        instructorsRecyclerView.setLayoutManager(instructorsLayoutManager);

        coursesLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);
        //setting the adapter

        if (networkManager.isOnline()) {
            prepareCourses();
        } else {
            new NetworkUtilities().networkFailure(getApplicationContext());
            spinner.setVisibility(View.GONE);
        }


    }

    public void prepareCourses() {
        networkManager.getCoursesByTrack(this, track.getPlatformIntakeId());
    }

    @Override
    public void onResponse(Response response) {
        if (response!=null&&getApplicationContext()!=null) {
            if (response.getStatus().equals(Response.SUCCESS)) {
                courses = DataSerializer.convert(response.getResponseData(), new TypeToken<ArrayList<Course>>() {
                }.getType());

//            courses = (ArrayList<Course>) response.getResponseData();
                coursesAdapter = new TrackCoursesAdapter(courses,this);
                coursesRecyclerView.setAdapter(coursesAdapter);
                ArrayList<TrackInstructor> trackInstructors = new ArrayList<>();
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    if (course.getTrackInstructors().size() != 0) {
                        for (int j = 0; j < course.getTrackInstructors().size(); j++) {
                            trackInstructors.add(course.getTrackInstructors().get(j));
                        }

                    }
                }
                instructorsAdapter = new InstructorsAdapter(trackInstructors,getApplicationContext());

                if(trackInstructors.size() == 0){
                    noInstructorsTV.setVisibility(View.VISIBLE);
                } else{
                    noInstructorsTV.setVisibility(View.GONE);
                }
//                instructorsAdapter = new InstructorsAdapter(trackInstructors);
                instructorsRecyclerView.setAdapter(instructorsAdapter);
                spinner.setVisibility(View.GONE);
            }
        } else {
            new NetworkUtilities().networkFailure(getApplicationContext());
            spinner.setVisibility(View.GONE);
        }

    }

    @Override
    public void onFailure() {
        new NetworkUtilities().networkFailure(getApplicationContext());
        spinner.setVisibility(View.GONE);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
