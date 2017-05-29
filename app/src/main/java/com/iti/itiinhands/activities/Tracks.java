package com.iti.itiinhands.activities;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;
import com.iti.itiinhands.adapters.TracksAdapter;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Track;


import java.util.ArrayList;
import java.util.Arrays;

public class Tracks extends AppCompatActivity {

//    public int getFlag() {
//        return flag;
//    }

    private int flag = 0;

    Branch branch;
    TextView branchLocation;

    private ArrayList<Track> tracksList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TracksAdapter tracksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        branch = (Branch) getIntent().getSerializableExtra("branchObject");
        flag = getIntent().getIntExtra("flag",0);
        branchLocation = (TextView) findViewById(R.id.track_branch);
        branchLocation.setText(branch.getBranchName());
        tracksList=branch.getTracks();
        recyclerView = (RecyclerView)findViewById(R.id.track_recycler_view);
        tracksAdapter = new TracksAdapter(tracksList, getApplicationContext(),flag);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tracksAdapter);

    }

}
