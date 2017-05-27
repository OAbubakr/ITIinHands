package com.iti.itiinhands.adapters.scheduleAdapters;

import android.widget.ListView;

import com.iti.itiinhands.beans.Schedule;
import com.iti.itiinhands.model.schedule.SessionModel;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by omari on 5/25/2017.
 */

public class ScheduleAdapter {


    private List<String> groups;
    private HashMap<String, List<SessionModel>> details;


    public ScheduleAdapter(List<SessionModel> list) {
        setGroups(list);
        setDetails(list);


    }


    private void setGroups(List<SessionModel> list) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE dd/MM");
        List<String> days = new ArrayList<>();
        Date date;
        for (SessionModel session : list) {
            date = new Date(session.getSessionDate());

            String dayName = formatter.format(date);
            session.setDayName(dayName);
            if (!days.contains(dayName))
                days.add(dayName);


        }
        this.groups = days;

    }


    private void setDetails(List<SessionModel> list) {
        details = new HashMap<>();

        for (String s : groups){
           details.put(s,new ArrayList<SessionModel>());
        }

        Set<String> keys = details.keySet();

        for (SessionModel sessionModel : list){

            for (String s: keys){
                if (sessionModel.getDayName().equals(s)) details.get(s).add(sessionModel);

            }
        }

    }

    public List<String> getGroups() {
        return groups;
    }

    public HashMap<String, List<SessionModel>> getDetails() {
        return details;
    }


}
