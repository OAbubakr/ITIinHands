package com.iti.itiinhands.adapters.chatAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.ChangeEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.iti.itiinhands.R;
import com.iti.itiinhands.model.chat.ChatMessage;

/**
 * Created by home on 5/23/2017.
 */

public class ChatRoomAdapter extends FirebaseRecyclerAdapter<ChatMessage, ChatRoomAdapter.MyViewHolder> {


    private String myId;
    ProgressDialog progressDialog;

    public ChatRoomAdapter(Context context, Class<ChatMessage> modelClass, int modelLayout,
                           Class<MyViewHolder> viewHolderClass, Query ref, String myId) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.myId = myId;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

    }
    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        progressDialog.hide();
    }


    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, ChatMessage model, int position) {

        boolean isMe = myId.equals(model.getSenderId());
        setAlignment(viewHolder, isMe);

        viewHolder.getTxtMessage().setText(model.getMessage());
        viewHolder.getTxtInfo().setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                model.getDate()));

    }

    //adjusting message orientation
    private void setAlignment(MyViewHolder holder, boolean isMe) {
        if (isMe) {
            holder.getContentWithBG().setBackgroundResource(R.drawable.in_message_bg);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.getContentWithBG().getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.getContentWithBG().setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.getContent().getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.getContent().setLayoutParams(lp);

            layoutParams = (LinearLayout.LayoutParams) holder.getTxtMessage().getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.getTxtMessage().setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.getTxtInfo().getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.getTxtInfo().setLayoutParams(layoutParams);

        } else {
            holder.getContentWithBG().setBackgroundResource(R.drawable.out_message_bg);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.getContentWithBG().getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.getContentWithBG().setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.getContent().getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.getContent().setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.getTxtMessage().getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.getTxtMessage().setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.getTxtInfo().getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.getTxtInfo().setLayoutParams(layoutParams);
        }
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMessage, txtInfo;
        private LinearLayout content, contentWithBG;

        public MyViewHolder(View v) {
            super(v);
            txtMessage = (TextView) v.findViewById(R.id.txtMessage);
            content = (LinearLayout) v.findViewById(R.id.content);
            contentWithBG = (LinearLayout) v.findViewById(R.id.contentWithBackground);
            txtInfo = (TextView) v.findViewById(R.id.txtInfo);
        }

        public TextView getTxtMessage() {
            return txtMessage;
        }

        public TextView getTxtInfo() {
            return txtInfo;
        }

        public LinearLayout getContent() {
            return content;
        }

        public LinearLayout getContentWithBG() {
            return contentWithBG;
        }

    }


}
