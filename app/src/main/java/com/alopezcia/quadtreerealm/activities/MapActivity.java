package com.alopezcia.quadtreerealm.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alopezcia.quadtreerealm.R;
import com.alopezcia.quadtreerealm.models.QuadTreeExtent;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import io.realm.Realm;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private LatLng minLL;
    private LatLng maxLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if( getIntent().getExtras()!= null ) {
            Realm realm = Realm.getDefaultInstance();
            int id = getIntent().getExtras().getInt("idExtent");
            QuadTreeExtent ext = realm.where(QuadTreeExtent.class).equalTo("id", id).findFirst();
            double minLat = ext.getMinLat();
            double minLng = ext.getMinLng();
            double maxLat = ext.getMaxLat();
            double maxLng = ext.getMaxLng();
            minLL = new LatLng(minLat, minLng);
            maxLL = new LatLng(maxLat, maxLng);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO -
        googleMap.setLatLngBoundsForCameraTarget(adjustBoundsForMaxZoomLevel(minLL, maxLL));
    }

    private LatLngBounds adjustBoundsForMaxZoomLevel(LatLng sw, LatLng ne) {
        LatLngBounds bounds=null;
        double deltaLat = Math.abs(sw.latitude - ne.latitude);
        double deltaLon = Math.abs(sw.longitude - ne.longitude);

        final double zoomN = 0.005; // minimum zoom coefficient
        if (deltaLat < zoomN) {
            sw = new LatLng(sw.latitude - (zoomN - deltaLat / 2), sw.longitude);
            ne = new LatLng(ne.latitude + (zoomN - deltaLat / 2), ne.longitude);
            bounds = new LatLngBounds(sw, ne);
        }
        else if (deltaLon < zoomN) {
            sw = new LatLng(sw.latitude, sw.longitude - (zoomN - deltaLon / 2));
            ne = new LatLng(ne.latitude, ne.longitude + (zoomN - deltaLon / 2));
            bounds = new LatLngBounds(sw, ne);
        }

        return bounds;
    }
}
