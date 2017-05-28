package com.iti.itiinhands.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.scheduleAdapters.ScheduleAdapter;
import com.iti.itiinhands.adapters.scheduleAdapters.ScheduleExapandableAdapter;
import com.iti.itiinhands.beans.Session;
import com.iti.itiinhands.model.schedule.SessionModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {


    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        String s = "[{\"sessionTime\":\"9:00-12:00\",\"sessionId\":18542,\"courseName\":\"Network Security Fundamentals\",\"sessionDate\":1413756000000,\"weekNumber\":4,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1108\",\"sessionPercentage\":\"1/2\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":18543,\"courseName\":\"Network Security Fundamentals\",\"sessionDate\":1413756000000,\"weekNumber\":4,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1108\",\"sessionPercentage\":\"2/2\"},{\"sessionTime\":\"9:00-12:00\",\"sessionId\":16985,\"courseName\":\"Operating System Fundamentals\",\"sessionDate\":1413842400000,\"weekNumber\":4,\"roomName\":\"2032\",\"instructorName\":\"Emplyee1441\",\"sessionPercentage\":\"1/7\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":16986,\"courseName\":\"Operating System Fundamentals\",\"sessionDate\":1413842400000,\"weekNumber\":4,\"roomName\":\"2032\",\"instructorName\":\"Emplyee1441\",\"sessionPercentage\":\"2/7\"},{\"sessionTime\":\"9:00-12:00\",\"sessionId\":16987,\"courseName\":\"Operating System Fundamentals\",\"sessionDate\":1413928800000,\"weekNumber\":4,\"roomName\":\"2032\",\"instructorName\":\"Emplyee1441\",\"sessionPercentage\":\"3/7\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":16988,\"courseName\":\"Operating System Fundamentals\",\"sessionDate\":1413928800000,\"weekNumber\":4,\"roomName\":\"2032\",\"instructorName\":\"Emplyee1441\",\"sessionPercentage\":\"4/7\"},{\"sessionTime\":\"9:00-12:00\",\"sessionId\":16989,\"courseName\":\"Operating System Fundamentals\",\"sessionDate\":1414015200000,\"weekNumber\":4,\"roomName\":\"2032\",\"instructorName\":\"Emplyee1441\",\"sessionPercentage\":\"5/7\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":16991,\"courseName\":\"Operating System Fundamentals\",\"sessionDate\":1414015200000,\"weekNumber\":4,\"roomName\":\"2032\",\"instructorName\":\"Emplyee1441\",\"sessionPercentage\":\"6/7\"},{\"sessionTime\":\"9:00-12:00\",\"sessionId\":18566,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414274400000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"1/5\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":18561,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414274400000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"1/5\"},{\"sessionTime\":\"9:00-12:00\",\"sessionId\":18567,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414360800000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"2/5\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":18562,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414360800000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"2/5\"},{\"sessionTime\":\"9:00-12:00\",\"sessionId\":18568,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414447200000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"3/5\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":18563,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414447200000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"3/5\"},{\"sessionTime\":\"9:00-12:00\",\"sessionId\":18569,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414533600000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"4/5\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":18564,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414533600000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"4/5\"},{\"sessionTime\":\"9:00-12:00\",\"sessionId\":18570,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414620000000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"5/5\"},{\"sessionTime\":\"12:30-3:30\",\"sessionId\":18565,\"courseName\":\"Microsoft Windows  Infrastructure\",\"sessionDate\":1414620000000,\"weekNumber\":5,\"roomName\":\"2033\",\"instructorName\":\"Emplyee1467\",\"sessionPercentage\":\"5/5\"}]";
        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<SessionModel>>(){}.getType();
        ArrayList<SessionModel> sessions = gson.fromJson(s,listType);

        ScheduleAdapter adapter = new ScheduleAdapter(sessions);
        List<String> groups =  adapter.getGroups();
        HashMap<String,List<SessionModel>> details = adapter.getDetails();

        ScheduleExapandableAdapter exapandableAdapter = new ScheduleExapandableAdapter(getContext(),groups,details);

       final ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expLstView);
        expandableListView.setAdapter(exapandableAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    expandableListView.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });
        return view;

    }

}
