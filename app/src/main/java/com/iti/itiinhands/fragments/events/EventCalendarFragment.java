package com.iti.itiinhands.fragments.events;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.events.CalenderFragmentAdapter;
import com.iti.itiinhands.model.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventCalendarFragment extends Fragment {

    private TextView month;
    private TextView year;
    private LinearLayout container;

    private Calendar calendar;
    private CompactCalendarView calendarView;
    private ArrayList<com.iti.itiinhands.model.Event> allEvents;
    private ArrayList<com.github.sundeepk.compactcalendarview.domain.Event> sundeebkEvents;
    private String displayMonth;
    private int displayYear;
    private RecyclerView recyclerView;
    private List<com.github.sundeepk.compactcalendarview.domain.Event> events = new ArrayList<>();
    private CalenderFragmentAdapter calenderFragmentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {


        container = (LinearLayout) view.findViewById(R.id.container);

        month = (TextView) view.findViewById(R.id.month);
        year = (TextView) view.findViewById(R.id.year);
        recyclerView = (RecyclerView) view.findViewById(R.id.event_recycler_view);

        calendar = Calendar.getInstance();

        calendarView = (CompactCalendarView) view.findViewById(R.id.calenderView);

        calenderFragmentAdapter = new CalenderFragmentAdapter(events, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(calenderFragmentAdapter);

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                events.clear();
                events.addAll(calendarView.getEvents(dateClicked));
                calenderFragmentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                events.clear();
                calenderFragmentAdapter.notifyDataSetChanged();

                calendar.setTime(firstDayOfNewMonth);
                displayMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
                displayYear = calendar.get(Calendar.YEAR);

                month.setText(displayMonth);
                year.setText(String.valueOf(displayYear));

            }
        });


    }

    public void setAllEvents(ArrayList<com.iti.itiinhands.model.Event> allEvents) {

        if(allEvents != null & allEvents.size() > 0){
            this.allEvents = allEvents;
            calendarView.removeAllEvents();
            sundeebkEvents = new ArrayList<>();

            for (Event event : allEvents) {
                com.github.sundeepk.compactcalendarview.domain.Event sundeebkEvent =
                        new com.github.sundeepk.compactcalendarview.domain.Event(Color.GREEN, event.getEventStart(), event);
                sundeebkEvents.add(sundeebkEvent);
            }

            calendarView.addEvents(sundeebkEvents);

            calendar.setTimeInMillis(allEvents.get(0).getEventStart());
            calendarView.setCurrentDate(calendar.getTime());

            displayMonth = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            displayYear = calendar.get(Calendar.YEAR);

            month.setText(displayMonth);
             year.setText(String.valueOf(displayYear));

            container.setVisibility(View.VISIBLE);
        }


    }


}
