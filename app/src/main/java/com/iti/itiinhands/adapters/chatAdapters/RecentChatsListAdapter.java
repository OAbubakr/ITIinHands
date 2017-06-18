package com.iti.itiinhands.adapters.chatAdapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.ChatRoomActivity;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.chat.ChatFragment;
import com.iti.itiinhands.model.chat.ChatRoom;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.ArrayList;
import java.util.List;

import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

import static android.content.Context.MODE_PRIVATE;

public class RecentChatsListAdapter extends RecyclerView.Adapter<RecentChatsListAdapter.FriendsViewHolder> {

    private Context context;
    private List<ChatRoom> recentChatRooms = new ArrayList<>();
    private List<ChatRoom> recentChatRoomsCopy = new ArrayList<>();
    private int cellToInflate;
 //   private SharedPreferences sharedPreferences;
    private String id;

    //firebase references
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference oneToOneRoot = firebaseDatabase.getReference("oneToOne");
    DatabaseReference myRoot, receiverRoot;

    private String roomKey;
    private DatabaseReference chatRoomNode;

    String myType;
    String myId;
    String myChatId;



    private ProgressBar progressBar;



    public RecentChatsListAdapter(Context context, List<ChatRoom> chatRooms, int cellToInflate, String id) {

        this.context = context;
        this.recentChatRooms = chatRooms;
        this.cellToInflate = cellToInflate;
        this.id = id;


        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        int userType = sharedPreferences.getInt(Constants.USER_TYPE, 0);
        UserData userData = UserDataSerializer.deSerialize(sharedPreferences.getString(Constants.USER_OBJECT, ""));
        myId = String.valueOf(userData.getId());
        switch (userType) {
            case 1:
                myType = "student";
                break;
            case 2:
                myType = "staff";
                break;
        }
        myChatId = myType + "_" + myId;

        myRoot = firebaseDatabase.getReference("users").child(myType);
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(cellToInflate, parent, false);
        return new FriendsViewHolder(view);
    }



    public void filter(String text) {

        recentChatRooms.clear();
        if (text.isEmpty()) {
            recentChatRooms.addAll(recentChatRoomsCopy);
        } else {
            text = text.toLowerCase();
            for (ChatRoom item : recentChatRoomsCopy) {
                if (item.getReceiverName().toLowerCase().contains(text) | item.getBranchName().toLowerCase().contains(text)) {
                    recentChatRooms.add(item);
                }
            }
        }
        notifyDataSetChanged();

    }


