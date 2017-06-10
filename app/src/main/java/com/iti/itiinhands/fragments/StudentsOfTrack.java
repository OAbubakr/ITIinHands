package com.iti.itiinhands.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
    ProgressBar spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.students_of_track_fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Intent intent = getIntent();
        id = intent.getIntExtra("trackId", 0);


        recyclerView = (RecyclerView) findViewById(R.id.studentsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        networkManager = NetworkManager.getInstance(getApplicationContext());
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);

        networkManager.getAllStudentsByTrackId(this, id);


    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        Intent intent = getActivity().getIntent();
//
//        return inflater.inflate(R.layout.students_of_track_fragment, container, false);
//
//
//    }


//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//
//
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }


    @Override
    public void onResponse(Response response) {

        if (response.getStatus().equals(Response.SUCCESS)) {
            students = DataSerializer.convert(response.getResponseData(),new TypeToken<ArrayList<StudentDataByTrackId>>(){}.getType());

//            students = (ArrayList<StudentDataByTrackId>) response.getResponseData();
            adapter = new AllStudentByTrackIdAdapter(students, getApplicationContext());

            recyclerView.setAdapter(adapter);
            spinner.setVisibility(View.GONE);
        }


    }

    @Override
    public void onFailure() {

        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
        spinner.setVisibility(View.GONE);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
