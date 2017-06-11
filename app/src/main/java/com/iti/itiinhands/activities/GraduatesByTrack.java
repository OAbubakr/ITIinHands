package com.iti.itiinhands.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.adapters.GraduateAdapter;
import com.iti.itiinhands.adapters.TracksAdapter;
import com.iti.itiinhands.beans.GraduateBasicData;
import com.iti.itiinhands.beans.ProgramIntake;
import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.Track;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.DataSerializer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GraduatesByTrack extends AppCompatActivity implements NetworkResponse {

    Branch branch;
    TextView branchLocation;
//    private ArrayList<GraduateBasicData> graduateBasicDatas = new ArrayList<>();
    private RecyclerView recyclerView;
    private GraduateAdapter graduateAdapter;
    Spinner intakeid;
    NetworkManager networkManager;
    private NetworkResponse myRef;
    private int flag = 0;
    private int trackId;
    String trackName;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduates_by_track);
        intakeid = (Spinner) findViewById(R.id.intakeId);
        trackId = getIntent().getIntExtra("trackId",0);
        trackName = getIntent().getStringExtra("tack name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(trackName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        networkManager = NetworkManager.getInstance(this);
        myRef = this;
        networkManager.getIntakes(myRef);
        System.out.println("track id = "+ trackId);

        recyclerView = (RecyclerView) findViewById(R.id.GraduateList);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        intakeid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(Integer.parseInt(intakeid.getSelectedItem().toString()) +"  "+trackId);
                networkManager.getAllGraduatesByTracId(myRef,Integer.parseInt(intakeid.getSelectedItem().toString()),trackId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onResponse(Response response) {

        if (flag == 0) {
            System.out.println("*H*H*H*H*H*H*H*H*");
            if (response.getStatus().equals(Response.SUCCESS)) {
                System.out.println("*H*H*H*H*H*H*H*H*");
                List<ProgramIntake> list = DataSerializer.convert(response.getResponseData(), new TypeToken<List<ProgramIntake>>() {
                }.getType());
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,getintakesId(list));
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                intakeid.setAdapter(dataAdapter);
                System.out.println("MNMNMNNMNMNMNMNMNMNMNMNMNMNMNMN");
                flag=1;
            }
        }else if(flag == 1){
            if(response.getStatus().equals(Response.SUCCESS)){
                List<GraduateBasicData> graduateBasicDatas = DataSerializer.convert(response.getResponseData(), new TypeToken<List<GraduateBasicData>>() {
                }.getType());
                graduateAdapter = new GraduateAdapter(getApplicationContext(), graduateBasicDatas);
                recyclerView.setAdapter(graduateAdapter);
            }
        }

        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onFailure() {
        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
        spinner.setVisibility(View.GONE);
    }


    public String[] getintakesId (List<ProgramIntake> list){
        String[] arr = new String[list.size()];
        for(int i =0 ; i < list.size() ; i++){
            arr[i] = String.valueOf(list.get(i).getIntakeId());
        }
        return arr;
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
