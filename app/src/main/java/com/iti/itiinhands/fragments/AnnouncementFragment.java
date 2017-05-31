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
import com.iti.itiinhands.adapters.AnnouncementAdapter;
import com.iti.itiinhands.beans.Announcement;
import com.iti.itiinhands.database.DataBase;

import java.util.ArrayList;


public class AnnouncementFragment extends Fragment {


    private RecyclerView recyclerView;
    private AnnouncementAdapter announcementAdapter;
    private ArrayList<Announcement> announcements =new ArrayList<>();
    private DataBase dataBase;


    public AnnouncementFragment() {
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
        View view=inflater.inflate(R.layout.fragment_announcement, container, false);
        dataBase=DataBase.getInstance(getActivity().getApplicationContext());
        recyclerView = (RecyclerView)view.findViewById(R.id.announcements_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL,false));


        announcements=dataBase.getAnnoucements();
        announcementAdapter =new AnnouncementAdapter(announcements);
        recyclerView.setAdapter(announcementAdapter);
        announcementAdapter.notifyDataSetChanged();

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
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


}
