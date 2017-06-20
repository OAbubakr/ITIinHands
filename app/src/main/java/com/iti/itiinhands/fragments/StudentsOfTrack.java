package com.iti.itiinhands.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.AllStudentByTrackIdAdapter;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.StudentDataByTrackId;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/29/2017.
 */

public class StudentsOfTrack extends AppCompatActivity implements NetworkResponse {


    NetworkManager networkManager;
    ArrayList<StudentDataByTrackId> students = new ArrayList<>();
    private RecyclerView recyclerView;
    private AllStudentByTrackIdAdapter adapter;
    int id;
    String trackName;
    ProgressBar spinner;
    private boolean isDownloading;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_of_track_fragment);

        isDownloading = true;
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (networkManager.isOnline() & !isDownloading) {
                    isDownloading = true;
                    networkManager.getAllStudentsByTrackId(StudentsOfTrack.this, id);
                } else {
                    onFailure();
                }
            }
        });


        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        Intent intent = getIntent();
        id = intent.getIntExtra("trackId", 0);
        trackName = intent.getStringExtra("tack name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(trackName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.studentsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        networkManager = NetworkManager.getInstance(getApplicationContext());
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);

        if (networkManager.isOnline())
            networkManager.getAllStudentsByTrackId(this, id);
        else
            onFailure();

    }


    @Override
    public void onResponse(Response response) {

        isDownloading = false;

        if (swipeContainer != null) {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }


        if (response != null && getApplicationContext() != null && response.getStatus().equals(Response.SUCCESS)) {
            students = DataSerializer.convert(response.getResponseData(), new TypeToken<ArrayList<StudentDataByTrackId>>() {
            }.getType());

            adapter = new AllStudentByTrackIdAdapter(students, getApplicationContext());

            recyclerView.setAdapter(adapter);
            spinner.setVisibility(View.GONE);
        } else
            onFailure();

    }

    @Override
    public void onFailure() {

        isDownloading = false;

        if (swipeContainer != null) {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }

        new NetworkUtilities().networkFailure(getApplicationContext());
        spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
