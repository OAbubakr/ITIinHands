package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;
import com.iti.itiinhands.adapters.RecyclerViewAdapter;
import com.iti.itiinhands.adapters.TracksAdapter;
import com.iti.itiinhands.beans.Branch;
import com.iti.itiinhands.beans.Track;

import java.util.ArrayList;
import java.util.Arrays;

public class Tracks extends AppCompatActivity {

    Branch branch;
    TextView branchLocation;

//    private ArrayList<Track> tracksList = new ArrayList<>();
    private ArrayList<Object> tracksList = new ArrayList<>();
    private RecyclerView recyclerView;
//    private TracksAdapter tracksAdapter;
    private RecyclerViewAdapter tracksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        branch = (Branch) getIntent().getSerializableExtra("branchObject");
        branchLocation = (TextView) findViewById(R.id.track_branch);
        branchLocation.setText(branch.getLocation());

        recyclerView = (RecyclerView) findViewById(R.id.track_recycler_view);

//        tracksAdapter = new TracksAdapter(tracksList);
        tracksAdapter = new RecyclerViewAdapter(tracksList, 1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tracksAdapter);

        prepareTrackData();
    }

    private void prepareTrackData(){
//        tracksList = branch.getTracks();
//        tracksAdapter.notifyDataSetChanged();

        Track t1 = new Track();
        t1.setTrackName("SD");

        Track t2 = new Track();
        t2.setTrackName("SA");

        Track t3 = new Track();
        t3.setTrackName("UI");

        Track t4 = new Track();
        t4.setTrackName("Mobile");

        Track t5 = new Track();
        t5.setTrackName("OS");

        Track[] t = new Track[]{t1, t2, t3, t4, t5};
        tracksList.addAll(Arrays.asList(t));
        tracksAdapter.notifyDataSetChanged();
    }
}
