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
//        flag = b.getInt("flag", 0);

//        if(flag == 0){
//            networkManager.getStudentSchedule(this,5699);
//
//        }
//        if (flag == 1) {
//            networkManager.getInstructorSchedule(this, 1059);
//        }
//        else{
//            int trackId = b.getInt("trackId");
//
//            networkManager.getTrackSchedule(this, trackId);
//        }



        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.day);


////////////////////////////////////////////
        String s = "[\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 18346,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1413756000000,\n" +
                "        \"weekNumber\": 4,\n" +
                "        \"roomName\": \"2016\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"4/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"ERP-SAP Consultant\",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 18347,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1413928800000,\n" +
                "        \"weekNumber\": 4,\n" +
                "        \"roomName\": \"2016\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"5/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"ERP-SAP Consultant\",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16845,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414015200000,\n" +
                "        \"weekNumber\": 4,\n" +
                "        \"roomName\": \"1011\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"1/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16846,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414015200000,\n" +
                "        \"weekNumber\": 4,\n" +
                "        \"roomName\": \"1011\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"2/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 17419,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414274400000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1012\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"1/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"Enterprise & Web Applications Developer (Java Technology)\",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 17420,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414274400000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1012\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"2/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"Enterprise & Web Applications Developer (Java Technology)\",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16865,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414360800000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1011\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"3/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16866,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414360800000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1020\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"1/4\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16867,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414447200000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1020\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"2/4\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16870,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414447200000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1011\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"4/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16868,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414533600000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1020\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"3/4\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16871,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414533600000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1011\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"5/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 16872,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414620000000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1020\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"4/4\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"GIS Professional \",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 17421,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414620000000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1012\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"3/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"Enterprise & Web Applications Developer (Java Technology)\",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 19043,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414620000000,\n" +
                "        \"weekNumber\": 5,\n" +
                "        \"roomName\": \"1004\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"1/4\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"Enterprise & Web Applications Developer (Java Technology)\",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 17926,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414879200000,\n" +
                "        \"weekNumber\": 6,\n" +
                "        \"roomName\": \"1012\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"1/5\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"Mobile Applications Developer\",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"sessionTime\": null,\n" +
                "        \"sessionId\": 19044,\n" +
                "        \"courseName\": \"Database Fundamentals\",\n" +
                "        \"sessionDate\": 1414879200000,\n" +
                "        \"weekNumber\": 6,\n" +
                "        \"roomName\": \"1004\",\n" +
                "        \"instructorName\": null,\n" +
                "        \"sessionPercentage\": \"2/4\",\n" +
                "        \"typeId\": 0,\n" +
                "        \"trackName\": \"Enterprise & Web Applications Developer (Java Technology)\",\n" +
                "        \"branchName\": \"Smart Village\"\n" +
                "    }\n" +
                "]";





        Type listType = new TypeToken<ArrayList<SessionModel>>(){}.getType();

        List<SessionModel> sss = new Gson().fromJson(s, listType);


        ScheduleAdapter adapter = new ScheduleAdapter(sss);
        List<String> groups = adapter.getGroups();
        HashMap<String, List<SessionModel>> details = adapter.getDetails();


//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(linearLayoutManager);

//        ScheduleExapandableAdapter exapandableAdapter = new ScheduleExapandableAdapter(getContext(), groups, details);

        ScheduleCardAdapter scheduleCardAdapter = new ScheduleCardAdapter(getContext(), groups, details);

        recyclerView.setAdapter(scheduleCardAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        linearLayoutManager.s
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setMinimumHeight(200);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        ////////////////////////////////////////////////////




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

//        ScheduleExapandableAdapter exapandableAdapter = new ScheduleExapandableAdapter(getContext(), groups, details);

        ScheduleCardAdapter scheduleCardAdapter = new ScheduleCardAdapter(getContext(), groups, details);


        recyclerView.setAdapter(scheduleCardAdapter);
    }

    @Override
    public void onFailure() {

    }
}
