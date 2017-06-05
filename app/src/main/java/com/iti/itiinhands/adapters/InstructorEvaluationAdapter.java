package com.iti.itiinhands.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.InstructorEvaluation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rana Gamal on 02/06/2017.
 */

public class InstructorEvaluationAdapter extends RecyclerView.Adapter<InstructorEvaluationAdapter.InstructorEvaluationViewHolder>{

    private ArrayList<InstructorEvaluation> instEvalList = new ArrayList<>();
    private Context context;

    public InstructorEvaluationAdapter(ArrayList<InstructorEvaluation> instEvalList, Context context) {
        this.instEvalList = instEvalList;
        this.context = context;
    }

    @Override
    public InstructorEvaluationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inst_eval_cell, parent, false);
        return new InstructorEvaluationViewHolder(itemView, instEvalList);
    }

    @Override
    public void onBindViewHolder(InstructorEvaluationViewHolder holder, int position) {
        holder.bind(instEvalList.get(position));
    }

    @Override
    public int getItemCount() {
        return instEvalList.size();
    }

    //---------------------------------View Holder ---------------------------------------
    public class InstructorEvaluationViewHolder extends RecyclerView.ViewHolder {

        TextView courseId;
        ImageView courseEval;

        ArrayList<InstructorEvaluation> instEvalList = new ArrayList<>();

        public InstructorEvaluationViewHolder(View itemView, ArrayList<InstructorEvaluation> instEvalList) {
            super(itemView);
            this.instEvalList = instEvalList;
            courseId = (TextView) itemView.findViewById(R.id.course_id);
            courseEval = (ImageView) itemView.findViewById(R.id.course_eval);
        }

        public void bind(final InstructorEvaluation instEval){
            courseId.setText(String.valueOf(instEval.getCourseId()));

            switch (Integer.valueOf(instEval.getEval())){
                case 1: courseEval.setImageResource(R.drawable.s); break;
                case 2: courseEval.setImageResource(R.drawable.ss); break;
                case 3: courseEval.setImageResource(R.drawable.sss); break;
                case 4: courseEval.setImageResource(R.drawable.ssss); break;
                case 5: courseEval.setImageResource(R.drawable.sssss); break;
            }


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, instEval.getEval(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
