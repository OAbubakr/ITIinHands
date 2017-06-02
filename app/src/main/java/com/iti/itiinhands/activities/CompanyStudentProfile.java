package com.iti.itiinhands.activities;

import android.net.Network;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.iti.itiinhands.R;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.ScheduleFragment;
import com.iti.itiinhands.fragments.StudentProfileFragment;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.UserDataSerializer;


public class CompanyStudentProfile extends AppCompatActivity implements NetworkResponse {

    NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkManager = NetworkManager.getInstance(getApplicationContext());
        networkManager.getStudentProfileData(this, 1, getIntent().getIntExtra("stuId", 0));


        setContentView(R.layout.activity_company_student_profile);

    }

    @Override
    public void onResponse(Object response) {
        FragmentManager fM = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fM.beginTransaction();
        StudentProfileFragment scheduleFragment = new StudentProfileFragment();
        Bundle b = getIntent().getBundleExtra("bundle");


        Response result = (Response) response;
//        LinkedTreeMap map = ((LinkedTreeMap) result.getResponseData());
        UserData data = UserDataSerializer.deSerialize(new Gson().toJson(result.getResponseData()));
        b.putSerializable("student",data);
        scheduleFragment.setArguments(b);
        fragTransaction.replace(R.id.frame, scheduleFragment);
        fragTransaction.commit();

    }

    @Override
    public void onFailure() {

    }
}
