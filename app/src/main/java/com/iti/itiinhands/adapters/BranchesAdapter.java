package com.iti.itiinhands.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.Tracks;
import com.iti.itiinhands.beans.Branch;

import java.util.ArrayList;

/**
 * Created by Rana Gamal on 05/05/2017.
 */

public class BranchesAdapter extends RecyclerView.Adapter<BranchesAdapter.MyViewHolder>{

    private ArrayList<Branch> branchesList = new ArrayList<>();

    public BranchesAdapter(ArrayList<Branch> branchesList){
        this.branchesList = branchesList;
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

        public MyViewHolder(View itemView, ArrayList<Branch> branchesList) {
            super(itemView);
            this.branchesList = branchesList;
            branchLocation = (TextView) itemView.findViewById(R.id.branch_list_item);
        }

        public void bind(final Branch branch){
            branchLocation.setText(branch.getLocation());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(branchLocation.getContext(), branch.getLocation(), Toast.LENGTH_SHORT).show();
                    Intent tracksView = new Intent(branchLocation.getContext(), Tracks.class);
                    tracksView.putExtra("branchObject", branch);
                    branchLocation.getContext().startActivity(tracksView);
                }
            });
        }
    }
}
