package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.Tracks;
import com.iti.itiinhands.beans.Event;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by omari on 5/9/2017.
 */

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private ArrayList<Event> eventsList = new ArrayList<>();
    private Context context;

    public EventAdapter(ArrayList<Event> eventsList, Context context) {
        this.eventsList = eventsList;
        this.context = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_cell, parent, false);
        return new EventViewHolder(itemView, eventsList);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
//        Event event = eventList.get(position);
//
//        holder.eventName.setText(event.getEventDescription());
//        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(event.getStartTime()));
//        holder.eventTime.setText(dateString);
//        Picasso.with(context).load(event.getImageURL()).into(holder.eventImage);

        holder.bind(eventsList.get(position));
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    //---------------------------------View Holder ---------------------------------------
    public class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitle;
        TextView eventLocation;
        TextView eventDate;
        ArrayList<Event> eventsList = new ArrayList<>();

        public EventViewHolder(View itemView, ArrayList<Event> eventsList) {
            super(itemView);
            this.eventsList = eventsList;
            eventTitle = (TextView) itemView.findViewById(R.id.event_name);
            eventLocation = (TextView) itemView.findViewById(R.id.event_place);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);
        }

        public void bind(final Event event){
            eventTitle.setText(event.getTitle());
            eventLocation.setText(event.getDescription());
            eventDate.setText(String.valueOf(event.getEventStart()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, event.getDescription(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
