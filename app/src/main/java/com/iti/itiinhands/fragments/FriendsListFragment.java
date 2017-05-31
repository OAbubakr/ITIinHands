package com.iti.itiinhands.fragments;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.chatAdapters.FriendListAdapter;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.chat.ChatRoom;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.services.FirebaseMessageReceiverService;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsListFragment extends Fragment implements NetworkResponse {

    public static final String SP_NAME = "chatRoomsKeys";
    private String receiver_type;
    private List<ChatRoom> chatRooms = new ArrayList<>();
    private List<String> branchesNamesList;
    SharedPreferences sharedPreferences;
    private ArrayAdapter<String> branchesNamesAdapter;
    private boolean namesDownloaded = false;
    String myName;
    String myType = "staff";
    String myId;
    String myChatId;

    private RecyclerView chatRoomsRecyclerView;
    private FriendListAdapter friendListAdapter;
    private BroadcastReceiver receiver;

    private ProgressDialog progressDialog;

    public FriendsListFragment() {
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

        sharedPreferences.edit().putString("friendListActivityActive", "true").apply();
    }


    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        sharedPreferences.edit().putString("friendListActivityActive", "false").apply();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().putString("friendListActivityActive", "false").apply();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends_list, container, false);
        sharedPreferences = getActivity().getSharedPreferences(SP_NAME, MODE_PRIVATE);
        myName = sharedPreferences.getString("myName", null);
        myId = sharedPreferences.getString("myId", null);
        myChatId = myType + "_" + myId;

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

        branchesNamesList = new ArrayList<>();
        branchesNamesList.add("Branches");
        Spinner spinner = (Spinner) view.findViewById(R.id.branches_spinner);

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


        //retreive branches names and smart branch
        NetworkManager.getInstance(getActivity()).getBranchesNames(this);
    //    downloadList(1);

        return view;
    }

    private void downloadList(int id){

        switch (receiver_type) {
            case "staff":
                NetworkManager.getInstance(getActivity()).getInstructorsByBranch(this, id);

                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                break;

        }

    }

    @Override
    public void onResponse(Object response) {

        List<Object> data = (List)response;
        if(data != null){
            if(data.get(0) instanceof Branch){
                namesDownloaded = true;
                List<Branch> branchesNames = (List<Branch>) response;
                branchesNamesList.clear();
                for(Branch branch : branchesNames){
                    branchesNamesList.add(branch.getBranchName());
                }

                branchesNamesAdapter.notifyDataSetChanged();

            }else if(data.get(0) instanceof Instructor){
                progressDialog.hide();
                switch (receiver_type) {
                    case "staff":
                        chatRooms.clear();
                        List<Instructor> instructors = (List<Instructor>) response;

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
                            chatRooms.add(chatRoom);
                        }
                        friendListAdapter.notifyDataSetChanged();

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
}
