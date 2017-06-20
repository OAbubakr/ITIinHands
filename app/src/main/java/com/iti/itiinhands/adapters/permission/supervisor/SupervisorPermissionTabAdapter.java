package com.iti.itiinhands.adapters.permission.supervisor;

/**
 * Created by omari on 5/28/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iti.itiinhands.fragments.permission.PermissionFragment;
import com.iti.itiinhands.fragments.permission.StudentPermissionList;
import com.iti.itiinhands.fragments.permission.supervisor.SupervisorPermissionList;

public class SupervisorPermissionTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public SupervisorPermissionTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SupervisorPermissionList tab1 = new SupervisorPermissionList();
//                Bundle b = new Bundle();
//                b.putInt("flag",1);
//                tab1.setArguments(b);

                return tab1;
            case 1:
                SupervisorPermissionList tab2 = new SupervisorPermissionList();
                Bundle b = new Bundle();
                b.putInt("flag", 1);
                tab2.setArguments(b);
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