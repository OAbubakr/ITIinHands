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
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
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
        List<SessionModel> sat = new ArrayList<>();
        List<SessionModel> sun = new ArrayList<>();
        List<SessionModel> mon = new ArrayList<>();
        List<SessionModel> tues = new ArrayList<>();
        List<SessionModel> wed = new ArrayList<>();
        List<SessionModel> thu = new ArrayList<>();
        List<SessionModel> fri = new ArrayList<>();


        for (SessionModel session : list) {

            switch (session.getDayName()) {

                case "Saturday":
                    sat.add(session);
                    break;
                case "Sunday":
                    sun.add(session);
                    break;
                case "Monday":
                    mon.add(session);
                    break;
                case "Tuesday":
                    tues.add(session);
                    break;
                case "Wednesday":
                    wed.add(session);
                    break;
                case "Thursday":
                    thu.add(session);
                    break;
                case "Friday":
                    fri.add(session);
                    break;

            }

        }
        details.put("Saturday", sat);
        details.put("Sunday", sun);
        details.put("Monday", mon);
        details.put("Tuesday", tues);
        details.put("Wednesday", wed);
        details.put("Thursday", thu);
        details.put("Friday", fri);


    }

    public List<String> getGroups() {
        return groups;
    }

    public HashMap<String, List<SessionModel>> getDetails() {
        return details;
    }


}
