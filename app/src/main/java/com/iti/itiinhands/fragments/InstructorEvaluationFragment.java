package com.iti.itiinhands.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.InstructorEvaluationAdapter;
import com.iti.itiinhands.beans.InstructorEvaluation;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import java.util.ArrayList;


public class InstructorEvaluationFragment extends Fragment implements NetworkResponse {

    private ArrayList<InstructorEvaluation> instEvalList = new ArrayList<>();
    private RecyclerView recyclerView;
    private InstructorEvaluationAdapter instEvalAdapter;
    private NetworkManager networkManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_instructor_evaluation, container, false);
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());

        recyclerView =(RecyclerView) view.findViewById(R.id.inst_eval_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareInstEvalData();
        return view;
    }

    private void prepareInstEvalData(){

        SharedPreferences data = getActivity().getSharedPreferences("userData", 0);
        int instId = data.getInt("userId", 0);

        if (networkManager.isOnline()){
            networkManager.getInstructorEvaluation(this, 2268);
        }
    }

    @Override
    public void onResponse(Object response) {
        instEvalList = (ArrayList<InstructorEvaluation>) response;
        instEvalAdapter = new InstructorEvaluationAdapter(instEvalList, getActivity().getApplicationContext());
        recyclerView.setAdapter(instEvalAdapter);
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
    }
}
