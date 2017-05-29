package com.iti.itiinhands.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.chatAdapters.ChatRoomAdapter;
import com.iti.itiinhands.model.chat.ChatMessage;

import static com.iti.itiinhands.fragments.FriendsListFragment.SP_NAME;

/**
 * Created by home on 5/22/2017.
 */
public class ChatRoomActivity extends AppCompatActivity {

    private Button sendMessage;
    private EditText message;
    private RecyclerView messagesRecyclerView;
    private ChatRoomAdapter adapter;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private SharedPreferences sharedPreferences;
    private DatabaseReference roomNode;
    private String roomKey;
    private String senderId;
    private String receiverId;
    private String receiverName;

    public RecyclerView getMessagesRecyclerView() {
        return messagesRecyclerView;
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences.edit().putString("chatRoomActive", null).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().putString("chatRoomActive", null).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        roomKey = getIntent().getStringExtra("roomKey");
        senderId = getIntent().getStringExtra("senderId");
        receiverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");

        sharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);
        sharedPreferences.edit().putString("chatRoomActive", receiverId).apply();

        setTitle(receiverName);

        roomNode = firebaseDatabase.getReference().getRoot().child("oneToOne").child(roomKey);

        sendMessage = (Button) findViewById(R.id.chatSendButton);
        message = (EditText) findViewById(R.id.messageEdit);
        messagesRecyclerView = (RecyclerView) findViewById(R.id.messagesContainer);


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageData = message.getText().toString();
                if (!messageData.isEmpty()) {
                    //check network status
                    ConnectivityManager cm =
                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    if (isConnected) {

                        ChatMessage chatMessage = new ChatMessage(messageData, senderId, receiverId, "sender name");

                        //create the message node
                        DatabaseReference messageNode = roomNode.push();
                        messageNode.setValue(chatMessage);

                        message.setText("");
                    } else {
                        Toast.makeText(ChatRoomActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        adapter = new ChatRoomAdapter(this,
                ChatMessage.class,
                R.layout.chat_bubble,
                ChatRoomAdapter.MyViewHolder.class,
                firebaseDatabase.getReference().getRoot().child("oneToOne").child(roomKey),
                senderId);

        messagesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesRecyclerView.setAdapter(adapter);
    }

}