    @Override
    public void onBindViewHolder(FriendsViewHolder holder, final int position) {

        final ChatRoom chatRoom = recentChatRooms.get(position);
        holder.getName().setText(chatRoom.getReceiverName());

        if (chatRoom.getPendingMessagesCount() > 0){
            holder.getMessage_image().setText(String.valueOf(chatRoom.getPendingMessagesCount()));
            holder.getMessage_image().setVisibility(View.VISIBLE);
        }
        else
            holder.getMessage_image().setVisibility(View.INVISIBLE);

        holder.getImageLoader().loadImage(holder.getAvatarView(), chatRoom.getReceiverImagePath()
                , chatRoom.getReceiverName());

        holder.getBranchName().setText(chatRoom.getBranchName());

        //onclick listener
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomKey = context.getSharedPreferences(ChatFragment.SP_NAME, MODE_PRIVATE).getString(chatRoom.getReceiverId(), null);
/*
                boolean isConnected = NetworkManager.getInstance(context).isOnline();

                if(roomKey == null){
                    if(isConnected){
                        progressBar.setVisibility(View.VISIBLE);
                        //check first no chat room exists under my node
                        myRoot.child(myChatId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Object val = dataSnapshot.getValue();
                                String roomKey = null;
                                if (val instanceof HashMap) {
                                    HashMap<String, String> usersRoomsMap = (HashMap) val;
                                    for (String key : usersRoomsMap.keySet()) {
                                        if (usersRoomsMap.get(key).equals(chatRoom.getReceiverId())) {
                                            roomKey = key;
                                            break;
                                        }
                                    }
                                }


                                chatRoom.setHasPendingMessages(false);
                                if (roomKey == null) {
                                    createChatRoom(chatRoom);
                                    launchChatRoom(chatRoom);

                                } else {
                                    chatRoom.setRoomKey(roomKey);
                                    context.getSharedPreferences(ChatFragment.SP_NAME, MODE_PRIVATE).edit().putString(chatRoom.getReceiverId(), roomKey).apply();
                                    launchChatRoom(chatRoom);

                                }
                                notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }else{
                        Toast.makeText(context, "Check your connection", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    chatRoom.setHasPendingMessages(false);
                    chatRoom.setRoomKey(roomKey);
                    launchChatRoom(chatRoom);
                    notifyDataSetChanged();
                }
                */
                if (NetworkManager.getInstance(context).isOnline()) {
                    if (roomKey != null) {
                        chatRoom.setPendingMessagesCount(0);
                        chatRoom.setRoomKey(roomKey);
                        launchChatRoom(chatRoom);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Invalid room key", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(context, "Failed, please check you internet connection.", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

/*
    private void createChatRoom(ChatRoom chatRoom) {

        //create the chat node
        chatRoomNode = oneToOneRoot.push();
        roomKey = chatRoomNode.getKey();
        oneToOneRoot.child(roomKey).setValue("");

        chatRoom.setRoomKey(roomKey);
        context.getSharedPreferences(ChatFragment.SP_NAME, MODE_PRIVATE).edit().putString(chatRoom.getReceiverId(), roomKey).apply();

        //create the chat node map
        Map<String, Object> map = new HashMap<>();
        map.put(roomKey, chatRoom.getSenderId());

        //add the chat node under the specified node
        //get the user's node
        receiverRoot = firebaseDatabase.getReference("users").child(chatRoom.getReceiverType());
        DatabaseReference friendsNode = receiverRoot.getRef().child(chatRoom.getReceiverId());
        //add the chat room
        friendsNode.updateChildren(map);

        //add the chat room under my node
        DatabaseReference myNode = myRoot.getRef().child(chatRoom.getSenderId());
        //push the chat room
        map.remove(roomKey);
        map.put(roomKey, chatRoom.getReceiverId());
        myNode.updateChildren(map);
    }

*/
    private void launchChatRoom(ChatRoom chatRoom) {
        Intent intent = new Intent(context, ChatRoomActivity.class);
        intent.putExtra("roomKey", chatRoom.getRoomKey());
        intent.putExtra("senderId", chatRoom.getSenderId());
        intent.putExtra("receiverId", chatRoom.getReceiverId());
        intent.putExtra("receiverName", chatRoom.getReceiverName());

        context.startActivity(intent);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return recentChatRooms.size();
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    class FriendsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView branchName;
        private Button message_image;
        private View view;
        private AvatarView avatarView;
        private IImageLoader imageLoader;

        FriendsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.friend_name);
            this.view = itemView.findViewById(R.id.cell);;
            this.message_image = (Button) itemView.findViewById(R.id.message_image);
            this.avatarView = (AvatarView) itemView.findViewById(R.id.avatar);
            this.imageLoader = new PicassoLoader();
            this.branchName = (TextView) itemView.findViewById(R.id.branch_name_text);
        }

        TextView getName() {
            return name;
        }

        View getView() {
            return view;
        }

        Button getMessage_image() {
            return message_image;
        }

        public AvatarView getAvatarView() {
            return avatarView;
        }

        public IImageLoader getImageLoader() {
            return imageLoader;
        }

        public TextView getBranchName() {
            return branchName;
        }

        public void setBranchName(TextView branchName) {
            this.branchName = branchName;
        }

    }


    public void updateData(List<ChatRoom> data){
        this.recentChatRoomsCopy.clear();
        this.recentChatRoomsCopy.addAll(data);
        notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

}
