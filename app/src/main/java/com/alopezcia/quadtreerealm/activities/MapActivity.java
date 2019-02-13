package com.alopezcia.quadtreerealm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alopezcia.quadtreerealm.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO
    }
}
