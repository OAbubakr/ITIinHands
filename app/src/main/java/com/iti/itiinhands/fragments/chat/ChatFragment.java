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
import com.iti.itiinhands.adapters.chatAdapters.FriendListAdapter;
import com.iti.itiinhands.model.chat.ChatRoom;

import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    public static final String SP_NAME = "chatRoomsKeys";
//    private String receiver_type;
//    private String myName;
//    private String myType;
//    private String myId;
//    private String myChatId;
    private SharedPreferences sharedPreferences;
    private RecyclerView chatRoomsRecyclerView;
    private FriendListAdapter friendListAdapter;
    private List<ChatRoom> allChatRooms, chatRooms;


    //    private int userType;
//    private int token;
//    private UserData userData;
    private MaterialProgressBar  h_progressBar;
    private ProgressBar progressBar;
  //  private BranchesTagsAdapter branchesTagsAdapter;
//    private RecyclerView branchesTagsRecyclerView;
//    private List<BranchTag> branches = new ArrayList<>();
    private BroadcastReceiver receiver;

    public ChatFragment() {
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
    public void onStart() {
        super.onStart();
        System.out.println();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_chat_friends, container, false);

//        setHasOptionsMenu(true);


        chatRoomsRecyclerView = (RecyclerView) view.findViewById(R.id.chatRooms);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        h_progressBar = (MaterialProgressBar) view.findViewById(R.id.h_progressBar);
        friendListAdapter.setProgressBar(progressBar, h_progressBar);
        chatRoomsRecyclerView.setAdapter(friendListAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        chatRoomsRecyclerView.setLayoutManager(linearLayoutManager);

/*
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


    public void setDataList(List<ChatRoom> chatRooms){
        this.chatRooms = chatRooms;
    }

    public void setListAdapter(FriendListAdapter friendListAdapter){
        this.friendListAdapter = friendListAdapter;
    //    chatRoomsRecyclerView.setAdapter(friendListAdapter);
    }


    public void setAllDataList(List<ChatRoom> chatRooms) {
        this.allChatRooms = chatRooms;
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
                friendListAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                friendListAdapter.filter(newText);
                return true;
            }
        });


    }*/

/*
    public class BranchTag {

        private Branch branch;
        private boolean pressed;

        public Branch getBranch() {
            return branch;
        }

        public void setBranch(Branch branch) {
            this.branch = branch;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

        public BranchTag(Branch branch, boolean pressed) {
            this.branch = branch;
            this.pressed = pressed;
        }
    }

*/

}

//    List<Object> data = (List) response;
            /*if (data.get(0) instanceof Branch) {
                List<Branch> branchesList = (List<Branch>) response;
                if (branchesList.size() > 0) {
                    for (Branch branch : branchesList) {
                        branches.add(new ChatFragment.BranchTag(branch, false));
                    }

                    if (branches.get(0).getBranch().getBranchName().toLowerCase().contains("smart village")) {
                        if (branches.get(1) != null) {
                            ChatFragment.BranchTag branch = branches.remove(1);
                            branches.add(0, branch);
                        }
                    }

                    branches.get(0).setPressed(true);
                    branchesTagsAdapter.notifyDataSetChanged();
                    downloadList(branches.get(0).getBranch().getBranchId(), Integer.parseInt(myId));

                }
            } */
