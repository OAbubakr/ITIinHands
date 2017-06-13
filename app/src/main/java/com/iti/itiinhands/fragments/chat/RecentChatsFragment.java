package com.iti.itiinhands.fragments.chat;


import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.chatAdapters.RecentChatsListAdapter;
import com.iti.itiinhands.model.chat.ChatRoom;

import java.util.List;
import java.util.Map;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentChatsFragment extends Fragment {
    //    private String receiver_type;
//    private String myName;
//    private String myType;
//    private String myId;
//    private String myChatId;
    private SharedPreferences sharedPreferences;
    private RecyclerView chatRoomsRecyclerView;
    private RecentChatsListAdapter recentChatsListAdapter;
    private List<ChatRoom> allChatRooms;
    private List<ChatRoom> recentChatRooms;
    //    private int userType;
//    private int token;
//    private UserData userData;
    private ProgressBar progressBar;
    //  private BranchesTagsAdapter branchesTagsAdapter;
//    private RecyclerView branchesTagsRecyclerView;
//    private List<BranchTag> branches = new ArrayList<>();
    private BroadcastReceiver receiver;

    public RecentChatsFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

        if (recentChatRooms != null) {

            if (allChatRooms != null) {
                recentChatRooms.clear();
                SharedPreferences local = getActivity().getSharedPreferences(ChatFragment.SP_NAME, MODE_PRIVATE);
                Map<String, ?> all = local.getAll();
                for (ChatRoom chatRoom : allChatRooms) {

                    String receiverId = chatRoom.getReceiverId();

                    String room = local.getString(chatRoom.getReceiverId(), null);

                    if (room != null) {
                        recentChatRooms.add(chatRoom);
                    }


                }
                if(recentChatsListAdapter != null)
                    recentChatsListAdapter.notifyDataSetChanged();

            }


        }

    }

    /* @Override
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

         */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Community");

        View view = inflater.inflate(R.layout.fragment_recent_chats, container, false);


        chatRoomsRecyclerView = (RecyclerView) view.findViewById(R.id.chatRooms);
        chatRoomsRecyclerView.setAdapter(recentChatsListAdapter);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        recentChatsListAdapter.setProgressBar(progressBar);
/*        topProgressBar = (MaterialProgressBar) view.findViewById(R.id.h_progressBar);

        sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

        userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        token = sharedPreferences.getInt(Constants.TOKEN, 0);

        myName = userData.getName();
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
*/

        //configure the recycler view
//        friendListAdapter = new FriendListAdapter(getActivity(), chatRooms, R.layout.friends_list_cell, myId, topProgressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        chatRoomsRecyclerView.setLayoutManager(linearLayoutManager);

        //     getActivity().setTitle(myName);

/*
        branchesTagsRecyclerView = (RecyclerView) view.findViewById(R.id.tagsRecycler);
        //render the brands recycler view
        branchesTagsRecyclerView = (RecyclerView) view.findViewById(R.id.tagsRecycler);
        branchesTagsAdapter = new BranchesTagsAdapter(getActivity(),
                branches, R.layout.branch_tag_view, this, myId);

        branchesTagsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        branchesTagsRecyclerView.setAdapter(branchesTagsAdapter);
*/

        //   NetworkManager.getInstance(getActivity()).getBranchesNames(this);

        //      downloadList("", -1, Integer.parseInt(myId));
/*
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (chatRooms != null) {
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

            }
        };
*/

        return view;
    }


    public void setAllDataList(List<ChatRoom> chatRooms) {
        this.allChatRooms = chatRooms;
    }

    public void setRecentDataList(List<ChatRoom> chatRooms) {
        this.recentChatRooms = chatRooms;
    }

    public void setListAdapter(RecentChatsListAdapter recentChatsListAdapter) {
        this.recentChatsListAdapter = recentChatsListAdapter;
    }


    /*
    public void downloadList(String token, int id, int excludeId) {

        topProgressBar.setVisibility(View.VISIBLE);
        switch (receiver_type) {
            case "staff":
                NetworkManager.getInstance(getActivity()).getInstructorsByBranch(this, token, id, excludeId);
        }

    }

    @Override
    public void onResponse(Response response) {

        if (response != null) {
            if(response.getStatus().equals(Response.SUCCESS)){
                LinkedTreeMap linkedTreeMap = (LinkedTreeMap) response.getResponseData();
//                List<Instructor> instructorss= linkedTreeMap.get("responseData");



                switch (receiver_type) {
                    case "staff":
                        List<Instructor> instructors = DataSerializer.convert(linkedTreeMap.get("responseData"),
                                new TypeToken<List<Instructor>>(){}.getType());
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

                        topProgressBar.setVisibility(View.GONE);
                        //      branchesTagsAdapter.notifyDataSetChanged();
                        friendListAdapter.updateData();
                        break;
                }
            }


        }else{
            topProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {
        topProgressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
        System.out.println("error");
    }
*/
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        inflater.inflate(R.menu.search_action_button, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setQueryHint("Search for friends or branches...");
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recentChatsListAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recentChatsListAdapter.filter(newText);
                return true;
            }
        });


    }*/

}