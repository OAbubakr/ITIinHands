package com.iti.itiinhands.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.Graduate;
import com.iti.itiinhands.beans.GraduateBasicData;
import com.iti.itiinhands.beans.StudentGrade;

import java.util.Collections;
import java.util.List;

/**
 * Created by engra on 6/7/2017.
 */

public class GraduateAdapter extends RecyclerView.Adapter<GraduateAdapter.GraduateViewHolder>{

    private List<GraduateBasicData> graduateBasicDataList = Collections.emptyList();
    private LayoutInflater inflater;
    private Context mycontext;

    public GraduateAdapter(Context context, List<GraduateBasicData> graduateBasicDataList) {
        inflater = LayoutInflater.from(context);
        this.mycontext = context;
        this.graduateBasicDataList = graduateBasicDataList;
    }
    @Override
    public GraduateAdapter.GraduateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.customrow_listview, parent, false);
        GraduateAdapter.GraduateViewHolder graduateViewHolder = new GraduateAdapter.GraduateViewHolder(view);

        return graduateViewHolder;
    }

    @Override
    public void onBindViewHolder(GraduateAdapter.GraduateViewHolder holder, final int position) {
        final GraduateBasicData graduateBasicData = graduateBasicDataList.get(position);
        holder.graduateUserName.setText(graduateBasicData.getEnglishname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mycontext,graduateBasicData.getUsername(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return graduateBasicDataList.size();
    }

    public class GraduateViewHolder extends RecyclerView.ViewHolder{
        TextView graduateUserName;
        public GraduateViewHolder(final View itemView) {
            super(itemView);
            graduateUserName = (TextView) itemView.findViewById(R.id.graduateName);
        }

    }


}
