package com.iti.itiinhands.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.InstructorEvaluationAdapter;
import com.iti.itiinhands.beans.InstructorEvaluation;
import com.iti.itiinhands.dto.UserData;
import com.iti.itiinhands.model.JobVacancy;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.utilities.Constants;
import com.iti.itiinhands.utilities.DataSerializer;
import com.iti.itiinhands.utilities.UserDataSerializer;

import java.util.ArrayList;


public class InstructorEvaluationFragment extends Fragment implements NetworkResponse {

    private ArrayList<InstructorEvaluation> instEvalList = new ArrayList<>();
    private RecyclerView recyclerView;
    private InstructorEvaluationAdapter instEvalAdapter;
    private NetworkManager networkManager;
    private TextView instName;
    private UserData userData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructor_evaluation, container, false);
        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());

        SharedPreferences data = getActivity().getSharedPreferences(Constants.USER_SHARED_PREFERENCES, 0);
        userData = UserDataSerializer.deSerialize(data.getString(Constants.USER_OBJECT,""));

        recyclerView = (RecyclerView) view.findViewById(R.id.inst_eval_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        instName = (TextView) view.findViewById(R.id.inst_name);
        instName.setText(userData.getEmployeeName()+"`");
        prepareInstEvalData();
        return view;
    }

    private void prepareInstEvalData() {


        int instId = userData.getId();

        if (networkManager.isOnline()) {
            networkManager.getInstructorEvaluation(this, instId);
        }
    }

    @Override
    public void onResponse(Response response) {
        if (response.getStatus().equals(Response.SUCCESS)) {
            instEvalList = DataSerializer.convert(response.getResponseData(),new TypeToken<ArrayList<InstructorEvaluation>>(){}.getType());

//            instEvalList = (ArrayList<InstructorEvaluation>) response.getResponseData();
            instEvalAdapter = new InstructorEvaluationAdapter(instEvalList, getActivity().getApplicationContext());
            recyclerView.setAdapter(instEvalAdapter);
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity().getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
    }
}
