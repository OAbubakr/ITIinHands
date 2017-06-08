package com.iti.itiinhands.fragments.chat;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.StaffSideMenuActivity;
import com.iti.itiinhands.adapters.chatAdapters.FriendListAdapter;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.model.chat.ChatRoom;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.services.FirebaseMessageReceiverService;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.DataSerializer;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements NetworkResponse {

    public static final String SP_NAME = "chatRoomsKeys";
    private String receiver_type;
//    private boolean namesDownloaded = false;

    String myName;
    String myType;
    String myId;
    String myChatId;
    SharedPreferences sharedPreferences;
    private RecyclerView chatRoomsRecyclerView;
    private FriendListAdapter friendListAdapter;
    private BroadcastReceiver receiver;
    List<ChatRoom> chatRooms = new ArrayList<>();
    int userType;
    int token;
    UserData userData;

    private ProgressDialog progressDialog;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.receiver_type = args.getString("receiver_type");
    }


    @Override
    public void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver((receiver),
                new IntentFilter(FirebaseMessageReceiverService.MESSAGE_RECEIVED_ACTION));

        getActivity().getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .edit().putString("friendListActivityActive", "true").apply();
    }


    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        getActivity().getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .edit().putString("friendListActivityActive", "false").apply();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getSharedPreferences(SP_NAME, MODE_PRIVATE).
                edit().putString("friendListActivityActive", "false").apply();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        Toolbar tb = (Toolbar) view.findViewById(R.id.toolbar);
        ((StaffSideMenuActivity)getActivity()).setSupportActionBar(tb);
        setHasOptionsMenu(true);
//        ActionBar ab = ((MainActivity2)getActivity()).getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
//        ab.setDisplayHomeAsUpEnabled(true);


        chatRoomsRecyclerView = (RecyclerView) view.findViewById(R.id.chatRooms);

        sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

        userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        token = sharedPreferences.getInt(Constants.USER_ID,0);

        myName = userData.getName();
        myId = token+"";
        int userType = this.userType;
        switch (userType){
            case 1:
                myType = "student";
                break;
            case 2:
                myType = "staff";
                break;
        }

        myChatId = myType + "_" + myId;

        //configure the recycler view
        friendListAdapter = new FriendListAdapter(getActivity(), chatRooms, R.layout.friends_list_cell, myId);
        chatRoomsRecyclerView.setAdapter(friendListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        chatRoomsRecyclerView.setLayoutManager(linearLayoutManager);

        progressDialog = new ProgressDialog(getActivity());

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String receiverId = intent.getStringExtra("receiverId");

                for (ChatRoom chatRoom : chatRooms) {
                    if (chatRoom.getReceiverId().equals(receiverId)) {
                        chatRoom.setHasPendingMessages(true);

                        chatRooms.remove(chatRoom);
                        chatRooms.add(0, chatRoom);
                        break;
                    }
                }
                friendListAdapter.notifyDataSetChanged();
            }
        };

        getActivity().setTitle(myName);

        downloadList(-1, Integer.parseInt(myId));

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("");
//        getActivity().getSupportFragmentManager().beginTransaction().remove(fragmentFriendsList).commit();
//        getActivity().getSupportFragmentManager().beginTransaction().remove(fragmentRecentChats).commit();
    }

    private void downloadList(int id, int excludeId){

        switch (receiver_type) {
            case "staff":
                NetworkManager.getInstance(getActivity()).getInstructorsByBranch(this, id, excludeId);

                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                break;

        }

    }

    @Override
    public void onResponse(Response response) {

//        List<Object> data = (List)response.getResponseData();
        List<Instructor> instructors = DataSerializer.convert(response.getResponseData(),new TypeToken<List<Instructor>>(){}.getType());

        if(instructors != null){

            if(instructors.get(0) instanceof Instructor){
                progressDialog.hide();
                switch (receiver_type) {
                    case "staff":
//                        List<Instructor> instructors = (List<Instructor>) response;
                        chatRooms.clear();

                        for (Instructor instructor : instructors) {
                            ChatRoom chatRoom = new ChatRoom();

                            chatRoom.setReceiverName(instructor.getInstructorName());

                            chatRoom.setReceiverType(receiver_type);

                            String receiverId = receiver_type + "_" + String.valueOf(instructor.getInstuctorId());
                            chatRoom.setReceiverId(receiverId);

                            String roomKey = sharedPreferences.getString(receiverId, null);
                            chatRoom.setRoomKey(roomKey);

                            chatRoom.setSenderId(myChatId);
                            chatRoom.setSenderName(myName);

                            chatRoom.setBranchName(instructor.getBranchName());

                            chatRooms.add(chatRoom);
                        }
                        friendListAdapter.updateData();
                        break;
                }
            }
        }


    }

    @Override
    public void onFailure() {
        progressDialog.hide();
        Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
        System.out.println("error");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        inflater.inflate(R.menu.search_action_button, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                friendListAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                friendListAdapter.filter(newText);
                return true;
            }
        });


    }


}

/*
        chatRoomsRecyclerView = (RecyclerView) view.findViewById(R.id.chatRooms);

        //configure the recycler view
        friendListAdapter = new FriendListAdapter(getActivity(),
                chatRooms, R.layout.friends_list_cell, myId);
        chatRoomsRecyclerView.setAdapter(friendListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        chatRoomsRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(chatRoomsRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        chatRoomsRecyclerView.addItemDecoration(dividerItemDecoration);

*/
//    branchesNamesList = new ArrayList<>();
//    branchesNamesList.add("Branches");
//    Spinner spinner = (Spinner) view.findViewById(R.id.branches_spinner);

        /*
        branchesNamesAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item, branchesNamesList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0 & !namesDownloaded)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0 & !namesDownloaded){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
*/
        /*

        branchesNamesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(branchesNamesAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0://smart
                        downloadList(1);
                        break;
                    case 1://alex
                        downloadList(7);
                        break;
                    case 2://assuit
                        downloadList(8);
                        break;
                    case 3://mansoura
                        downloadList(9);
                        break;
                    case 4://ismailia
                        downloadList(10);
                        break;
                    case 5://elnozha
                        downloadList(11);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/
//        NetworkManager.getInstance(getActivity()).getBranchesNames(this);



/*
            if(data.get(0) instanceof Branch){
                namesDownloaded = true;
                List<Branch> branchesNames = (List<Branch>) response;
                branchesNamesList.clear();
                for(Branch branch : branchesNames){
                    branchesNamesList.add(branch.getBranchName());
                }

                branchesNamesAdapter.notifyDataSetChanged();

            }else */