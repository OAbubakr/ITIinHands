package com.iti.itiinhands.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.chatAdapters.ChatRoomAdapter;
import com.iti.itiinhands.model.chat.ChatMessage;

import static com.iti.itiinhands.fragments.chat.ChatFragment.SP_NAME;

/**
 * Created by home on 5/22/2017.
 */

public class ChatRoomActivity extends AppCompatActivity {

    private FloatingActionButton sendMessage;
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
    private String myName;

    public RecyclerView getMessagesRecyclerView() {
        return messagesRecyclerView;
    }

    @Override
    protected void onStop() {
        super.onStop();
        getApplicationContext().getSharedPreferences(SP_NAME, MODE_PRIVATE).edit().putString("chatRoomActive", null).apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplicationContext().getSharedPreferences(SP_NAME, MODE_PRIVATE).edit().putString("chatRoomActive", null).apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        roomKey = getIntent().getStringExtra("roomKey");
        senderId = getIntent().getStringExtra("senderId");
        receiverId = getIntent().getStringExtra("receiverId");
        receiverName = getIntent().getStringExtra("receiverName");

        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        getApplicationContext().getSharedPreferences(SP_NAME, MODE_PRIVATE)
                .edit().putString("chatRoomActive", receiverId).apply();


        myName = sharedPreferences.getString("myName", null);

        setTitle(receiverName);

        roomNode = firebaseDatabase.getReference().getRoot().child("oneToOne").child(roomKey);

        sendMessage = (FloatingActionButton) findViewById(R.id.chatSendButton);
        message = (EditText) findViewById(R.id.messageEdit);
        messagesRecyclerView = (RecyclerView) findViewById(R.id.messagesContainer);


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String messageData = message.getText().toString();
                if ( !messageData.trim().isEmpty() ) {

                    //check network status
                    ConnectivityManager cm =
                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    ChatMessage chatMessage = new ChatMessage(messageData, senderId, receiverId, myName);
                    if (isConnected) {

                        chatMessage.setOffline("false");

                        //create the message node
                        DatabaseReference messageNode = roomNode.push();
                        messageNode.setValue(chatMessage);

                        message.getText().clear();
                    } else {
                     //   Toast.makeText(ChatRoomActivity.this, "Check your connection", Toast.LENGTH_SHORT).show();
                        chatMessage.setOffline("true");
                        DatabaseReference messageNode = roomNode.push();
                        messageNode.setValue(chatMessage);

                        message.getText().clear();
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
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        messagesRecyclerView.setLayoutManager(linearLayoutManager);
        messagesRecyclerView.setAdapter(adapter);


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = adapter.getItemCount();
                int lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    messagesRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }else
            return false;
    }

}
