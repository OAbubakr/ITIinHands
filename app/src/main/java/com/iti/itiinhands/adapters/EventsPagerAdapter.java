package com.iti.itiinhands.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

//import com.iti.itiinhands.fragments.EventCalendarFragment;
import com.iti.itiinhands.fragments.EventListFragment;
import com.iti.itiinhands.fragments.events.EventCalendarFragment;

/**
 * Created by admin on 6/10/2017.
 */

public class EventsPagerAdapter extends FragmentStatePagerAdapter {

    int num;
    public  EventsPagerAdapter(int num,FragmentManager fm){
        super(fm);
        this.num=num;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position){

            case 1:
                return new EventListFragment();
            case 2:
            return new EventCalendarFragment();

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return num;
    }
}
