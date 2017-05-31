package com.iti.itiinhands.fragments.chat;


import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.chatAdapters.FriendListAdapter;
import com.iti.itiinhands.model.chat.ChatRoom;
import com.iti.itiinhands.services.FirebaseMessageReceiverService;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.iti.itiinhands.fragments.chat.ChatFragment.SP_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendlistFragment extends Fragment {


    private RecyclerView chatRoomsRecyclerView;
    private FriendListAdapter friendListAdapter;
    String myName;
    String myType = "staff";
    String myId;
    String myChatId;
    SharedPreferences sharedPreferences;
    private List<ChatRoom> chatRooms = new ArrayList<>();


    public FriendlistFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_firendlist, container, false);
        chatRoomsRecyclerView = (RecyclerView) v.findViewById(R.id.chatRooms);

        sharedPreferences = getActivity().getSharedPreferences(SP_NAME, MODE_PRIVATE);
        myName = sharedPreferences.getString("myName", null);
        myId = sharedPreferences.getString("myId", null);
        myChatId = myType + "_" + myId;

        //configure the recycler view
        friendListAdapter = new FriendListAdapter(getActivity(),
                chatRooms, R.layout.friends_list_cell, myId);
        chatRoomsRecyclerView.setAdapter(friendListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        chatRoomsRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(chatRoomsRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        chatRoomsRecyclerView.addItemDecoration(dividerItemDecoration);

        return v;
    }

    public void setData(List<ChatRoom> chatRooms){
        this.chatRooms.addAll(chatRooms);
        friendListAdapter.notifyDataSetChanged();
    }


}
