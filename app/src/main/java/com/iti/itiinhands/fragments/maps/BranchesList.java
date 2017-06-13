package com.iti.itiinhands.fragments.maps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.maps.MapsAdapter;
import com.iti.itiinhands.model.Branch;

import java.util.ArrayList;
import java.util.List;

public class BranchesList extends Fragment {

    private RecyclerView mapsRecyclerView;
    private MapsAdapter mapsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.branches_list, container, false);
        getActivity().setTitle("Maps");
        List<Branch> branchList = new ArrayList<>();
        branchList.add(
                new Branch
                        ("Smart Village",
                                "Address: 28 Km by Cairo / Alexandria Desert Road - 6 October - Building B148, Egypt",
                                "Phones:  (+202) 353-55656",
                                "Email:  ITIinfo@iti.gov.eg",
                                1,
                                true));
        branchList.add(
                new Branch(
                        "Alexandria",
                        "Address: 1 Mahmoud Said St., Shohada Square, Main Post Office Building, Alexandria",
                        "Phones: 033906924 - 033906925",
                        "Email: dsadsdc@dsad.com",
                        7,
                        false));
        branchList.add(
                new Branch("Assuit",
                        "Address: Assuit University, Network Information Building, Assuit",
                        "Phones: 0882374264 - 0882374265",
                        "Email: ITIinfo@iti.gov.eg",
                        8,
                        false));
        branchList.add(
                new Branch("Mansoura",
                        "Address: Mansoura University, Faculty of Computers & Information Sciences",
                        "Phones: 0502202474 - 0502203217",
                        "Email: ITIinfo@iti.gov.eg",
                        9,
                        false));
        branchList.add(
                new Branch("Ismailia",
                        "Address: Suez-Canal University, Main entrance Gate, Information Technology Building",
                        "Phones: 0649203358 - 0649203357",
                        "Email: ITIinfo@iti.gov.eg",
                        10,
                        false));

        mapsRecyclerView = (RecyclerView) view.findViewById(R.id.mapsRecycler);

        mapsAdapter = new MapsAdapter(getActivity(),
                R.layout.maps_cardview_cell, branchList);

        mapsRecyclerView.setHasFixedSize(true);
        mapsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mapsRecyclerView.setAdapter(mapsAdapter);

        return view;
    }

}
