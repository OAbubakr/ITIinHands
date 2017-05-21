package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.TrackDetails;
import com.iti.itiinhands.beans.Track;

import java.util.ArrayList;

/**
 * Created by Rana Gamal on 07/05/2017.
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksAdapter.MyViewHolder>{

    private ArrayList<Track> tracksList = new ArrayList<>();
    private Context context;

    public TracksAdapter(ArrayList<Track> tracksList, Context context){
        this.tracksList = tracksList;
        this.context = context;
    }

    @Override
    public TracksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tracks_list_item, parent, false);
        return new TracksAdapter.MyViewHolder(itemView, tracksList);
    }

    @Override
    public void onBindViewHolder(TracksAdapter.MyViewHolder holder, int position) {
        holder.bind(tracksList.get(position));
    }

    @Override
    public int getItemCount() {
        return tracksList.size();
    }

    //----------------------------------View Holder Class-------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView trackName;
        ArrayList<Track> tracksList = new ArrayList<>();

        public MyViewHolder(View itemView, ArrayList<Track> tracksList) {
            super(itemView);
            this.tracksList = tracksList;
            trackName = (TextView) itemView.findViewById(R.id.track_list_item);
        }

        public void bind(final Track track){
            trackName.setText(track.getTrackName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, track.getTrackName(), Toast.LENGTH_SHORT).show();
                    Intent trackDetailsView = new Intent(context, TrackDetails.class);
                    trackDetailsView.putExtra("trackObject", track);
                    context.startActivity(trackDetailsView);
                }
            });
        }
    }
}
