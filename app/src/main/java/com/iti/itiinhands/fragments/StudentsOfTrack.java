package com.iti.itiinhands.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.AllStudentByTrackIdAdapter;
import com.iti.itiinhands.model.StudentDataByTrackId;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import java.util.ArrayList;

/**
 * Created by admin on 5/29/2017.
 */

public class StudentsOfTrack extends AppCompatActivity implements NetworkResponse {


    NetworkManager networkManager;
    ArrayList<StudentDataByTrackId> students = new ArrayList<>();
    private RecyclerView recyclerView;
    private AllStudentByTrackIdAdapter adapter;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.students_of_track_fragment);
            Intent intent = getIntent();
        id = intent.getIntExtra("trackId", 0);


        recyclerView = (RecyclerView) findViewById(R.id.studentsList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        networkManager = NetworkManager.getInstance(getApplicationContext());

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
    public void onResponse(Object response) {

        students = (ArrayList<StudentDataByTrackId>) response;
        adapter = new AllStudentByTrackIdAdapter(students,getApplicationContext());

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onFailure() {

    }
}
