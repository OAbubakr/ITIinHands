package com.iti.itiinhands.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.Announcement;
import com.iti.itiinhands.database.DataBase;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.fragments.AllJobPostsFragment;
import com.iti.itiinhands.fragments.EventListFragment;
import com.iti.itiinhands.fragments.PostJobFragment;
import com.iti.itiinhands.fragments.ScheduleFragment;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by HP on 28/05/2017.
 */

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.MyViewHolder> {

    private ArrayList<Announcement> announcements;
    private Context context;
    private FragmentManager fragmentManager;

    public AnnouncementAdapter(ArrayList<Announcement> announcements, Context context, FragmentManager fragmentManager) {
        this.announcements = announcements;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.announcement_list_item, parent, false);

        return new MyViewHolder(itemView, announcements);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.bind(announcements.get(position));

    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView body;
        public ImageView pic;
       // public TextView date;
        public ImageView deleteAnnouncement;

        ArrayList<Announcement> announcements = new ArrayList<>();

        public MyViewHolder(View itemView, ArrayList<Announcement> announcements) {
            super(itemView);
            this.announcements = announcements;
            title = (TextView) itemView.findViewById(R.id.announceTitle);
            body = (TextView) itemView.findViewById(R.id.announceBody);
        //    date = (TextView) itemView.findViewById(R.id.announceDate);
            pic = (ImageView) itemView.findViewById(R.id.announceType);
            deleteAnnouncement = (ImageView) itemView.findViewById(R.id.delete);

        }

        public void bind(final Announcement announcementBean) {

            title.setText(announcementBean.getTitle());
            body.setText(announcementBean.getBody());
           // date.setText(DateFormat.format("MM/dd/yyyy", new Date(announcementBean.getDate())).toString());
            if (announcementBean.getType() == 1) {
                pic.setImageResource(R.drawable.event);
            } else if (announcementBean.getType() == 2) {
                pic.setImageResource(R.drawable.schedule_change);
            } else if (announcementBean.getType() == 3) {
                pic.setImageResource(R.drawable.permission);
            }else if (announcementBean.getType() == 4){
                pic.setImageResource(R.drawable.job_post);
            }

            deleteAnnouncement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("delete", "delete");

                    SharedPreferences setting = context.getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
                    UserData userData = UserDataSerializer.deSerialize(setting.getString(Constants.USER_OBJECT, ""));
                    int type = setting.getInt(Constants.USER_TYPE, 0);
                    String userName = "";

                    switch (type) {
                        case 0:
                            //guest
                            userName = "guest";
                            break;
                        case 1:
                            //Student
                            userName = userData.getName();
                            break;
                        case 2:
                            //Staff
                            userName = userData.getEmployeeName();
                            break;
                        case 3:
                            //Company
                            userName = userData.getCompanyUserName();
                            break;
                        case 4:
                            //Graduate
                            userName = "graduate";
                            break;

                    }


                    int delete = DataBase.getInstance(context).deleteAnnouncement(announcementBean.getId(), userName);
                    Log.i("delete", String.valueOf(delete));
                    announcements.remove(announcementBean);
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), announcements.size());


                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (announcementBean.getType()) {

                        case 1://events
                            fragmentManager.beginTransaction().replace(R.id.content_frame, new EventListFragment()).commit();
                            break;
                        case 2://scheduleChange
                            fragmentManager.beginTransaction().replace(R.id.content_frame, new ScheduleFragment()).commit();

                            break;
                        case 3:

                            break;
                        case 4://jonPost
                            fragmentManager.beginTransaction().replace(R.id.content_frame, new AllJobPostsFragment()).commit();
                            break;
                    }

                }
            });
        }
    }
}
