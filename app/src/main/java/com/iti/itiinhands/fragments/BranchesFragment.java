package com.iti.itiinhands.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;


public class BranchesFragment extends Fragment implements NetworkResponse {

    private ArrayList<Branch> branchesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BranchesAdapter branchesAdapter;
    private NetworkManager networkManager;
    private int flag = 0;
    private ProgressBar spinner;
    private SwipeRefreshLayout swipeContainer;
    private boolean isDownloading;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public BranchesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_branches, container, false);
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());

        isDownloading = true;
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (networkManager.isOnline() & !isDownloading) {
                    isDownloading = true;
                    prepareBranchData();
                } else
                    onFailure();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        if (flag != 2)
            getActivity().setTitle("Branches");

        recyclerView = (RecyclerView) view.findViewById(R.id.branch_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        spinner = (ProgressBar) view.findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);

        if (networkManager.isOnline()) {
            prepareBranchData();
        } else {
            new NetworkUtilities().networkFailure(getContext());
            spinner.setVisibility(View.GONE);
        }

        return view;
    }

    private void prepareBranchData() {
    //    spinner.setVisibility(View.VISIBLE);
        networkManager.getBranches(this);
    }

    @Override
    public void onResponse(Response response) {
        isDownloading = false;
        if (swipeContainer != null) {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }


        if (response != null && getActivity() != null) {
            if (response.getStatus().equals(Response.SUCCESS)) {
                branchesList = DataSerializer.convert(response.getResponseData(), new TypeToken<ArrayList<Branch>>() {
                }.getType());

//            branchesList = (ArrayList<Branch>) response.getResponseData();
                branchesAdapter = new BranchesAdapter(branchesList, getActivity().getApplicationContext(), flag);
                recyclerView.setAdapter(branchesAdapter);
                spinner.setVisibility(View.GONE);
            } else onFailure();
        } else {
            onFailure();
        }
    }

    @Override
    public void onFailure() {
        isDownloading = false;

        new NetworkUtilities().networkFailure(getContext());
        spinner.setVisibility(View.GONE);

        if (swipeContainer != null) {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }

    }

}
