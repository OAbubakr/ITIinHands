package com.iti.itiinhands.beans;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Sandra on 5/3/2017.
 */

public class Schedule {
    private ArrayList<Session> sessions;

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }
}
