package com.iti.itiinhands.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.GraduateBasicData;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.StudentProfileFragment;
import com.iti.itiinhands.utilities.DataSerializer;

public class CompanyGraduateProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_graduate_profile);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StudentProfileFragment fragment = new StudentProfileFragment();
        Bundle b = getIntent().getBundleExtra("bundle");
        GraduateBasicData graduateBasicData = (GraduateBasicData) getIntent().getSerializableExtra("graduate");
        UserData userData = DataSerializer.convert(getIntent().getSerializableExtra("graduate"),UserData.class);

    }
}
