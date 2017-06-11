package com.iti.itiinhands.fragments.chat;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.chatAdapters.ChatPagerAdapter;
import com.iti.itiinhands.adapters.chatAdapters.FriendListAdapter;
import com.iti.itiinhands.adapters.chatAdapters.RecentChatsListAdapter;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.Instructor;
import com.iti.itiinhands.model.RenewAccessTokenObject;
import com.iti.itiinhands.model.RenewTokenResponse;
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
import static com.iti.itiinhands.fragments.chat.ChatFragment.SP_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatMainFragment extends Fragment implements NetworkResponse {


    private String receiver_type;
    private String myName;
    private String myType;
    private String myId;
    private String myChatId;
    //  private SharedPreferences sharedPreferences;
    //    private RecyclerView chatRoomsRecyclerView;
    private FriendListAdapter friendListAdapter;
    private RecentChatsListAdapter recentChatsAdapter;

    private List<ChatRoom> allChatRooms = new ArrayList<>();
    private List<ChatRoom> chatRooms = new ArrayList<>();
    private List<ChatRoom> recentChatRooms = new ArrayList<>();

    private int userType;
    private int token;
    private UserData userData;
    private BroadcastReceiver receiver;
    private ViewPager viewPager;
    private MenuItem searchViewItem;
    //


    public ChatMainFragment() {
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

        friendListAdapter.notifyDataSetChanged();
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

        View view = inflater.inflate(R.layout.fragment_chat_main, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("CONTACTS"));
        tabLayout.addTab(tabLayout.newTab().setText("RECENT CHATS"));

        getActivity().setTitle("Community");

        setHasOptionsMenu(true);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);

        userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
        userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        token = sharedPreferences.getInt(Constants.USER_ID, 0);

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

        viewPager = (ViewPager) view.findViewById(R.id.pager);

        friendListAdapter = new FriendListAdapter(getActivity(), chatRooms, R.layout.friends_list_cell, myId);
        recentChatsAdapter = new RecentChatsListAdapter(getActivity(), recentChatRooms, R.layout.friends_list_cell, myId);

        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setListAdapter(friendListAdapter);
        chatFragment.setDataList(chatRooms);
        chatFragment.setAllDataList(allChatRooms);

        RecentChatsFragment recentChatsFragment = new RecentChatsFragment();
        recentChatsFragment.setListAdapter(recentChatsAdapter);
        recentChatsFragment.setRecentDataList(recentChatRooms);
        recentChatsFragment.setAllDataList(allChatRooms);

        final ChatPagerAdapter adapter = new ChatPagerAdapter
                (getActivity().getSupportFragmentManager(), chatFragment, recentChatsFragment, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


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

                            if (recentChatRooms.contains(chatRoom)) {
                                recentChatRooms.remove(chatRoom);
                                recentChatRooms.add(0, chatRoom);
                            }

                            break;

                        }
                    }
/*
                    friendListAdapter.notifyDataSetChanged();
                    recentChatsAdapter.notifyDataSetChanged();
*/
                    friendListAdapter.updateData(chatRooms);
                    recentChatsAdapter.updateData(recentChatRooms);
                }

            }
        };

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                MenuItemCompat.collapseActionView(searchViewItem);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        downloadList(-1, Integer.parseInt(myId));

        return view;
    }

    public void downloadList(int id, int excludeId) {

        switch (receiver_type) {
            case "staff":
                NetworkManager.getInstance(getActivity()).getInstructorsByBranch(this, id, excludeId);
        }

    }

    @Override
    public void onResponse(Response response) {

        if (response != null) {
            if (response.getStatus().equals(Response.SUCCESS)) {

                if (response instanceof RenewTokenResponse) {
                    RenewTokenResponse renewTokenResponse = (RenewTokenResponse) response;
                    RenewAccessTokenObject renewAccessTokenObject = renewTokenResponse.getData();
                    String accessToken = renewAccessTokenObject.getAccessToken();
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                    sharedPreferences.edit().putString(Constants.TOKEN, accessToken).apply();

                    //resend the request
                    downloadList(-1, Integer.parseInt(myId));

                } else {
                //    LinkedTreeMap linkedTreeMap = (LinkedTreeMap) response.getResponseData();

                    switch (receiver_type) {
                        case "staff":
                            List<Instructor> instructors = DataSerializer.convert(response.getResponseData(),
                                    new TypeToken<List<Instructor>>() {
                                    }.getType());
                            chatRooms.clear();
                            SharedPreferences local = getActivity().getSharedPreferences(ChatFragment.SP_NAME, MODE_PRIVATE);
                            for (Instructor instructor : instructors) {
                                ChatRoom chatRoom = new ChatRoom();


                                chatRoom.setReceiverName(instructor.getInstructorName());

                                chatRoom.setReceiverType(receiver_type);

                                String receiverId = receiver_type + "_" + String.valueOf(instructor.getInstuctorId());
                                chatRoom.setReceiverId(receiverId);

                                String roomKey = local.getString(receiverId, null);
                                chatRoom.setRoomKey(roomKey);


                                chatRoom.setSenderId(myChatId);
                                chatRoom.setSenderName(myName);

                                chatRoom.setBranchName(instructor.getBranchName());

                                allChatRooms.add(chatRoom);
                                chatRooms.add(chatRoom);

                                if (chatRoom.getRoomKey() != null)
                                    recentChatRooms.add(chatRoom);


//                            if (recentUsers.contains(receiverId)) {
//                                recentChatRooms.add(chatRoom);
//                            }

                            }

                            //   topProgressBar.setVisibility(View.GONE);
                            //      branchesTagsAdapter.notifyDataSetChanged();

                            friendListAdapter.updateData(chatRooms);
                            recentChatsAdapter.updateData(recentChatRooms);
                            break;
                    }
                }
            } else if (response.getStatus().equals(Response.FAILURE)) {

                if (response.getError().equals(Response.EXPIRED_ACCESS_TOKEN)) {

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                    String refreshToken = sharedPreferences.getString(Constants.REFRESH_TOKEN, "");
                    NetworkManager.getInstance(getActivity()).renewAccessToken(this, refreshToken);

                } else if (response.getError().equals(Response.EXPIRED_REFRESH_TOKEN)) {

                } else if (response.getError().equals(Response.INVALID_ACCESS_TOKEN)) {

                } else if (response.getError().equals(Response.INVALID_REFRESH_TOKEN)) {

                }


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

        } else {
            friendListAdapter.getProgressBar().setVisibility(View.GONE);
            recentChatsAdapter.getProgressBar().setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure() {
        friendListAdapter.getProgressBar().setVisibility(View.GONE);
        recentChatsAdapter.getProgressBar().setVisibility(View.GONE);

        Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
        System.out.println("error");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        inflater.inflate(R.menu.search_action_button, menu);
        searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchViewAndroidActionBar.setQueryHint("Search for friends or branches...");
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                int tab = viewPager.getCurrentItem();
                switch (tab) {
                    case 0:
                        friendListAdapter.filter(query);
                        break;
                    case 1:
                        recentChatsAdapter.filter(query);
                        break;
                }

//                recentChatsListAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                int tab = viewPager.getCurrentItem();
                switch (tab) {
                    case 0:
                        friendListAdapter.filter(query);
                        break;
                    case 1:
                        recentChatsAdapter.filter(query);
                        break;
                }

//                recentChatsListAdapter.filter(newText);
                return true;
            }
        });


    }


}
