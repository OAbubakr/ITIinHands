package com.iti.itiinhands.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.BranchesAdapter;
import com.iti.itiinhands.beans.Branch;
import com.iti.itiinhands.beans.Track;

import java.util.ArrayList;
import java.util.Arrays;


public class BranchesFragment extends Fragment {

    private ArrayList<Branch> branchesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private BranchesAdapter branchesAdapter;

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
        branchViewTitle = (TextView) view.findViewById(R.id.branch_view_title);
        branchViewTitle.setText("ITI-BRANCHES");

        recyclerView = (RecyclerView) view.findViewById(R.id.branch_recycler_view);

        branchesAdapter = new BranchesAdapter(branchesList, getActivity().getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(branchesAdapter);

        prepareBranchData();
        return view;
    }

    private void prepareBranchData(){
        Track t1 = new Track();
        t1.setTrackName("SD");

        Track t2 = new Track();
        t2.setTrackName("SA");

        Track t3 = new Track();
        t3.setTrackName("UI");

        Track t4 = new Track();
        t4.setTrackName("Mobile");

        Track t5 = new Track();
        t5.setTrackName("OS");

        ArrayList<Track> tracksList = new ArrayList<>();
        Track[] t = new Track[]{t1, t2, t3, t4, t5};
        tracksList.addAll(Arrays.asList(t));

        Branch b1 = new Branch();
        b1.setLocation("Alexandria");
        b1.setTracks(tracksList);

        Branch b2 = new Branch();
        b2.setLocation("Cairo");
        b2.setTracks(tracksList);

        Branch b3 = new Branch();
        b3.setLocation("Esmailia");
        b3.setTracks(tracksList);

        Branch b4 = new Branch();
        b4.setLocation("Elmansoura");
        b4.setTracks(tracksList);

        Branch[] b = new Branch[]{b1, b2, b3, b4};
        branchesList.addAll(Arrays.asList(b));
        branchesAdapter.notifyDataSetChanged();
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
