package com.iti.itiinhands.activities;

import android.app.ActionBar;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.Window;
import android.util.Log;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;
import com.iti.itiinhands.adapters.TracksAdapter;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Track;


import java.util.ArrayList;
import java.util.Arrays;

public class Tracks extends AppCompatActivity {

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        branch = (Branch) getIntent().getSerializableExtra("branchObject");
        toolbar.setTitle(branch.getBranchName());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        flag = getIntent().getIntExtra("flag",0);
//        branchLocation = (TextView) findViewById(R.id.track_branch);
//        branchLocation.setText(branch.getBranchName());
        tracksList=branch.getTracks();

        Log.i("before", String.valueOf(tracksList.size()));
        for (int i =0 ; i<tracksList.size() ; i++){
            Track track = tracksList.get(i);
            if (track.getTrackName().equals("")){
                tracksList.remove(track);
                i--;
            }

        }
        Log.i("after", String.valueOf(tracksList.size()));

        recyclerView = (RecyclerView)findViewById(R.id.track_recycler_view);
        tracksAdapter = new TracksAdapter(tracksList, getApplicationContext(),flag);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tracksAdapter);

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

}
