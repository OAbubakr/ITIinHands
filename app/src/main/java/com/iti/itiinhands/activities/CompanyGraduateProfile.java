package com.iti.itiinhands.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.GraduateBasicData;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.StudentProfileFragment;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

public class CompanyGraduateProfile extends AppCompatActivity implements NetworkResponse {

    NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_graduate_profile);
        System.out.println("*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A*A");
        networkManager = NetworkManager.getInstance(getApplicationContext());
        networkManager.getUserProfileDataOther(this, 4, getIntent().getIntExtra("graduateId", 0));


        setContentView(R.layout.activity_company_student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        StudentProfileFragment fragment = new StudentProfileFragment();
//        Bundle b = getIntent().getBundleExtra("bundle");
//        GraduateBasicData graduateBasicData = (GraduateBasicData) getIntent().getSerializableExtra("graduate");
//        UserData userData = DataSerializer.convert(getIntent().getSerializableExtra("graduate"),UserData.class);

    }


    @Override
    public void onResponse(Response response) {

        if (response.getStatus().equals(Response.SUCCESS)) {
            FragmentManager fM = getSupportFragmentManager();
            FragmentTransaction fragTransaction = fM.beginTransaction();
            StudentProfileFragment scheduleFragment = new StudentProfileFragment();
            Bundle b = getIntent().getBundleExtra("bundle");
            UserData data = DataSerializer.convert(response.getResponseData(), UserData.class);

            b.putSerializable("student", data);
            scheduleFragment.setArguments(b);
            fragTransaction.replace(R.id.frame, scheduleFragment);
            fragTransaction.commit();
        }
    }

    @Override
    public void onFailure() {
        new NetworkUtilities().networkFailure(getApplicationContext());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
