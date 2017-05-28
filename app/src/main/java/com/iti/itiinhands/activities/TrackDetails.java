package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.InstructorsAdapter;
import com.iti.itiinhands.adapters.TrackCoursesAdapter;

import com.iti.itiinhands.beans.Instructor;
import com.iti.itiinhands.model.Course;
import com.iti.itiinhands.model.Track;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import java.util.ArrayList;

public class TrackDetails extends AppCompatActivity implements NetworkResponse{
    RecyclerView instructorsRecyclerView;
    RecyclerView.Adapter instructorsAdapter;
    RecyclerView.LayoutManager instructorsLayoutManager;

    RecyclerView coursesRecyclerView;
    RecyclerView.Adapter coursesAdapter;
    RecyclerView.LayoutManager coursesLayoutManager;
    NetworkManager networkManager;
    Track track;

    ArrayList<Course> courses= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        networkManager=NetworkManager.getInstance(getApplicationContext());

        track = (Track) getIntent().getSerializableExtra("trackObject");
        //////GETTING List of instructors////
        Instructor i1 = new Instructor();
        i1.setFirstName("Mahmoud");
        Instructor i2 = new Instructor();
        i2.setFirstName("Ghada");
        Instructor[] instructors = {i1, i2, i1, i2, i1, i2};
        ////////GETTING list of courses////
//        Course c1 = new Course();
//        c1.setCourseName("Java");
//        Course c2 = new Course();
//        c2.setCourseName("Android");
//        Course courses[] = {c1, c2, c1, c2, c1, c2, c1};

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

        //setting the adapter
        instructorsAdapter = new InstructorsAdapter(instructors);
        instructorsRecyclerView.setAdapter(instructorsAdapter);
        prepareCourses();

    }

    public void prepareCourses(){
        networkManager.getCoursesByTrack(this,track.getPlatformIntakeId());
    }

    @Override
    public void onResponse(Object response) {

        courses= (ArrayList<Course>) response;
        coursesAdapter = new TrackCoursesAdapter(courses);
        coursesRecyclerView.setAdapter(coursesAdapter);
    }

    @Override
    public void onFaliure() {

    }
}
