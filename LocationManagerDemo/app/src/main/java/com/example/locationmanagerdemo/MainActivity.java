package com.example.locationmanagerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mLatitudeTV1;
    private TextView mLatitudeTV2;
    private TextView mLongtitudeTV1;
    private TextView mLongtitudeTV2;

    private LocationManager mLocationManager;
    private String mProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeTV1 = findViewById(R.id.latitude1);
        mLatitudeTV2 = findViewById(R.id.latitude2);
        mLongtitudeTV1 = findViewById(R.id.longtitude1);
        mLongtitudeTV2 = findViewById(R.id.longtitude2);

        initLocation();
    }

    private void initLocation(){
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //获取所有可供选择的提供器
        List<String> providerList = mLocationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            mProvider = LocationManager.GPS_PROVIDER;
        }else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
            mProvider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(this, "no location provider to use", Toast.LENGTH_SHORT).show();
            return;
        }

        Location location = mLocationManager.getLastKnownLocation(mProvider);
        if(location != null){
            showLocation(location);
        }

        mLocationManager.requestLocationUpdates(mProvider, 3000, 1,mLocationListener );
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
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
    };

    private void showLocation(Location location){
        setLatitude(location.getLatitude());
        setLongtitude(location.getLongitude());
    }

    private void setLatitude(double latitude){
        mLatitudeTV2.setText(Double.toString(latitude));
    }

    private void setLongtitude(double latitude){
        mLongtitudeTV2.setText(Double.toString(latitude));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationManager != null){
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}
