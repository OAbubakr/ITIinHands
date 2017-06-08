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

import com.google.firebase.messaging.FirebaseMessaging;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CustomExpandableListAdapter;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.AboutIti;
import com.iti.itiinhands.fragments.AboutIti;
import com.iti.itiinhands.fragments.AllJobPostsFragment;
import com.iti.itiinhands.fragments.AnnouncementFragment;
import com.iti.itiinhands.fragments.BranchesFragment;
import com.iti.itiinhands.fragments.CompanyProfileFragment;
import com.iti.itiinhands.fragments.EventListFragment;
import com.iti.itiinhands.fragments.PostJobFragment;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.fragments.maps.BranchesList;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;
import com.squareup.picasso.Picasso;

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
    int[] images = {R.drawable.sm_profile,R.drawable.itians,R.drawable.sm_iti,R.drawable.stu_job_post,R.drawable.sm_logout};
    UserData userData;
    int[] itians={R.drawable.students,R.drawable.graduate};

    int[] itiImages={
            R.drawable.about_ti,
            R.drawable.tracks,
            R.drawable.event,
            R.drawable.map,
            R.drawable.bus,
            R.drawable.announce};
    int[] third;

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

        //subscribe to receive notifications
        FirebaseMessaging.getInstance().subscribeToTopic("events");


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
        expListView.setGroupIndicator(null);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.side_menu_header, expListView, false);


        TextView name = (TextView) headerView.findViewById(R.id.name);
        TextView track = (TextView) headerView.findViewById(R.id.track_name);

        ImageView avatar = (ImageView) headerView.findViewById(R.id.imageView);

        ////////////////////////////////////////////////////////
        //set name and track or company of the user

        SharedPreferences data = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

        userData = UserDataSerializer.deSerialize(data.getString(Constants.USER_OBJECT, ""));

        if(userData != null){
            name.setText(userData.getCompanyName());
            track.setText("");
//        if(userData.getImagePath()==null) userData.setImagePath("") ;
            Picasso.with(getApplicationContext()).load(userData.getCompanyLogoPath()).placeholder(R.drawable.ic_account_circle_white_48dp).into(avatar);
        }

        // Add header view to the expandable list

        expListView.addHeaderView(headerView);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem)
                    expListView.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });

//        //////////////////////////sert the dcompany fragment  student schedule
        fragment = new AboutIti();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//        /////////////////////
        prepareListData();

        listAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild,images,itians,itiImages,third,4);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                switch (groupPosition) {

                    case 0:
                        fragment = new CompanyProfileFragment();
                        mDrawerLayout.closeDrawer(expListView);
                        break;

                    case 3:
                        fragment = new PostJobFragment();
                        mDrawerLayout.closeDrawer(expListView);
                        break;
                    case 4:
                        // handle logout action
                        //clear data in shared perference
                        SharedPreferences setting = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                        SharedPreferences.Editor editor = setting.edit();
                        editor.remove(Constants.LOGGED_FLAG);
                        editor.remove(Constants.TOKEN);
                        editor.remove(Constants.USER_TYPE);
                        editor.remove(Constants.USER_OBJECT);
                        editor.commit();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();


                return false;
            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch (groupPosition) {
                    case 2:
                        switch (childPosition) {
                            case 0:
                                //handle about iti fragment
                                fragment=new AboutIti();
                                break;
                            case 1:
                                //handle tracks fragment
                                fragment  =new BranchesFragment();
                                break;
                            case 2:
                                //handle events fragment
                                fragment=new EventListFragment();
                                break;
                            case 3:
                                //handle maps fragment
                                fragment = new BranchesList();
                                break;
                            case 4:
                                //handle bus services fragment
//                                fragment = new BranchesFragment();
                                break;
                            default:
                                fragment = new BranchesList();
                                break;
                        }
                        break;

                    case 1:
                        switch (childPosition) {
                            case 0:
                                //accesss students profile
                                BranchesFragment temp =new BranchesFragment();
                                temp.setFlag(1);
                                fragment = temp;
                                break;
                            case 1:
                                //access graduates profile
                                Toast.makeText(getApplicationContext(), "graduates list", Toast.LENGTH_LONG).show();
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
        listDataHeader.add("ITIians");
        listDataHeader.add("ITI");
        listDataHeader.add("Add Job post");
        listDataHeader.add("Logout");

        // Adding child data
        List<String> iti = new ArrayList<String>();
        iti.add("About ITI");
        iti.add("Branches and Tracks");
        iti.add("Events");
        iti.add("Maps");
        iti.add("Bus Services");
        iti.add("Announcements");


        List<String> itians = new ArrayList<String>();
        itians.add("Students");
        itians.add("Graduates");


        List<String> profile = new ArrayList<String>();
        List<String> postJobs = new ArrayList<String>();
        List<String> announcement = new ArrayList<String>();
        List<String> logout = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), profile);
        listDataChild.put(listDataHeader.get(2), iti); // Header, Child data
        listDataChild.put(listDataHeader.get(1), itians);
        listDataChild.put(listDataHeader.get(3), postJobs);
        listDataChild.put(listDataHeader.get(4), logout);

        //check extras
        if (getIntent().getExtras() != null) {

            Fragment announcementFragment = new AnnouncementFragment();
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