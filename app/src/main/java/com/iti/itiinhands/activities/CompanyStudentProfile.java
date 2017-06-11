package com.iti.itiinhands.activities;

import android.net.Network;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.ScheduleFragment;
import com.iti.itiinhands.fragments.StudentProfileFragment;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.StudentDataByTrackId;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.DataSerializer;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.ArrayList;


public class CompanyStudentProfile extends AppCompatActivity implements NetworkResponse {

    NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        networkManager = NetworkManager.getInstance(getApplicationContext());
        networkManager.getUserProfileDataOther(this, 1, getIntent().getIntExtra("stuId", 0));


        setContentView(R.layout.activity_company_student_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public void onResponse(Response response) {

        if (response.getStatus().equals(Response.SUCCESS)) {
            FragmentManager fM = getSupportFragmentManager();
            FragmentTransaction fragTransaction = fM.beginTransaction();
            StudentProfileFragment scheduleFragment = new StudentProfileFragment();
            Bundle b = getIntent().getBundleExtra("bundle");


//        Response result = (Response) response;
//        LinkedTreeMap map = ((LinkedTreeMap) result.getResponseData());
            UserData data = DataSerializer.convert(response.getResponseData(),UserData.class);

//            UserData data = (UserData) response.getResponseData();
//                UserDataSerializer.deSerialize(new Gson().toJson(result.getResponseData()));
            b.putSerializable("student", data);
            scheduleFragment.setArguments(b);
            fragTransaction.replace(R.id.frame, scheduleFragment);
            fragTransaction.commit();
        }
    }

    @Override
    public void onFailure() {

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }
}
