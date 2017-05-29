package com.iti.itiinhands.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.chatAdapters.FriendListAdapter;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.chat.ChatRoom;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.services.FirebaseMessageReceiverService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsListFragment extends Fragment implements NetworkResponse{

    public static final String SP_NAME = "chatRoomsKeys";
    private String receiver_type;
    private List<ChatRoom> chatRooms = new ArrayList<>();
    SharedPreferences sharedPreferences;

    String myName = "my name";
    String myType = "student";
    String myId = "123456";
    String myChatId = myType + "_" + myId;

    private RecyclerView chatRoomsRecyclerView;
    private FriendListAdapter friendListAdapter;
    private BroadcastReceiver receiver;

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
        //new FriendListAdapter(getActivity(), )
        chatRoomsRecyclerView.setAdapter(friendListAdapter);
        chatRoomsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        switch (receiver_type) {
            case "staff":
                NetworkManager.getInstance(getActivity()).getInstructorsByBranch(this, -1);
                break;
        }


        return view;
    }

    @Override
    public void onResponse(Object response) {

        switch (receiver_type) {
            case "staff":
                List<Instructor> instructors = (List<Instructor>) response;

                for(Instructor instructor : instructors){
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

    @Override
    public void onFailure() {
        System.out.println("error");
    }
}
