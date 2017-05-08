package com.iti.itiinhands.adapters;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.TrackDetails;
import com.iti.itiinhands.activities.Tracks;
import com.iti.itiinhands.beans.Branch;
import com.iti.itiinhands.beans.Track;

import java.util.ArrayList;

/**
 * Created by Rana Gamal on 07/05/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    private ArrayList<Object> dataList = new ArrayList<>();
    private int viewType;

    public RecyclerViewAdapter(ArrayList<Object> dataList, int viewType){
        this.dataList = dataList;
        this.viewType = viewType;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType){
            case 0: //Branches List
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.branches_list_item, parent, false);
                break;
            case 1: //Tracks List
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracks_list_item, parent, false);
                break;
            default: break;
        }
        return new RecyclerViewAdapter.MyViewHolder(itemView, dataList, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.bind(dataList.get(position), viewType);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return viewType;
    }

    //----------------------------------View Holder Class-------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView branchLocation;
        public TextView trackName;

        ArrayList<Object> dataList = new ArrayList<>();

        public MyViewHolder(View itemView, ArrayList<Object> dataList, int viewType) {
            super(itemView);
            this.dataList = dataList;
            switch (viewType){
                case 0: //Branches List
                    branchLocation = (TextView) itemView.findViewById(R.id.branch_list_item);
                    break;
                case 1: //Tracks List
                    trackName = (TextView) itemView.findViewById(R.id.track_list_item);
                    break;
            }
        }

        public void bind(final Object object, final int viewType){

            switch (viewType){
                case 0: //Branches List
                    final Branch branch = (Branch) object;
                    branchLocation.setText(branch.getLocation());
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(branchLocation.getContext(), branch.getLocation(), Toast.LENGTH_SHORT).show();
                            Intent tracksView = new Intent(branchLocation.getContext(), Tracks.class);
                            tracksView.putExtra("branchObject", branch);
                            branchLocation.getContext().startActivity(tracksView);
                        }
                    });
                    break;
                case 1: //Tracks List
                    final Track track = (Track) object;
                    trackName.setText(track.getTrackName());
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(trackName.getContext(), track.getTrackName(), Toast.LENGTH_SHORT).show();
                            Intent trackDetailsView = new Intent(trackName.getContext(), TrackDetails.class);
                            trackDetailsView.putExtra("trackObject", track);
                            trackName.getContext().startActivity(trackDetailsView);
                        }
                    });
            }
        }
    }
}
