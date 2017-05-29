package com.iti.itiinhands.adapters.scheduleAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.model.schedule.SessionModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by omari on 5/29/2017.
 */

public class ScheduleCardAdapter extends RecyclerView.Adapter<ScheduleCardAdapter.ScheduleCardViewHolder> {

    Context context;
    List<String> listDataHeader;
    HashMap<String, List<SessionModel>> listChildData;

    private LayoutInflater inflater;


    public ScheduleCardAdapter(Context context, List<String> listDataHeader,
                               HashMap<String, java.util.List<SessionModel>> listChildData) {

        this.context = context;
        this.listChildData = listChildData;
        this.listDataHeader = listDataHeader;
        inflater = LayoutInflater.from(context);



    }


    @Override
    public ScheduleCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.schedule_card_cell, parent, false);
        ScheduleCardViewHolder eventViewHolder = new ScheduleCardViewHolder(view);

        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduleCardViewHolder holder, int position) {

        String sessionName = listDataHeader.get(position);
        holder.sessionName.setText(sessionName);


        List<SessionModel> sessionModels =  listChildData.get(listDataHeader.get(position));



        ScheduleExapandableAdapter exapandableAdapter = new ScheduleExapandableAdapter(context,sessionModels);

        holder.expandableListView.setAdapter(exapandableAdapter);

    }

    @Override
    public int getItemCount() {
        return listDataHeader.size();
    }

    public class ScheduleCardViewHolder extends RecyclerView.ViewHolder {

        TextView sessionName;
        ExpandableListView expandableListView;


        public ScheduleCardViewHolder(View itemView) {
            super(itemView);
            sessionName = (TextView) itemView.findViewById(R.id.dayName);
            expandableListView = (ExpandableListView) itemView.findViewById(R.id.expLstView);

        }
    }
}
