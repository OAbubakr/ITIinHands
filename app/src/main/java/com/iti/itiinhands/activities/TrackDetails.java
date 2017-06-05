package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
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

    private ProgressBar spinner;
    ArrayList<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        networkManager = NetworkManager.getInstance(getApplicationContext());

        track = (Track) getIntent().getSerializableExtra("trackObject");


        instructorsRecyclerView = (RecyclerView) findViewById(R.id.instructorsRV);
        coursesRecyclerView = (RecyclerView) findViewById(R.id.coursesRV);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        instructorsRecyclerView.setHasFixedSize(true);
        coursesRecyclerView.setHasFixedSize(true);

        //setting the layout manager
        instructorsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        instructorsRecyclerView.setLayoutManager(instructorsLayoutManager);

        coursesLayoutManager = new GridLayoutManager(this, 3);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
        //setting the adapter

        prepareCourses();

    }

    public void prepareCourses() {
        networkManager.getCoursesByTrack(this, track.getPlatformIntakeId());
    }

    @Override
    public void onResponse(Response response) {
        if (response.getStatus().equals(Response.SUCCESS)) {
            courses = DataSerializer.convert(response.getResponseData(),new TypeToken<ArrayList<Course>>(){}.getType());

//            courses = (ArrayList<Course>) response.getResponseData();
            coursesAdapter = new TrackCoursesAdapter(courses);
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
            instructorsAdapter = new InstructorsAdapter(trackInstructors);
            instructorsRecyclerView.setAdapter(instructorsAdapter);
            spinner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {

    }


}
