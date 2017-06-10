package com.iti.itiinhands.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;


public class AllJobPostsFragment extends Fragment implements NetworkResponse {

    private NetworkManager networkManager;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<JobVacancy> jobVacancies = new ArrayList<>();

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
        recyclerView =
                (RecyclerView) view.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar);
        spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#7F0000"), PorterDuff.Mode.SRC_IN);
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
    public void onResponse(Response response) {
        if (response.getStatus().equals(Response.SUCCESS)) {
            jobVacancies = DataSerializer.convert(response.getResponseData(),new TypeToken<ArrayList<JobVacancy>>(){}.getType());

//            jobVacancies = (ArrayList<JobVacancy>) response.getResponseData();
            adapter = new JobsAdapter(jobVacancies, getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);
            spinner.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {

        Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
        spinner.setVisibility(View.GONE);
    }
}
