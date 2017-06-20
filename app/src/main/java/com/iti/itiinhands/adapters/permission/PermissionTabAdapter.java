package com.iti.itiinhands.adapters.permission;

/**
 * Created by omari on 5/28/2017.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iti.itiinhands.fragments.BranchesFragment;
//import com.iti.itiinhands.fragments.permission.PermissionFragment;
import com.iti.itiinhands.fragments.permission.PermissionFragment;
import com.iti.itiinhands.fragments.permission.StudentPermissionList;

public class PermissionTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PermissionTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PermissionFragment tab1 = new PermissionFragment();
//                Bundle b = new Bundle();
//                b.putInt("flag",1);
//                tab1.setArguments(b);

                return tab1;
            case 1:
                StudentPermissionList tab2 = new StudentPermissionList();
//                tab2.setFlag(2);
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