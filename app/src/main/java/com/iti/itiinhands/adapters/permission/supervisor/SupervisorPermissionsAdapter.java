package com.iti.itiinhands.adapters.permission.supervisor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.fragments.permission.supervisor.SupervisorPermissionList;
import com.iti.itiinhands.model.Permission;

import java.util.ArrayList;

/**
 * Created by Rana Gamal on 05/05/2017.
 */

public class SupervisorPermissionsAdapter extends RecyclerView.Adapter<SupervisorPermissionsAdapter.MyViewHolder> {

    private ArrayList<Permission> permissions = new ArrayList<>();
    private Context context;
    private  SupervisorPermissionList supervisorPermissionList;

    public SupervisorPermissionsAdapter(ArrayList<Permission> permissions, Context context, SupervisorPermissionList supervisorPermissionList) {
        this.permissions = permissions;
        this.context = context;
        this.supervisorPermissionList = supervisorPermissionList;

    }

    @Override
    public SupervisorPermissionsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.permission_supervisor_card, parent, false);
        return new MyViewHolder(itemView, permissions);
    }

    @Override
    public void onBindViewHolder(SupervisorPermissionsAdapter.MyViewHolder holder, int position) {
        holder.bind(permissions.get(position));
    }

    @Override
    public int getItemCount() {
        return permissions.size();
    }

    //----------------------------------View Holder Class-------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView body;

        public TextView accept;

        public TextView refuse;

        ArrayList<Permission> permissions = new ArrayList<>();

        public MyViewHolder(View itemView, final ArrayList<Permission> permissions) {
            super(itemView);
            this.permissions = permissions;
            title = (TextView) itemView.findViewById(R.id.title);
            body = (TextView) itemView.findViewById(R.id.announceBody);
            accept = (TextView) itemView.findViewById(R.id.accept);
            refuse = (TextView) itemView.findViewById(R.id.refuse);


        }

        public void bind(final Permission permission) {

            title.setText(permission.getStudentName());

            String s = "Date: " + permission.getPermissionDate() + "\nFrom " + permission.getFromH() + ":" + permission.getFromMin() + " to "
                    + permission.getToH() + ":" + permission.getToMin() + "\nCause: " + permission.getComment() + "\nStatus: " + permission.getPermissionStatus();
            body.setText(s);

            if (permission.getPermissionStatus().equalsIgnoreCase("pending")) {
                accept.setVisibility(View.VISIBLE);
                refuse.setVisibility(View.VISIBLE);

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        supervisorPermissionList.sendResponse(permission.getPermissionId(),true);
                    }
                });
                refuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        supervisorPermissionList.sendResponse(permission.getPermissionId(),false);


                    }
                });


            } else {
                accept.setVisibility(View.GONE);
                refuse.setVisibility(View.GONE);
            }

        }
    }
}
