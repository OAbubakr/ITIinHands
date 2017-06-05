package com.iti.itiinhands.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.GridAdapterForHours;
import com.iti.itiinhands.beans.EmpHour;
import com.iti.itiinhands.model.Branch;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.DataSerializer;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.Calendar;

public class EmployeeHours extends Fragment implements NetworkResponse, View.OnClickListener {

    NetworkManager networkManager;
    private NetworkResponse myRef;
    EmpHour empHour;
    Button getHours_Button;
    GridView hoursGrid;
    PieChart pieChart;
    private int mYear, mMonth, mDay;
    EditText startDate, endDate;

    int[] images = {R.drawable.amark, R.drawable.amark, R.drawable.amark, R.drawable.amark,
            R.drawable.amark, R.drawable.amark, R.drawable.amark};

    String[] data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_employee_hours, container, false);
        networkManager = NetworkManager.getInstance(getActivity());
        myRef = this;
        startDate = (EditText) view.findViewById(R.id.startDateTv);
        endDate = (EditText) view.findViewById(R.id.endDateTv);
        hoursGrid = (GridView) view.findViewById(R.id.HoursGridView);
        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        getHours_Button = (Button) view.findViewById(R.id.getEmpBtn);
        getHours_Button.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResponse(Response response) {
        if (response.getStatus().equals(Response.SUCCESS)) {
            empHour = DataSerializer.convert(response.getResponseData(),EmpHour.class);

//            empHour = (EmpHour) response.getResponseData();
            data = new String[]{
                    empHour.getWorkingDays().toString(),
                    empHour.getAbsenceDays().toString(),
                    empHour.getAttendHours().toString(),
                    empHour.getLateDays().toString(),
                    empHour.getMissionHours().toString(),
                    empHour.getPermissionHours().toString(),
                    empHour.getVacationHours().toString()
            };


            pieChart.addPieSlice(new PieModel("Absence Hour", empHour.getWorkingDays(), Color.parseColor("#56B7F1")));
            pieChart.addPieSlice(new PieModel("Absence Hour", empHour.getAbsenceDays(), Color.parseColor("#56B745")));
            pieChart.addPieSlice(new PieModel("Absence Hour", empHour.getAttendDays(), Color.parseColor("#56B778")));
            pieChart.addPieSlice(new PieModel("Absence Hour", empHour.getAttendHours(), Color.parseColor("#56B712")));
            pieChart.addPieSlice(new PieModel("Absence Hour", empHour.getLateDays(), Color.parseColor("#56B7FF")));
            pieChart.addPieSlice(new PieModel("Absence Hour", empHour.getMissionHours(), Color.parseColor("#56B7FA")));
            pieChart.addPieSlice(new PieModel("Absence Hour", empHour.getPermissionHours(), Color.parseColor("#56B7FB")));
            pieChart.addPieSlice(new PieModel("Absence Hour", empHour.getVacationHours(), Color.parseColor("#56B7FC")));

            pieChart.startAnimation();
            pieChart.setShowDecimal(true);
            hoursGrid.setAdapter(new GridAdapterForHours(getContext(), data, images));
        }
    }

    @Override
    public void onFailure() {

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.getEmpBtn) {
            if (!(endDate.getText().toString().isEmpty() || startDate.getText().toString().isEmpty())) {
                System.out.println(startDate.getText().toString());
                System.out.println(endDate.getText().toString());
                networkManager.getEmployeeHours(myRef, 1018, startDate.getText().toString(),
                        endDate.getText().toString());
            } else {
                Toast.makeText(getActivity(), "please select date firsst", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.startDateTv) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            startDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        } else if (v.getId() == R.id.endDateTv) {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            endDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }
}


