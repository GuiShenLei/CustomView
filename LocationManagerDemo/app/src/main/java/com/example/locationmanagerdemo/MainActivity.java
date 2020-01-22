package com.example.locationmanagerdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_LOCATION_SERVICE = 1001;
    private TextView mLatitudeTV1;
    private TextView mLatitudeTV2;
    private TextView mLongtitudeTV1;
    private TextView mLongtitudeTV2;

    private LocationManager mLocationManager;
    private String mProvider;
    private PermissionManager mPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLatitudeTV1 = findViewById(R.id.latitude1);
        mLatitudeTV2 = findViewById(R.id.latitude2);
        mLongtitudeTV1 = findViewById(R.id.longtitude1);
        mLongtitudeTV2 = findViewById(R.id.longtitude2);

        applyPermission();
    }

    private void applyPermission(){
        mPermissionManager = new PermissionManager(this);
        mPermissionManager.askForPermission(mPermissionCallback,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    private PermissionCallback mPermissionCallback = new PermissionCallback() {
        @Override
        public void onGranted() {
            initLocation();
        }

        @Override
        public void onDenied() {

        }

        @Override
        public void onNeverAsk() {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(mPermissionManager != null){
            mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initLocation(){
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //获取所有可供选择的提供器
        List<String> providerList = mLocationManager.getProviders(true);
        if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
            mProvider = LocationManager.NETWORK_PROVIDER;
        }else if(providerList.contains(LocationManager.GPS_PROVIDER)){
            mProvider = LocationManager.GPS_PROVIDER;
        }else{
            Toast.makeText(this, "no location provider to use", Toast.LENGTH_SHORT).show();
            gotoSettingLocationSwitch();
            return;
        }

        Location location = mLocationManager.getLastKnownLocation(mProvider);
        if(location != null){
            showLocation(location);
        }

        mLocationManager.requestLocationUpdates(mProvider, 3000, 0,mLocationListener );
    }

    private void gotoSettingLocationSwitch(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, REQUEST_CODE_LOCATION_SERVICE);
    }

    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Toast.makeText(MainActivity.this, "onStatusChanged", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, "onProviderEnabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, "onProviderDisabled", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_LOCATION_SERVICE){
            initLocation();
        }
    }
}
