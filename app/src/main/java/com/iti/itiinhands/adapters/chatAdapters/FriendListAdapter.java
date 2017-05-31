package com.iti.itiinhands.adapters.chatAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.ChatRoomActivity;
import com.iti.itiinhands.model.chat.ChatRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import agency.tango.android.avatarview.IImageLoader;
import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;

import static android.content.Context.MODE_PRIVATE;
import static com.iti.itiinhands.fragments.chat.ChatFragment.SP_NAME;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendsViewHolder> {

    private Context context;
    private List<ChatRoom> chatRooms = new ArrayList<>();
    private List<ChatRoom> chatRoomsCopy = new ArrayList<>();
    private int cellToInflate;
    private SharedPreferences sharedPreferences;
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




    public FriendListAdapter(Context context, List<ChatRoom> chatRooms, int cellToInflate, String id) {

        this.context = context;
        this.chatRooms = chatRooms;
        this.cellToInflate = cellToInflate;
        this.id = id;
        sharedPreferences = context.getSharedPreferences("userData", MODE_PRIVATE);
        int userType = sharedPreferences.getInt("userType", -1);
        switch (userType){
            case 1:
                myType = "student";
                break;
            case 2:
                myType = "staff";
                break;
        }

        myRoot = firebaseDatabase.getReference("users").child(myType);

        myId = sharedPreferences.getString("myId", null);
        myChatId = myType + "_" + myId;

    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(cellToInflate, parent, false);
        return new FriendsViewHolder(view);
    }



    public void filter(String text) {

        chatRooms.clear();
        if (text.isEmpty()) {
            chatRooms.addAll(chatRoomsCopy);
        } else {
            text = text.toLowerCase();
            for (ChatRoom item : chatRoomsCopy) {
                if (item.getReceiverName().toLowerCase().contains(text)) {
                    chatRooms.add(item);
                }
            }
        }
        notifyDataSetChanged();

    }


    @Override
    public void onBindViewHolder(FriendsViewHolder holder, final int position) {

        final ChatRoom chatRoom = chatRooms.get(position);
        holder.getName().setText(chatRoom.getReceiverName());

        if (chatRoom.isHasPendingMessages())
            holder.getMessage_image().setVisibility(View.VISIBLE);
        else
            holder.getMessage_image().setVisibility(View.INVISIBLE);

        holder.getImageLoader().loadImage(holder.getAvatarView(), chatRoom.getReceiverImagePath()
                , chatRoom.getReceiverName());

        //onclick listener
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomKey = sharedPreferences.getString(chatRoom.getReceiverId(), null);

                //check network status
                ConnectivityManager cm =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();


                if(roomKey == null){


                    if(isConnected){

                        final ProgressDialog progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();

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
                                    if(progressDialog.isShowing()) {
                                        progressDialog.hide();
                                        launchChatRoom(chatRoom);
                                    }
                                } else {
                                    chatRoom.setRoomKey(roomKey);
                                    sharedPreferences.edit().putString(chatRoom.getReceiverId(), roomKey).apply();
                                    if(progressDialog.isShowing()) {
                                        progressDialog.hide();
                                        launchChatRoom(chatRoom);
                                    }
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

            }

        });
    }


    private void createChatRoom(ChatRoom chatRoom) {

        //create the chat node
        chatRoomNode = oneToOneRoot.push();
        roomKey = chatRoomNode.getKey();
        oneToOneRoot.child(roomKey).setValue("");

        chatRoom.setRoomKey(roomKey);
        sharedPreferences.edit().putString(chatRoom.getReceiverId(), roomKey).apply();

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


    private void launchChatRoom(ChatRoom chatRoom) {
        Intent intent = new Intent(context, ChatRoomActivity.class);
        intent.putExtra("roomKey", chatRoom.getRoomKey());
        intent.putExtra("senderId", chatRoom.getSenderId());
        intent.putExtra("receiverId", chatRoom.getReceiverId());
        intent.putExtra("receiverName", chatRoom.getReceiverName());

        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return chatRooms.size();
    }


    class FriendsViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView message_image;
        private View view;
        private AvatarView avatarView;
        private IImageLoader imageLoader;

        FriendsViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.friend_name);
            this.view = itemView.findViewById(R.id.cell);;
            this.message_image = (ImageView) itemView.findViewById(R.id.message_image);
            this.avatarView = (AvatarView) itemView.findViewById(R.id.avatar);
            this.imageLoader = new PicassoLoader();

        }

        TextView getName() {
            return name;
        }

        View getView() {
            return view;
        }

        ImageView getMessage_image() {
            return message_image;
        }

        public AvatarView getAvatarView() {
            return avatarView;
        }

        public IImageLoader getImageLoader() {
            return imageLoader;
        }


    }


    public void updateData(){
        this.chatRoomsCopy.clear();
        this.chatRoomsCopy.addAll(chatRooms);
        notifyDataSetChanged();

    }
}
