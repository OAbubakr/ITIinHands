package com.iti.itiinhands.adapters.permission;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.Tracks;
import com.iti.itiinhands.model.Permission;

import java.util.ArrayList;

/**
 * Created by Rana Gamal on 05/05/2017.
 */

public class StudentPermissionsAdapter extends RecyclerView.Adapter<StudentPermissionsAdapter.MyViewHolder>{

    private ArrayList<Permission> permissions = new ArrayList<>();
    private Context context;

    public StudentPermissionsAdapter(ArrayList<Permission> permissions, Context context){
         this.permissions = permissions;
        this.context = context;

    }

    @Override
    public StudentPermissionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.branches_list_item, parent, false);
        return new MyViewHolder(itemView, permissions);
    }

    @Override
    public void onBindViewHolder(StudentPermissionsAdapter.MyViewHolder holder, int position) {
        holder.bind(permissions.get(position));
    }

    @Override
    public int getItemCount() {
        return permissions.size();
    }

    //----------------------------------View Holder Class-------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView details;
        ArrayList<Permission> permissions = new ArrayList<>();

        public MyViewHolder(View itemView, final ArrayList<Permission> permissions) {
            super(itemView);
            this.permissions = permissions;
            details = (TextView) itemView.findViewById(R.id.branch_list_item);


        }

        public void bind(final Permission permission){


            String s =  "Date: "+ permission.getPermissionDate() + "\nFrom " + permission.getFromH() + ":" + permission.getFromMin() + " to "
                    + permission.getToH() + ":" + permission.getToMin() + "\nCause: " + permission.getComment()+"\nStatus: "+permission.getPermissionStatus();
            details.setText(s);

        }
    }
}
