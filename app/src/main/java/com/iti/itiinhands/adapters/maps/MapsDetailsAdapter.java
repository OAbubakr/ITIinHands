package com.iti.itiinhands.adapters.maps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.activities.MapsDetailsActivity;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.List;

/**
 * Created by home on 6/1/2017.
 */

public class MapsDetailsAdapter extends RecyclerView.Adapter<MapsDetailsAdapter.MapsViewHolder>{


    private Context context;
    private int cellToInflate;
    private List<MapsDetailsActivity.MapTitle> mapTitles;

    public MapsDetailsAdapter(Context context, int cellToInflate, List<MapsDetailsActivity.MapTitle> mapTitles) {
        this.context = context;
        this.cellToInflate = cellToInflate;
        this.mapTitles = mapTitles;
    }

    @Override
    public MapsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(cellToInflate, parent, false);
        return new MapsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MapsViewHolder holder, final int position) {

        MapsDetailsActivity.MapTitle mapTitle = mapTitles.get(position);

        holder.getTitle().setText(mapTitle.getTitle());
        holder.getMap().setImageResource(mapTitle.getDrawable());
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageViewer.Builder<>
                        (context, mapTitles).setFormatter(new ImageViewer.Formatter<MapsDetailsActivity.MapTitle>() {
                    @Override
                    public String format(MapsDetailsActivity.MapTitle mapTitle) {
                        return mapTitle.getUrl();
                    }
                }).setStartPosition(position).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mapTitles.size();
    }

    public static class MapsViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private ImageView map;
        private View view;

        public MapsViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.map = (ImageView) itemView.findViewById(R.id.map);
            this.view = itemView;

        }

        public TextView getTitle() {
            return title;
        }


        public ImageView getMap() {
            return map;
        }

        public View getView() {
            return view;
        }



    }

}
