package com.iti.itiinhands.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import java.util.ArrayList;
import java.util.Arrays;


public class BranchesFragment extends Fragment implements NetworkResponse {

    private ArrayList<Branch> branchesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BranchesAdapter branchesAdapter;
    private NetworkManager networkManager;
    private TextView branchViewTitle;

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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_branches, container, false);
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        branchViewTitle = (TextView) view.findViewById(R.id.branch_view_title);
        branchViewTitle.setText("ITI-BRANCHES");

        recyclerView = (RecyclerView) view.findViewById(R.id.branch_recycler_view);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        prepareBranchData();
        return view;
    }

    private void prepareBranchData(){
        Log.i("network","yamosahel");
        networkManager.getBranches(this);
    }



    public void onButtonPressed(Uri uri) {

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
        branchesList= (ArrayList<Branch>) response;
        branchesAdapter = new BranchesAdapter(branchesList, getActivity().getApplicationContext());
        recyclerView.setAdapter(branchesAdapter);
    }

    @Override
    public void onFaliure() {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
