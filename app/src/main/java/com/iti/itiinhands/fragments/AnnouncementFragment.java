package com.iti.itiinhands.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.AnnouncementAdapter;
import com.iti.itiinhands.beans.Announcement;
import com.iti.itiinhands.database.DataBase;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.ArrayList;


public class AnnouncementFragment extends Fragment {


    private RecyclerView recyclerView;
    private AnnouncementAdapter announcementAdapter;
    private ArrayList<Announcement> announcements =new ArrayList<>();
    private DataBase dataBase;
    TextView noAnnounTV;


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
        noAnnounTV = (TextView) view.findViewById(R.id.noAnnounTV);
        noAnnounTV.setVisibility(View.INVISIBLE);
        recyclerView = (RecyclerView)view.findViewById(R.id.announcements_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL,false));

        getActivity().setTitle("Announcements");

//        announcements=dataBase.getAnnoucements();
//        announcementAdapter =new AnnouncementAdapter(announcements,getActivity().getApplicationContext());
//        recyclerView.setAdapter(announcementAdapter);
//        announcementAdapter.notifyDataSetChanged();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences setting = getActivity().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        UserData userData = UserDataSerializer.deSerialize(setting.getString(Constants.USER_OBJECT,""));
        int type = setting.getInt(Constants.USER_TYPE,0 );
        String userName = "";

        switch (type){
            case 0:
                //guest
                userName = "guest";
                break;
            case 1:
                //Student
                userName = userData.getName();
                break;
            case 2:
                //Staff
                userName = userData.getEmployeeName();
                break;
            case 3:
                //Company
                userName = userData.getCompanyUserName();
                break;
            case 4:
                //Graduate
                userName = "graduate";
                break;

        }
        announcements=dataBase.getAnnoucements(userName);
        if(announcements.size() == 0){
            noAnnounTV.setVisibility(View.VISIBLE);
        }
        Log.i("size",String.valueOf(announcements.size()));

        announcementAdapter =new AnnouncementAdapter(announcements,getActivity().getApplicationContext(),getFragmentManager());
        recyclerView.setAdapter(announcementAdapter);
        //announcementAdapter.notifyDataSetChanged();
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
