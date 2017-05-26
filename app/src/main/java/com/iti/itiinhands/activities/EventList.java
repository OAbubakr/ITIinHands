package com.iti.itiinhands.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.adapters.EventAdapter;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentCourse;
import com.iti.itiinhands.networkinterfaces.NetworkApi;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.Response;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventList extends AppCompatActivity implements NetworkResponse {


    private ArrayList<Event> eventsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter eventsAdapter;

    private TextView dayTitle;
    private TextView dateTitle;
    CompactCalendarView calendarView;
//    CalendarView calendarView;

    private NetworkManager networkManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        networkManager = NetworkManager.getInstance(getApplicationContext());
        dayTitle = (TextView) findViewById(R.id.day_title);
        dateTitle = (TextView) findViewById(R.id.date_title);
        calendarView = (CompactCalendarView) findViewById(R.id.calenderView);
//        calendarView = (CalendarView) findViewById(R.id.calenderView);;

        Calendar calendar = Calendar.getInstance();
        System.out.println("------------------------------"+calendar.getTime());
        calendarView.setFirstDayOfWeek(Calendar.SATURDAY);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        dayTitle.setText(dayFormat.format(calendar.getTime()).toUpperCase());

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM d", Locale.US);
        SimpleDateFormat dayNumFormat = new SimpleDateFormat("d", Locale.US);
        String n = getDayOfMonthSuffix(Integer.parseInt(dayNumFormat.format(calendar.getTime())));
        dateTitle.setText(monthFormat.format(calendar.getTime()).toUpperCase()+ n);

        recyclerView =(RecyclerView) findViewById(R.id.event_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        prepareEventData();
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
        eventsAdapter = new EventAdapter(eventsList, getApplicationContext());
        recyclerView.setAdapter(eventsAdapter);

        for(Event e: eventsList){
            com.github.sundeepk.compactcalendarview.domain.Event ev = new com.github.sundeepk.compactcalendarview.domain.Event(Color.parseColor("#7F0000"), e.getEventStart());
            calendarView.addEvent(ev);
//            calendarView.setDate(e.getEventStart());
        }
    }

    @Override
    public void onFaliure() {
        Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
    }
}
