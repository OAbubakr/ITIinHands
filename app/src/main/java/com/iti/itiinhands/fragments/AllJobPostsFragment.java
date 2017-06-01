package com.iti.itiinhands.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;
import com.iti.itiinhands.adapters.JobsAdapter;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.JobVacancy;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import java.util.ArrayList;


public class AllJobPostsFragment extends Fragment implements NetworkResponse{

    private NetworkManager networkManager;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<JobVacancy> jobVacancies = new ArrayList<>();

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
        recyclerView =
                (RecyclerView) view.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        networkManager.getAllJobs(this);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onResponse(Object response) {
        jobVacancies= (ArrayList<JobVacancy>) response;
        adapter = new JobsAdapter(jobVacancies,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure() {

    }
}
