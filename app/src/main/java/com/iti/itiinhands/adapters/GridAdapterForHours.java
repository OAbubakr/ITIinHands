package com.iti.itiinhands.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iti.itiinhands.R;

/**
 * Created by engra on 5/28/2017.
 */

public class GridAdapterForHours extends BaseAdapter{

    private static LayoutInflater inflater = null;
    String [] result;
    Context context;
    int [] imageId;

    public GridAdapterForHours(Context context, String[] prgmNameList, int[] prgmImages) {
        result = prgmNameList;
        this.context = context;
        imageId = prgmImages;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return result.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView = inflater.inflate(R.layout.grid_custom_cell, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.Activityicon);
        holder.tv.setText(result[position]);
        holder.img.setImageResource(imageId[position]);
        return rowView;
    }

    public class Holder {
        TextView tv;
        ImageView img;
    }

}
