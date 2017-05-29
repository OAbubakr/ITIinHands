package com.iti.itiinhands.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CustomExpandableListAdapter;
import com.iti.itiinhands.beans.Announcement;
import com.iti.itiinhands.database.DataBase;
import com.iti.itiinhands.fragments.AnnouncementFragment;
import com.iti.itiinhands.fragments.BranchesFragment;
import com.iti.itiinhands.fragments.EventListFragment;
import com.iti.itiinhands.fragments.ScheduleFragment;
import com.iti.itiinhands.fragments.StaffSchedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SideMenuActivity extends AppCompatActivity {

    static DrawerLayout mDrawerLayout;
    ImageView home;
    Fragment fragment = null;
    TextView appname;
    ExpandableListView expListView;
    HashMap<String, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    int[] images = {R.drawable.social, R.drawable.home_512, R.drawable.forums, R.drawable.info_512, R.drawable.outbox};


    @Override
    protected void onStart() {
        super.onStart();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDrawerLayout.isDrawerOpen(expListView)) {
                    mDrawerLayout.closeDrawer(expListView);
                } else {
                    mDrawerLayout.openDrawer(expListView);
                }

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_side_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        home = (ImageView) findViewById(R.id.home);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.side_menu_header, expListView, false);


        TextView name = (TextView) headerView.findViewById(R.id.name);
        TextView track = (TextView) headerView.findViewById(R.id.track_name);


        name.setText("dina");
        track.setText("web and mobile");


        expListView.addHeaderView(headerView);

        fragment = new BranchesFragment();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//        /////////////////////
        prepareListData();
        listAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild,images);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                switch (groupPosition) {
                    case 0:

                        //replace with profile fragment
                        Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_LONG).show();
                        break;

                    case 4:
                        //logout action
                        //clear data in shared perference
                        SharedPreferences setting = getSharedPreferences("userData", 0);
                        SharedPreferences.Editor editor = setting.edit();
                        editor.clear();
                        editor.commit();

                        //send user back to login activity
                        finish();
                        break;


                    default:
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();


                return false;
            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch (groupPosition) {
                    case 1:
                        switch (childPosition) {
                            case 0:



                                // get type from share prefs

                                //if(student)  fragment= new ScheduleFragment();

                                //if(staff)

                                fragment= new StaffSchedule();
                                break;
                            case 1:
                                //handle grades fragment
                                //Toast.makeText(getApplicationContext(), "0,1", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                //handle permission fragment
                                Toast.makeText(getApplicationContext(), "0,2", Toast.LENGTH_LONG).show();
                                break;
                            case 3:
                                //handle evaluation fragment
                                fragment = new BranchesFragment();
                                break;
                            case 4:
                                //handle announcment fragment

                                Announcement announcement=new Announcement();
                                announcement.setDate(1234);
                                announcement.setBody("cdcnjkdnckc");
                                announcement.setType(1);
                                announcement.setTitle("dnwkendjkwnejdk");
                                DataBase DB=DataBase.getInstance(getApplicationContext());
                                DB.insertAnnouncement(announcement);
                                fragment = new AnnouncementFragment();
                                break;
                            case 5:
                                //handle list of courses fragment
                                fragment = new BranchesFragment();
                                break;
                            default:
                                break;
                        }
                        break;

                    case 2:
                        switch (childPosition) {
                            case 0:
                                Toast.makeText(getApplicationContext(), "1,0", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "1,1", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(getApplicationContext(), "1,2", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        break;

                    case 3:
                        switch (childPosition) {
                            case 0:
                                Toast.makeText(getApplicationContext(), "2,2", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                fragment = new BranchesFragment();
                                break;
                            case 2:
                                fragment = new EventListFragment();
                                break;
                            case 3:
                                Toast.makeText(getApplicationContext(), "2,2", Toast.LENGTH_LONG).show();
                                break;
                            case 4:
                                Toast.makeText(getApplicationContext(), "2,2", Toast.LENGTH_LONG).show();
                                break;

                            default:
                                break;
                        }
                        break;

                    default:
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                mDrawerLayout.closeDrawer(expListView);
                return false;
            }
        });
    }


//    View.OnClickListener homeOnclickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if (mDrawerLayout.isDrawerOpen(expListView)) {
//                mDrawerLayout.closeDrawer(expListView);
//            } else {
//                mDrawerLayout.openDrawer(expListView);
//            }
//        }
//    };

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Profile");
        listDataHeader.add("My Track");
        listDataHeader.add("Community");
        listDataHeader.add("ITI");
        listDataHeader.add("Logout");

        // Adding child data
        List<String> profile = new ArrayList<String>();
        //profile.add("");


        List<String> myTrack = new ArrayList<String>();
        myTrack.add("Schedule");
        myTrack.add("Grades");
        myTrack.add("Permission");
        myTrack.add("Evaluation");
        myTrack.add("Announcment");
        myTrack.add("List of Courses");


        List<String> community = new ArrayList<String>();
        community.add("Students");
        community.add("Staff");
        community.add("Graduates");


        List<String> aboutIti = new ArrayList<String>();
        aboutIti.add("About ITI");
        aboutIti.add("Tracks");
        aboutIti.add("Events");
        aboutIti.add("Maps");
        aboutIti.add("Bus Services");


        List<String> logout = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), profile); // Header, Child data
        listDataChild.put(listDataHeader.get(1), myTrack);
        listDataChild.put(listDataHeader.get(2), community);
        listDataChild.put(listDataHeader.get(3), aboutIti);
        listDataChild.put(listDataHeader.get(4), logout);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


}