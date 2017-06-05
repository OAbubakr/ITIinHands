package com.iti.itiinhands.adapters.scheduleAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.model.schedule.SessionModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by omari on 5/25/2017.
 */

public class ScheduleExapandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<SessionModel> listDataHeader;
//    private HashMap<Integer, List<SessionModel>> listChildData;


    public ScheduleExapandableAdapter(Context context, List<SessionModel> listDataHeader) {
        this.context = context;
        this.listDataHeader = listDataHeader;
//        this.listChildData = listChildData;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listDataHeader.get(i);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.schedule_cell, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.allDay);

        txt.setText(listDataHeader.get(i).getCourseName());
        if (listDataHeader.get(i).getTypeId()==2){convertView.setBackgroundColor(0xFFacd5cd);}
        else if(listDataHeader.get(i).getTypeId()==1){convertView.setBackgroundColor(0xFFb9c1d4);}
        else{convertView.setBackgroundColor(0xffd5accd);}

        return convertView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.schedule_day_details, null);
        }

        TextView txt = (TextView) convertView.findViewById(R.id.dayDetails);

        SessionModel sessionModel = listDataHeader.get(i);

        String temp = sessionModel.getCourseName();
        temp = temp + "\n" + sessionModel.getSessionTime();
        temp = temp + "\n" + sessionModel.getSessionPercentage();
        temp = temp + "\n" + sessionModel.getRoomName();
        if (sessionModel.getSessionTime() != null) temp = temp + "\n" + sessionModel.getTrackName();


        txt.setText(temp);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
