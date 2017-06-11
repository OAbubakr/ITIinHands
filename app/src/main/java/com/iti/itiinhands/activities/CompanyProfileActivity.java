package com.iti.itiinhands.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.iti.itiinhands.R;
import com.iti.itiinhands.fragments.CompanyProfileFragment;
import com.iti.itiinhands.fragments.ScheduleFragment;
import com.iti.itiinhands.model.Company;

public class CompanyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);

        Fragment fragment = new CompanyProfileFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("company",getIntent().getSerializableExtra("company"));
                    bundle.putInt("flag",1);
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame, fragment);
                    fragmentTransaction.commit();
    }



}
