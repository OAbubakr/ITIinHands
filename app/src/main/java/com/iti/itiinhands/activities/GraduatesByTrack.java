package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graduates_by_track);
        intakeid = (Spinner) findViewById(R.id.intakeId);
        trackId = getIntent().getIntExtra("trackId",0);
        networkManager = NetworkManager.getInstance(this);
        myRef = this;
        networkManager.getIntakes(myRef);
        System.out.println("track id = "+ trackId);

        recyclerView = (RecyclerView) findViewById(R.id.GraduateList);

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
    }

    @Override
    public void onFailure() {

    }


    public String[] getintakesId (List<ProgramIntake> list){
        String[] arr = new String[list.size()];
        for(int i =0 ; i < list.size() ; i++){
            arr[i] = String.valueOf(list.get(i).getIntakeId());
        }
        return arr;
    }
}
