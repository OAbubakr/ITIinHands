package com.iti.itiinhands.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.iti.itiinhands.R;


/**
 * Created by admin on 5/30/2017.
 */

public class PermissionFragment extends Fragment {


    TextView startMin;
    TextView startHour;
    TextView endMin;
    TextView endHour;
    TextView endCase;
    TextView startCase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.permission_fragment, container, false);


    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        

        startMin = (TextView)view.findViewById(R.id.start_minute);
        endMin = (TextView)view.findViewById(R.id.end_minute);
        startHour = (TextView)view.findViewById(R.id.start_hour);
        endHour = (TextView)view.findViewById(R.id.end_hour);
        endCase= (TextView)view.findViewById(R.id.end_dayORnight);
        startCase =(TextView)view.findViewById(R.id.start_dayORnight);






















    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }





}
