package com.iti.itiinhands.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CompaniesListAdapter;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.CompaniesProfiles;
import com.iti.itiinhands.model.Company;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud on 6/6/2017.
 */

public class CompaniesFragment extends Fragment implements NetworkResponse {

    private RecyclerView companiesLv;
    private List<Company> companiesList = new ArrayList<>();
    private ProgressBar spinner;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.companies_list, container, false);
        companiesLv =(RecyclerView) view.findViewById(R.id.companiesLvId);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        companiesLv.setLayoutManager(mLayoutManager);
        companiesLv.setItemAnimator(new DefaultItemAnimator());

        getActivity().setTitle("Companies profiles");


        spinner = (ProgressBar) view.findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);

        if( NetworkManager.getInstance(getActivity().getApplicationContext()).isOnline())
        NetworkManager.getInstance(getActivity().getApplicationContext()).getAllCompaniesData(this);
        else
            onFailure();


        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response!=null&&getActivity()!=null && response.getStatus().equals(Response.SUCCESS)) {
            CompaniesProfiles data = DataSerializer.convert(response.getResponseData(),CompaniesProfiles.class) ;
            if(data != null){
                companiesList = data.getCompanies();
                CompaniesListAdapter companiesListAdapter = new CompaniesListAdapter(getContext(),companiesList);
                companiesLv.setAdapter(companiesListAdapter);
                spinner.setVisibility(View.GONE);
            }
        }
        else
            onFailure();
    }

    @Override
    public void onFailure() {
        new NetworkUtilities().networkFailure( getActivity().getApplicationContext());
        spinner.setVisibility(View.GONE);
    }
}
