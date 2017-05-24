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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventList extends AppCompatActivity {


    private ArrayList<Event> eventsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter eventsAdapter;

    private TextView dayTitle;
    private TextView dateTitle;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        dayTitle = (TextView) findViewById(R.id.day_title);
        dateTitle = (TextView) findViewById(R.id.date_title);
        calendarView = (CalendarView) findViewById(R.id.calenderView);

        Calendar calendar = Calendar.getInstance();
        System.out.println("------------------------------"+calendar.getTime());

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        dayTitle.setText(dayFormat.format(calendar.getTime()));

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM d", Locale.US);
        SimpleDateFormat dayNumFormat = new SimpleDateFormat("d", Locale.US);
        String n = getDayOfMonthSuffix(Integer.parseInt(dayNumFormat.format(calendar.getTime())));
        dateTitle.setText(monthFormat.format(calendar.getTime())+ n);

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
        e1.setTitle("Event 1");
        e1.setDescription("Home");
        e1.setEventStart(33334444);

        Event e2 = new Event();
        e2.setTitle("Event 2");
        e2.setDescription("School");
        e2.setEventStart(6655);

        Event e3 = new Event();
        e3.setTitle("Event 3");
        e3.setDescription("Collage");
        e3.setEventStart(433112);

        Event e4 = new Event();
        e4.setTitle("Event 4");
        e4.setDescription("Work");
        e4.setEventStart(9909);

        Event[] e = new Event[]{e1, e2, e3, e4};
        eventsList.addAll(Arrays.asList(e));
        eventsAdapter.notifyDataSetChanged();
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
}
