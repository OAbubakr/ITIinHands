package com.iti.itiinhands.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.model.Company;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahmoud on 6/6/2017.
 */

public class CompaniesFragment extends Fragment implements NetworkResponse {

    private ListView companiesLv;
    private List<Company> companiesList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkManager.getInstance(getActivity().getApplicationContext()).getAllCompaniesData(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.companies_list, container, false);
        companiesLv =(ListView) view.findViewById(R.id.companiesLvId);

        return view;
    }

    @Override
    public void onResponse(Response response) {

    }

    @Override
    public void onFailure() {

    }
}
