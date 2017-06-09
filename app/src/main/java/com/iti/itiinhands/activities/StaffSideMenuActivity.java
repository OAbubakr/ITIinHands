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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.CustomExpandableListAdapter;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.AboutIti;
import com.iti.itiinhands.fragments.AnnouncementFragment;
import com.iti.itiinhands.fragments.BranchesFragment;
import com.iti.itiinhands.fragments.EventListFragment;
import com.iti.itiinhands.fragments.InstructorEvaluationFragment;
import com.iti.itiinhands.fragments.ScheduleFragment;
import com.iti.itiinhands.fragments.StaffSchedule;
import com.iti.itiinhands.fragments.chat.ChatFragment;
import com.iti.itiinhands.fragments.maps.BranchesList;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffSideMenuActivity extends AppCompatActivity {

    static DrawerLayout mDrawerLayout;
    ImageView home;
    Fragment fragment = null;
    TextView appname;
    ExpandableListView expListView;
    HashMap<String, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    int[] images = {R.drawable.sm_mytrack,
            R.drawable.sm_community,
            R.drawable.sm_iti,
            R.drawable.itians,
            R.drawable.sm_logout};


    int[] itians = {R.drawable.students, R.drawable.graduate};
    int[] itiImages = {R.drawable.about_ti, R.drawable.tracks, R.drawable.event, R.drawable.map, R.drawable.bus, R.drawable.announce};

    int[] myWork = {R.drawable.sm_eval, R.drawable.sm_working, R.drawable.schedule, R.drawable.schedule};
    FragmentManager fragmentManager;


    /*
    * chat part
    * */
    SharedPreferences sharedPreferences;
    String myType;
    String myId;
    String myName;
    String myChatId;
    DatabaseReference myRoot;
    int userType;
    int token;
    UserData userData;
    /*
    **/

    @Override
    protected void onStart() {
        super.onStart();


        //subscribe to my topic to receive notifications
        FirebaseMessaging.getInstance().subscribeToTopic(myChatId);
        FirebaseMessaging.getInstance().subscribeToTopic("events");

        this.myRoot = FirebaseDatabase.getInstance().getReference("users").child(myType);
        //listen for my node to save chat rooms
        myRoot.child(myChatId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object val = dataSnapshot.getValue();
                if (val == null)
                    myRoot.child(myChatId).setValue("");
                else if (val instanceof HashMap) {
                    HashMap<String, String> usersRoomsMap = (HashMap) val;
                    Map<String, ?> all = sharedPreferences.getAll();
                    //update the stored keys
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    for (String key : usersRoomsMap.keySet()) {
                        editor.putString(usersRoomsMap.get(key), key);
                    }
                    editor.apply();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        /*
        *
        * */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_side_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        home = (ImageView) findViewById(R.id.home);


        /*
        * chat part
        * */
        sharedPreferences = getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

        userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        token = sharedPreferences.getInt(Constants.USER_ID, 0);

        myName = userData.getEmployeeName();
        myId = token + "";
        int userType = this.userType;
        switch (userType) {
            case 1:
                myType = "student";
                break;
            case 2:
                myType = "staff";
                break;
        }

        myChatId = myType + "_" + myId;
        /*
        * */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        android.support.v7.app.ActionBarDrawerToggle toggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        ////for expandale
        /////////
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setGroupIndicator(null);
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem)
                    expListView.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.side_menu_header, expListView, false);


        TextView name = (TextView) headerView.findViewById(R.id.name);
        TextView track = (TextView) headerView.findViewById(R.id.track_name);
        ImageView avatar = (ImageView) headerView.findViewById(R.id.imageView);


        ////////////////////////////////////////////////////////
        //set name and track or company of the user
        name.setText(userData.getEmployeeName());
        track.setText(userData.getEmployeeBranchName());
        Picasso.with(getApplicationContext()).load(userData.getImagePath()).placeholder(R.drawable.ic_account_circle_white_48dp).into(avatar);


        // Add header view to the expandable list

        expListView.addHeaderView(headerView);

//        //////////////////////////sert the default
        fragment = new AboutIti();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
//        /////////////////////
        prepareListData();

        listAdapter = new CustomExpandableListAdapter(this, listDataHeader, listDataChild, images, myWork, itiImages, itians, 2);
        // setting list adapter
        expListView.setAdapter(listAdapter);
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.d("onGroupClick:", "worked");
                switch (groupPosition) {
                    case 1:
                        fragment = new ChatFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("receiver_type", "staff");
                        fragment.setArguments(bundle);
                        mDrawerLayout.closeDrawer(expListView);

                        break;

                    case 4:
                        //logout action
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
                        FirebaseMessaging.getInstance().unsubscribeFromTopic(myChatId);

                        Intent logIn = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(logIn);
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


                    case 3:

                        switch (childPosition) {
                            case 0:
                                //accesss students profile
                                BranchesFragment temp = new BranchesFragment();
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

                    case 2:
                        switch (childPosition) {
                            case 0:
                                //handle about ITI Fragment
                                fragment = new AboutIti();
                                break;
                            case 1:
                                //handle tracks fragment
                                //Toast.makeText(getApplicationContext(), "0,1", Toast.LENGTH_LONG).show();
                                fragment = new BranchesFragment();
                                break;
                            case 2:
                                //handle events fragment
                                fragment = new EventListFragment();
                                break;
                            case 3:
                                //handle maps fragment
                                fragment = new BranchesList();
                                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                                break;
                            case 4:
                                //handle bus services fragment
//                                fragment = new BranchesFragment();
                                break;

                            case 5:
                                fragment = new AnnouncementFragment();
                                break;
                            default:
                                break;
                        }
                        break;

                    case 1:
                        switch (childPosition) {
                            case 0:
                                //handle student community
                                Toast.makeText(getApplicationContext(), "1,0", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                //handle staff community
                                fragment = new ChatFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("receiver_type", "staff");
                                fragment.setArguments(bundle);
                                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                                break;
                            case 2:
                                //handle graduate community
                                Toast.makeText(getApplicationContext(), "1,2", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        break;

                    case 0:
                        switch (childPosition) {
                            case 0:
                                //handle staff evaluation fragment
                                fragment = new InstructorEvaluationFragment();
                                Toast.makeText(getApplicationContext(), "2,2", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                //handle scheduale fragment
                                fragment = new StaffSchedule();
                                break;
                            case 2:
                                //handle working hours fragment
                                fragment = new EmployeeHours();
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
        listDataHeader.add("My Work");
        listDataHeader.add("Community");
        listDataHeader.add("ITI");
        listDataHeader.add("ITIans");
        listDataHeader.add("Logout");

        // Adding child data
        List<String> iti = new ArrayList<String>();
        iti.add("About ITI");
        iti.add("Branches And Tracks");
        iti.add("Events");
        iti.add("Maps");
        iti.add("Bus Services");
        iti.add("Announcements");


        List<String> community = new ArrayList<String>();


        List<String> myWork = new ArrayList<String>();
        myWork.add("Evaluation");
        myWork.add("Schedule");
        myWork.add("Working hours");
        if(userData.getEmployeePlatformIntake()!=0)myWork.add("Notify schedule change");


        List<String> itians = new ArrayList<String>();
        itians.add("Students");
        itians.add("Graduates");


        List<String> logout = new ArrayList<String>();


        listDataChild.put(listDataHeader.get(0), myWork); // Header, Child data
        listDataChild.put(listDataHeader.get(1), community);
        listDataChild.put(listDataHeader.get(2), iti);
        listDataChild.put(listDataHeader.get(3), itians);
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