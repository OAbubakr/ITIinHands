package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CalendarView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.adapters.EventAdapter;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentCourse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventList extends AppCompatActivity {


    private ArrayList<Event> eventsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter eventsAdapter;

    private TextView dayTitle;
    private TextView dateTitle;
    private CalendarView calendarView;
    private TextView eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        dayTitle = (TextView) findViewById(R.id.day_title);
        dateTitle = (TextView) findViewById(R.id.date_title);
        eventTitle = (TextView) findViewById(R.id.event_title);
        calendarView = (CalendarView) findViewById(R.id.calenderView);

        Calendar calendar = Calendar.getInstance();
        System.out.println(calendar.getTime());

        dayTitle.setText("MONDAY");
        dateTitle.setText("JUNE 14TH");
        eventTitle.setText("EVENTS");

        recyclerView =(RecyclerView) findViewById(R.id.event_recycler_view);
        eventsAdapter = new EventAdapter(eventsList, getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eventsAdapter);

        prepareEventData();
    }

    private void prepareEventData(){

        Event e1 = new Event();
        e1.setEventTitle("Event 1");
        e1.setLocation("Home");
        e1.setStartTime(33334444);

        Event e2 = new Event();
        e2.setEventTitle("Event 2");
        e2.setLocation("School");
        e2.setStartTime(6655);

        Event e3 = new Event();
        e3.setEventTitle("Event 3");
        e3.setLocation("Collage");
        e3.setStartTime(433112);

        Event e4 = new Event();
        e4.setEventTitle("Event 4");
        e4.setLocation("Work");
        e4.setStartTime(9909);

        Event[] e = new Event[]{e1, e2, e3, e4};
        eventsList.addAll(Arrays.asList(e));
        eventsAdapter.notifyDataSetChanged();
    }
}
