package com.iti.itiinhands.adapters.events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iti.itiinhands.fragments.events.EventCalendarFragment;
import com.iti.itiinhands.fragments.events.EventListFragment;

/**
 * Created by admin on 6/10/2017.
 */

public class EventsPagerAdapter extends FragmentStatePagerAdapter {

    private int num;
    private EventCalendarFragment eventCalendarFragment;
    private EventListFragment eventListFragment;

    public  EventsPagerAdapter(int num,FragmentManager fm,
                               EventCalendarFragment eventCalendarFragment, EventListFragment eventListFragment){
        super(fm);
        this.num=num;
        this.eventCalendarFragment = eventCalendarFragment;
        this.eventListFragment = eventListFragment;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 0:
                return eventListFragment;
            case 1:
                return eventCalendarFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return num;
    }
}
