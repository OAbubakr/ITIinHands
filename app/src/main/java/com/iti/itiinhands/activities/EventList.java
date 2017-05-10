package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CourseAdapter;
import com.iti.itiinhands.adapters.EventAdapter;
import com.iti.itiinhands.beans.Event;
import com.iti.itiinhands.beans.StudentCourse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventList extends AppCompatActivity {


    private List<Event> events;

    public List<Event> getEvents() {


        events =  new ArrayList<>();

        for (int i = 0 ;i<20 ; i++){
            Event e = new Event();
            e.setEventDescription("event "+i);
            e.setStartTime(new Date().getTime());
            switch (i%4){
                case 0:
                    e.setImageURL("https://www.jdslabs.com/images/engraving/cart/2015/8/Read+description+i+know+doge+is+considered+cancer+here+but_545875_4930596.png");
                    break;
                case 1:
                    e.setImageURL("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0yNP_WIWAYbg4RFECyGpRvsLIZIfrkdX5lyma61EOpPNW8aXI");
                    break;
                case 2:
                    e.setImageURL("https://lh4.ggpht.com/DlnbQiUtcdoFsNnn2mXvdGr_gLivZRdEgvIOGdysPVn3pdKx5nIc0WB1pN-aD8_U6Te5=w300");
                    break;
                case 3:
                    e.setImageURL("https://lh5.ggpht.com/sW2KGtpLaq48el5Xt4DXjXN3jsSat_ApH1RMF6KwMWkfTH4TjBE_-p5DHqNRWo7iOK7P");
                    break;


            }


            events.add(e);



        }



        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        final RecyclerView eventsRecyclerView =(RecyclerView) findViewById(R.id.eventsCell);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        eventsRecyclerView.setLayoutManager(linearLayoutManager);
        EventAdapter eventAdapter = new EventAdapter(getEvents(),getApplicationContext());

        eventsRecyclerView.setAdapter(eventAdapter);

    }
}
