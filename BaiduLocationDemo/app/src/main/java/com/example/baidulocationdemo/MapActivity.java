package com.example.baidulocationdemo;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.MapView;

public class MapActivity extends AppCompatActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    private Button mSearchBtn;
    private Button mLocation;

    private double mLatitude;
    private double mLongtitude;

    private PermissionManager mPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mMapView = findViewById(R.id.map);
        mSearchBtn = findViewById(R.id.search);
        mLocation = findViewById(R.id.location);

        applyPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    private void applyPermission(){
        mPermissionManager = new PermissionManager(this);
        mPermissionManager.askForPermission(mPermissionCallback,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    private PermissionCallback mPermissionCallback = new PermissionCallback() {
        @Override
        public void onGranted() {
            initLocationSDK();
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

    private void initLocationSDK() {
        setOption();
    }

    private void setOption() {
        BaiduMapOptions options = new BaiduMapOptions();

    }

}


