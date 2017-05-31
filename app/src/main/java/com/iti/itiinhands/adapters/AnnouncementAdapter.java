package com.iti.itiinhands.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.Announcement;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HP on 28/05/2017.
 */

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    private ArrayList<Announcement> announcements;

    public AnnouncementAdapter(ArrayList<Announcement> announcements) {
        this.announcements = announcements;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_list_item, parent, false);

        return new MyViewHolder(itemView,announcements);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.bind(announcements.get(position));

    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView body;
        public ImageView pic ;
        public TextView date;

        ArrayList<Announcement> announcements = new ArrayList<>();

        public MyViewHolder(View itemView, ArrayList<Announcement>announcements) {
            super(itemView);
            this.announcements = announcements;
            title =(TextView) itemView.findViewById(R.id.announceTitle);
            body =(TextView) itemView.findViewById(R.id.announceBody);
            date = (TextView)itemView.findViewById(R.id.announceDate);
            pic = (ImageView)itemView.findViewById(R.id.announceType);
        }

        public void bind(final Announcement announcementBean){

            title.setText(announcementBean.getTitle());
            body.setText(announcementBean.getBody());
            date.setText(DateFormat.format("MM/dd/yyyy", new Date(announcementBean.getDate())).toString());
            if(announcementBean.getType()==1){
                pic.setImageResource(R.drawable.available);
            }else if (announcementBean.getType()==2){
                pic.setImageResource(R.drawable.away);
            }else if(announcementBean.getType()==3){
                pic.setImageResource(R.drawable.busy);
            }



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }
}
