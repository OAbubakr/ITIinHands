package com.iti.itiinhands.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.scheduleAdapters.ScheduleAdapter;
import com.iti.itiinhands.adapters.scheduleAdapters.ScheduleCardAdapter;
import com.iti.itiinhands.adapters.scheduleAdapters.ScheduleExapandableAdapter;
import com.iti.itiinhands.beans.Session;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.schedule.SessionModel;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements NetworkResponse {
    NetworkManager networkManager;
    RecyclerView recyclerView;
    int flag = 0;
    int id;

    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get id from share prefs
        id = 0;


        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());
        Bundle b = getArguments();
        if(b != null){
            flag = b.getInt("flag", 0);
        }

        if(flag == 0){
            networkManager.getStudentSchedule(this,5699);

        }
        else if (flag == 1) {
            networkManager.getInstructorSchedule(this, 1059);
        }
        else{
            int trackId = b.getInt("trackId");

            networkManager.getTrackSchedule(this, trackId);
        }



        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.day);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int previousItem = -1;
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (groupPosition != previousItem)
//                    expandableListView.collapseGroup(previousItem);
//                previousItem = groupPosition;
//            }
//        });

        return view;

    }

    @Override
    public void onResponse(Object response) {
        ArrayList<SessionModel> sessions = (ArrayList<SessionModel>) response;
        ScheduleAdapter adapter = new ScheduleAdapter(sessions);
        List<String> groups = adapter.getGroups();
        HashMap<String, List<SessionModel>> details = adapter.getDetails();

        ScheduleCardAdapter scheduleCardAdapter = new ScheduleCardAdapter(getContext(), groups, details);

        recyclerView.setAdapter(scheduleCardAdapter);
    }

    @Override
    public void onFailure() {

    }
}
