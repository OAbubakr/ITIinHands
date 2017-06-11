package com.iti.itiinhands.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import com.google.firebase.messaging.FirebaseMessaging;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CustomExpandableListAdapter;
import com.iti.itiinhands.fragments.AboutIti;
import com.iti.itiinhands.fragments.BranchesFragment;
import com.iti.itiinhands.fragments.events.EventListFragment;
import com.iti.itiinhands.fragments.maps.BranchesList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GuestSideMenu extends AppCompatActivity {

    static DrawerLayout mDrawerLayout;
    ImageView home;
    Fragment fragment = null;
    TextView appname;
    ExpandableListView expListView;
    HashMap<String, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    int[] images={
            R.drawable.about_ti,
            R.drawable.tracks,
            R.drawable.map,
            R.drawable.sm_event,
            R.drawable.g_login};
    int[] second;
    int[] third;
    int[] forth;


    @Override
    protected void onStart() {
        super.onStart();

//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (mDrawerLayout.isDrawerOpen(expListView)) {
//                    mDrawerLayout.closeDrawer(expListView);
//                } else {
//                    mDrawerLayout.openDrawer(expListView);
//                }
//
//            }
//        });
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

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        FirebaseMessaging.getInstance().subscribeToTopic("events");


        ////for expandale
        /////////
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setGroupIndicator(null);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.side_menu_header, expListView, false);


        TextView name = (TextView) headerView.findViewById(R.id.name);
        TextView track = (TextView) headerView.findViewById(R.id.track_name);
        name.setText("Guest");
        //name.setVisibility(View.GONE);
        track.setVisibility(View.GONE);

        ImageView avatar = (ImageView) headerView.findViewById(R.id.imageView);

        ////////////////////////////////////////////////////////
        //set name and track or company of the user


        avatar.setImageResource(R.drawable.logo1);

//        Picasso.with(getApplicationContext()).load(R.d).into(avatar);


        ////////////////////////////////////////////////////////
        //set name and track or company of the user
//        name.setText("dina");
//        track.setText("web and mobile");

        // Add header view to the expandable list

        expListView.addHeaderView(headerView);

//        //////////////////////////sert the dcompany fragment  student schedule
        fragment = new AboutIti();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//        /////////////////////
        prepareListData();

        listAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild, images,second,third,forth,5);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                switch (groupPosition) {
                    case 0:
                        //replace with about iti fragment
                        fragment = new AboutIti();
                        break;
                    case 1:
                        //tracks fragment
                        fragment = new BranchesFragment();
                        break;
                    case 2:

                        // maps fragment
                        fragment = new BranchesList();
                        break;
                    case 3:
                        //events fragment
                        fragment = new EventListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                        break;

                    case 4:
                        // handle logout action
                        //Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_LONG).show();
                        //clear data in shared perference
//                        SharedPreferences setting = getSharedPreferences("userData", 0);
//                        SharedPreferences.Editor editor = setting.edit();
//                        editor.clear();
//                        editor.commit();

                        //unsubscribe from topics
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("events");

                        Intent logIn = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logIn);

                        //send user back to login activity
                        finish();
                        break;

                    default:
                        break;

                }
                mDrawerLayout.closeDrawer(expListView);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();


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
        listDataHeader.add("About ITI");
        listDataHeader.add("Branches and Tracks");
        listDataHeader.add("Maps");
        listDataHeader.add("Events");

        listDataHeader.add("Login");

        // Adding child data
        List<String> iti = new ArrayList<String>();
        List<String> tracks = new ArrayList<String>();
        List<String> events = new ArrayList<String>();
        List<String> maps = new ArrayList<String>();
        List<String> login = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), iti); // Header, Child data
        listDataChild.put(listDataHeader.get(1), tracks);
        listDataChild.put(listDataHeader.get(2), events);
        listDataChild.put(listDataHeader.get(3), maps);
        listDataChild.put(listDataHeader.get(4), login);

        //check extras
        if(getIntent().getExtras() != null){

            Fragment announcementFragment = new EventListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, announcementFragment).commit();
        }

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