package com.iti.itiinhands.fragments;


import android.content.SharedPreferences;
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
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.schedule.SessionModel;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

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
    UserData userData;
    int flag = 0;
    int userType;
    int token;


    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get id from share prefs


        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());


        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        token = sharedPreferences.getInt(Constants.TOKEN,0);


        Bundle b = getArguments();
        if (b != null) flag = b.getInt("flag", 0);

        if (userType == 1) {
            networkManager.getStudentSchedule(this,token);

        } else if (userType == 2) {
            if (flag == 0)
                networkManager.getInstructorSchedule(this, token);
            else {
                int trackId = b.getInt("trackId");
                networkManager.getTrackSchedule(this, trackId);
            }
        }


        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.day);






        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

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
        ScheduleCardAdapter scheduleCardAdapter = new ScheduleCardAdapter(getContext(), groups, details);
        recyclerView.setAdapter(scheduleCardAdapter);
    }

    @Override
    public void onFailure() {

    }
}
