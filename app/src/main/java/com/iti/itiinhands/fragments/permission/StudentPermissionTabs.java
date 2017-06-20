package com.iti.itiinhands.fragments.permission;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.permission.PermissionTabAdapter;
import com.iti.itiinhands.adapters.scheduleAdapters.staff.PagerAdapter;

public class StudentPermissionTabs extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Permissions");
        View view =  inflater.inflate(R.layout.fragment_student_permssion_tabs, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Send Permission"));
        tabLayout.addTab(tabLayout.newTab().setText("My Permissions"));


        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        final PermissionTabAdapter adapter = new PermissionTabAdapter
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        getActivity().setTitle("Schedule");

        return view;



    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {





    }
}
