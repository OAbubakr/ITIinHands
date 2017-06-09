package com.iti.itiinhands.adapters;

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
import com.iti.itiinhands.model.Branch;


import java.util.ArrayList;

/**
 * Created by Rana Gamal on 05/05/2017.
 */

public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.MyViewHolder>{

    private ArrayList<Branch> branchesList = new ArrayList<>();
    private Context context;
    private  int flag;

    public BranchesAdapter(ArrayList<Branch> branchesList, Context context, int flag){
        if ( branchesList!= null)  this.branchesList = branchesList;
        this.context = context;
        this.flag = flag;
    }

    @Override
    public BranchesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.branches_list_item, parent, false);
        return new MyViewHolder(itemView, branchesList);
    }

    @Override
    public void onBindViewHolder(BranchesAdapter.MyViewHolder holder, int position) {
        holder.bind(branchesList.get(position));
    }

    @Override
    public int getItemCount() {
        return branchesList.size();
    }

    //----------------------------------View Holder Class-------------------------------------------
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView branchLocation;
        ArrayList<Branch> branchesList = new ArrayList<>();

        public MyViewHolder(View itemView, final ArrayList<Branch> branchesList) {
            super(itemView);
            this.branchesList = branchesList;
            branchLocation = (TextView) itemView.findViewById(R.id.branch_list_item);


        }

        public void bind(final Branch branch){
            branchLocation.setText(branch.getBranchName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, branch.getLocation(), Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    Log.i("branch","click");
                    Intent tracksView = new Intent(context, Tracks.class);
                    tracksView.putExtra("branchObject",branch);
                    tracksView.putExtra("flag",flag);
                    tracksView.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(tracksView);
                }
            });
        }
    }
}
