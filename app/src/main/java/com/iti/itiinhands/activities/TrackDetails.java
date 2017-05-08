package com.iti.itiinhands.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.iti.itiinhands.R;
import com.iti.itiinhands.beans.Track;

public class TrackDetails extends AppCompatActivity {

    Track track;
    TextView trackName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        track = (Track) getIntent().getSerializableExtra("trackObject");
        trackName = (TextView) findViewById(R.id.track_name);
        trackName.setText(track.getTrackName());
    }
}
