package com.iti.itiinhands.adapters.scheduleAdapters.staff;

/**
 * Created by omari on 5/28/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iti.itiinhands.activities.Schedule;
import com.iti.itiinhands.fragments.BranchesFragment;
import com.iti.itiinhands.fragments.ScheduleFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ScheduleFragment tab1 = new ScheduleFragment();
//                Bundle b = new Bundle();
//                b.putInt("flag",1);
//                tab1.setArguments(b);

                return tab1;
            case 1:
                BranchesFragment tab2 = new BranchesFragment();
                tab2.setFlag(2);
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}