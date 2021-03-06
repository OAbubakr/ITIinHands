package com.iti.itiinhands.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;
import com.iti.itiinhands.adapters.JobsAdapter;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.JobVacancy;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;


public class AllJobPostsFragment extends Fragment implements NetworkResponse {

    private NetworkManager networkManager;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<JobVacancy> jobVacancies = new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;
    private boolean isDownloading;
    ProgressBar spinner;

    public AllJobPostsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_job_posts, container, false);

        isDownloading = true;
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (networkManager.isOnline() & !isDownloading) {
                    isDownloading = true;
                    networkManager.getAllJobs(AllJobPostsFragment.this);
                } else {
                    onFailure();
                }
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        recyclerView =
                (RecyclerView) view.findViewById(R.id.recycler_view);

        getActivity().setTitle("Job posts");

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);
        if (networkManager.isOnline()) {
            networkManager.getAllJobs(this);
        } else {
            onFailure();
        }

        return view;
    }


    @Override
    public void onResponse(Response response) {
        isDownloading = false;

        if(swipeContainer != null) {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }

        if (response!=null&&getActivity()!=null&&response.getStatus().equals(Response.SUCCESS)) {

                jobVacancies = DataSerializer.convert(response.getResponseData(), new TypeToken<ArrayList<JobVacancy>>() {
                }.getType());

            ArrayList<JobVacancy> jobs = new ArrayList<>();

            for (int i =jobVacancies.size()-1 ; i>=0;i--){

                jobs.add(jobVacancies.get(i));

            }

            adapter = new JobsAdapter(jobs, getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);
            spinner.setVisibility(View.GONE);
        }
            else onFailure();

    }

    @Override
    public void onFailure() {
        isDownloading = false;

        if(swipeContainer != null) {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }

        new NetworkUtilities().networkFailure(getContext());
        spinner.setVisibility(View.GONE);
    }
}
