package com.iti.itiinhands.adapters.events;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.model.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by omari on 5/9/2017.
 */

public class CalenderFragmentAdapter extends RecyclerView.Adapter<CalenderFragmentAdapter.EventViewHolder> {

    private List<com.github.sundeepk.compactcalendarview.domain.Event> eventsList = new ArrayList<>();
    private Context context;

    public CalenderFragmentAdapter(List<com.github.sundeepk.compactcalendarview.domain.Event> eventsList, Context context) {
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
        List<com.github.sundeepk.compactcalendarview.domain.Event> eventsList = new ArrayList<>();

        public EventViewHolder(View itemView, List<com.github.sundeepk.compactcalendarview.domain.Event> eventsList) {
            super(itemView);
            this.eventsList = eventsList;
            eventTitle = (TextView) itemView.findViewById(R.id.event_name);
//            eventLocation = (TextView) itemView.findViewById(R.id.event_place);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);
        }

        public void bind(final com.github.sundeepk.compactcalendarview.domain.Event sundeebkEvent){
            final Event event = (Event) sundeebkEvent.getData();
            eventTitle.setText(event.getTitle());
//            eventLocation.setText(event.getDescription());

            SimpleDateFormat dayFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
            eventDate.setText(dayFormat.format(event.getEventStart()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, event.getDescription(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
