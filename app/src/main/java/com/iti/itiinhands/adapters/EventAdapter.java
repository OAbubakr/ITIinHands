package com.iti.itiinhands.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.Event;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by omari on 5/9/2017.
 */

public class EventAdapter  extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList = Collections.emptyList();
    private LayoutInflater inflater;
    Context context;

    public EventAdapter(List<Event> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_cell, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(view);

        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.eventName.setText(event.getEventDescription());
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(event.getStartTime()));
        holder.eventTime.setText(dateString);
        Picasso.with(context).load(event.getImageURL()).into(holder.eventImage);



    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName;
        ImageView eventImage;
        TextView eventTime;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_name);
            eventTime = (TextView) itemView.findViewById(R.id.event_time);
            eventImage = (ImageView) itemView.findViewById(R.id.event_image);
        }
    }
}
