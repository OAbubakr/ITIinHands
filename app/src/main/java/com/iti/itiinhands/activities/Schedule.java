package com.iti.itiinhands.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.iti.itiinhands.R;
import com.iti.itiinhands.fragments.ScheduleFragment;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fM.beginTransaction();
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        scheduleFragment.setArguments(getIntent().getBundleExtra("bundle"));
        fragTransaction.replace(R.id.frame,scheduleFragment);
        fragTransaction.commit();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
