package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;

import com.iti.itiinhands.beans.StudentGrade;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Branches extends AppCompatActivity implements NetworkResponse{


    private ArrayList<Branch> branchesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BranchesAdapter branchesAdapter;

    private TextView branchViewTitle;
    private NetworkManager networkManager;
    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches);
        networkManager = NetworkManager.getInstance(getApplicationContext());
        branchViewTitle = (TextView) findViewById(R.id.branch_view_title);
        branchViewTitle.setText("ITI-BRANCHES");

        recyclerView = (RecyclerView) findViewById(R.id.branch_recycler_view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        prepareBranchData();
    }

    private void prepareBranchData(){
        networkManager.getBranches(this);
    }

    @Override
    public void onResponse(Response response) {

        if(response.getStatus().equals(Response.SUCCESS)){
            branchesList = DataSerializer.convert(response.getResponseData(),new TypeToken<ArrayList<Branch>>(){}.getType());

//            branchesList= (ArrayList<Branch>) response.getResponseData();
        branchesAdapter = new BranchesAdapter(branchesList, getApplicationContext(),flag);
        recyclerView.setAdapter(branchesAdapter);
        }
    }



    @Override
    public void onFailure() {

    }
}