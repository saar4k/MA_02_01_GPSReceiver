package com.example.gpsreceiver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView textViewKor;
    TextView textViewHoehe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        textViewKor = findViewById(R.id.textViewKor);
        textViewHoehe = findViewById(R.id.textViewHoehe);

        // Check for location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the missing permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // Permission has been granted
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }
    }

    @SuppressLint("SetTextI18n")
    private void makeUseOfNewLocation(Location location) {
        // Use the location to update the TextViews for latitude, longitude, and altitude

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewKor.setText("Coordinate: " + latitude + ", " + longitude);
                textViewHoehe.setText("Height: " + altitude);
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Called when a new location is found by the network location provider.
        makeUseOfNewLocation(location);
        Log.d("MainActivity", "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}


