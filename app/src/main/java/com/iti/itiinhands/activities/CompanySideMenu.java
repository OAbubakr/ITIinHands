package com.iti.itiinhands.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import com.iti.itiinhands.fragments.AnnouncementFragment;
import com.iti.itiinhands.fragments.BranchesFragment;
import com.iti.itiinhands.fragments.EventListFragment;
import com.iti.itiinhands.fragments.PostJobFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanySideMenu extends AppCompatActivity {

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
        setContentView(R.layout.activity_company_side_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        home = (ImageView) findViewById(R.id.home);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);


        ////for expandale
        /////////
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.side_menu_header, expListView, false);


        TextView name = (TextView) headerView.findViewById(R.id.name);
        TextView track = (TextView) headerView.findViewById(R.id.track_name);


        ////////////////////////////////////////////////////////
        //set name and track or company of the user
        name.setText("dina");
        track.setText("web and mobile");

        // Add header view to the expandable list

        expListView.addHeaderView(headerView);

//        //////////////////////////sert the dcompany fragment  student schedule
        fragment = new BranchesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
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
                    case 2:
                        fragment= new PostJobFragment();
                        break;
                    case 3:
                        //announcment fragment
                        fragment=new AnnouncementFragment();
                        mDrawerLayout.closeDrawer(expListView);
                        break;
                    case 4:
                        // handle logout action
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
                    case 0:
                        switch (childPosition) {
                            case 0:
                                //handle about iti fragment
                                //fragment=new FragmentClass();
                                break;
                            case 1:
                                //handle tracks fragment
                                //Toast.makeText(getApplicationContext(), "0,1", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                //handle events fragment
                                Toast.makeText(getApplicationContext(), "0,2", Toast.LENGTH_LONG).show();
                                break;
                            case 3:
                                //handle maps fragment
                                fragment = new BranchesFragment();
                                break;
                            case 4:
                                //handle bus services fragment
                                fragment = new BranchesFragment();
                                break;
                            default:
                                break;
                        }
                        break;

                    case 1:
                        switch (childPosition) {
                            case 0:
                                //accesss students profile
                                Toast.makeText(getApplicationContext(), "1,0", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                //access graduates profile
                                Toast.makeText(getApplicationContext(), "1,1", Toast.LENGTH_LONG).show();
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
        listDataHeader.add("ITI");
        listDataHeader.add("Itians");
        listDataHeader.add("post job");
        listDataHeader.add("Announcement");
        listDataHeader.add("Logout");

        // Adding child data
        List<String> iti = new ArrayList<String>();
        iti.add("About ITI");
        iti.add("Tracks");
        iti.add("Events");
        iti.add("Maps");
        iti.add("Bus Services");


        List<String> itians = new ArrayList<String>();
        itians.add("Students");
        itians.add("Graduates");


        List<String> postJobs = new ArrayList<String>();
        List<String> announcement = new ArrayList<String>();
        List<String> logout = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), iti); // Header, Child data
        listDataChild.put(listDataHeader.get(1), itians);
        listDataChild.put(listDataHeader.get(2), postJobs);
        listDataChild.put(listDataHeader.get(3), announcement);
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