package com.iti.itiinhands.fragments.events;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.iti.itiinhands.R;
import com.iti.itiinhands.adapters.events.EventAdapter;
import com.iti.itiinhands.model.Event;
import com.iti.itiinhands.model.Response;
import com.iti.itiinhands.networkinterfaces.NetworkManager;
import com.iti.itiinhands.networkinterfaces.NetworkResponse;
import com.iti.itiinhands.networkinterfaces.NetworkUtilities;
import com.iti.itiinhands.utilities.DataSerializer;

import java.util.ArrayList;


public class EventListFragment extends Fragment implements NetworkResponse {


    private RecyclerView recyclerView;
    private boolean isDownloading;
    private SwipeRefreshLayout swipeContainer;
    private NetworkManager networkManager;
    private ProgressBar progressBar;
    private ArrayList<Event> eventsList = new ArrayList<>();
    private EventAdapter eventsAdapter;
    private Fragment parent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_event_list, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {



        isDownloading = true;
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isDownloading){
                    isDownloading = true;
                    prepareEventData();
                }
                else
                    onFailure();
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) view.findViewById(R.id.event_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        eventsAdapter = new EventAdapter(eventsList, getActivity().getApplicationContext());
        recyclerView.setAdapter(eventsAdapter);


        networkManager = NetworkManager.getInstance(getActivity().getApplicationContext());

        prepareEventData();


    }

    private void prepareEventData() {

        if (networkManager.isOnline()) {
            networkManager.getEvents(this);
        }
        else onFailure();
    }

    @Override
    public void onResponse(Response response) {


        isDownloading = false;
        if(swipeContainer != null) {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }

        if (response!=null&&getActivity()!=null && response.getStatus().equals(Response.SUCCESS)) {

            ArrayList<Event> result = DataSerializer.convert(response.getResponseData(), new TypeToken<ArrayList<Event>>() {
            }.getType());

            eventsList.clear();
            eventsList.addAll(result);

            progressBar.setVisibility(View.GONE);

            eventsAdapter.notifyDataSetChanged();
            ((EventTabFragment)parent).setCalenderData(eventsList);

        }
        else
            onFailure();


    }


    @Override
    public void onFailure() {

        isDownloading = false;
        if(swipeContainer != null) {
            if (swipeContainer.isRefreshing()) {
                swipeContainer.setRefreshing(false);
            }
        }


        new NetworkUtilities().networkFailure(getActivity());
        progressBar.setVisibility(View.GONE);
    }

    public void setParent(EventTabFragment parent) {
        this.parent = parent;
    }
}
