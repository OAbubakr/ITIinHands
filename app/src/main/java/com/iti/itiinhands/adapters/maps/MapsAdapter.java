package com.iti.itiinhands.adapters.maps;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.MapsDetailsActivity;
import com.iti.itiinhands.model.Branch;

import java.util.List;

/**
 * Created by home on 6/1/2017.
 */

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.MapsViewHolder>{


    private Context context;
    private int cellToInflate;
    private List<Branch> branches;

    public MapsAdapter(Context context, int cellToInflate, List<Branch> branches) {
        this.context = context;
        this.cellToInflate = cellToInflate;
        this.branches = branches;
    }

    @Override
    public MapsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(cellToInflate, parent, false);
        return new MapsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MapsViewHolder holder, final int position) {

        Branch branch = branches.get(position);

        holder.getTitle().setText(branch.getBranchName());
        holder.getAddress().setText(branch.getAddress());
        holder.getPhone().setText(branch.getPhones());
        holder.getEmail().setText(branch.getEmails());

        if(branch.isHasMaps()){
            holder.getViewMap().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, MapsDetailsActivity.class);
                    intent.putExtra("branchId", branches.get(position).getBranchId());
                    context.startActivity(intent);

                }
            });
        }else{
            holder.getViewMap().setText("");
            holder.getViewMap().setBackgroundResource(R.drawable.view_map_grey);
            holder.getViewMap().setClickable(false);
        }

    }

    @Override
    public int getItemCount() {
        return branches.size();
    }

    public static class MapsViewHolder extends RecyclerView.ViewHolder{

        TextView title, address, phone, email;
        Button viewMap;

        public MapsViewHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.mapCardTitle);
            this.address = (TextView) itemView.findViewById(R.id.mapCardAddress);
            this.phone = (TextView) itemView.findViewById(R.id.mapCardPhone);
            this.email = (TextView) itemView.findViewById(R.id.mapCardEmail);
            this.viewMap = (Button) itemView.findViewById(R.id.mapCardButton);
        }

        public Button getViewMap() {
            return viewMap;
        }

        public TextView getEmail() {
            return email;
        }

        public TextView getPhone() {
            return phone;
        }

        public TextView getAddress() {
            return address;
        }

        public TextView getTitle() {
            return title;
        }




    }

}
