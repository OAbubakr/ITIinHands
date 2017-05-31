package com.iti.itiinhands.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.EventAdapter;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;


public class EventListFragment extends Fragment implements NetworkResponse {


    private ArrayList<Event> eventsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter eventsAdapter;

    private TextView dayTitle;
    private TextView dateTitle;
    CompactCalendarView calendarView;

    private NetworkManager networkManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_event_list, container, false);
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());

        dayTitle = (TextView) view.findViewById(R.id.day_title);
        dateTitle = (TextView) view.findViewById(R.id.date_title);
        calendarView = (CompactCalendarView) view.findViewById(R.id.calenderView);

        Calendar calendar = Calendar.getInstance();
        System.out.println("------------------------------"+calendar.getTime());
        calendarView.setFirstDayOfWeek(Calendar.SATURDAY);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        dayTitle.setText(dayFormat.format(calendar.getTime()).toUpperCase());

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM d", Locale.US);
        SimpleDateFormat dayNumFormat = new SimpleDateFormat("d", Locale.US);
        String n = getDayOfMonthSuffix(Integer.parseInt(dayNumFormat.format(calendar.getTime())));
        dateTitle.setText(monthFormat.format(calendar.getTime()).toUpperCase()+ n);

        recyclerView =(RecyclerView) view.findViewById(R.id.event_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareEventData();
        return view;
    }

    private void prepareEventData(){

        if (networkManager.isOnline()){
            networkManager.getEvents(this);
        }
    }

    private String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "TH";
        }
        switch (n % 10) {
            case 1:  return "ST";
            case 2:  return "ND";
            case 3:  return "RD";
            default: return "TH";
        }
    }

    @Override
    public void onResponse(Object response) {
        eventsList = (ArrayList<Event>) response;
        eventsAdapter = new EventAdapter(eventsList, getActivity().getApplicationContext());
        recyclerView.setAdapter(eventsAdapter);

        for(Event e: eventsList){
            com.github.sundeepk.compactcalendarview.domain.Event ev = new com.github.sundeepk.compactcalendarview.domain.Event(Color.parseColor("#7F0000"), e.getEventStart());
            calendarView.addEvent(ev);
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
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
}
