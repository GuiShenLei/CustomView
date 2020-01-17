package com.example.baidulocationdemo;

import android.Manifest;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

public class LocationActivity extends AppCompatActivity {

    private TextView mAddrTV;
    private TextView mAddrDetailTV;
    private TextView mLatitudeTV1;
    private TextView mLatitudeTV2;
    private TextView mLongtitudeTV1;
    private TextView mLongtitudeTV2;

    public LocationClient mLocationClient;
    private MyLocationListener myListener;

    private double mLatitude;
    private double mLongtitude;

    private PermissionManager mPermissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mAddrTV = findViewById(R.id.addr1);
        mAddrDetailTV = findViewById(R.id.addr2);
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
        mLocationClient = new LocationClient(getApplicationContext());
        myListener = new MyLocationListener();
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        setOption();

        //mLocationClient为第二步初始化过的LocationClient对象
        //调用LocationClient的start()方法，便可发起定位请求
        //start()：启动定位SDK；stop()：关闭定位SDK。调用start()之后只需要等待定位结果自动回调即可。
        //开发者定位场景如果是单次定位的场景，在收到定位结果之后直接调用stop()函数即可。
        //如果stop()之后仍然想进行定位，可以再次start()等待定位结果回调即可。
        //自V7.2版本起，新增LocationClient.reStart()方法，用于在某些特定的异常环境下重启定位。
        //如果开发者想按照自己逻辑请求定位，可以在start()之后按照自己的逻辑请求LocationClient.requestLocation()函数，会主动触发定位SDK内部定位逻辑，等待定位回调即可。
        mLocationClient.start();
    }

    private void setOption() {
        LocationClientOption option = new LocationClientOption();

        /************************** 定位 获取全球经纬度***************************************/
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        option.setCoorType("BD09ll");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);

        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false
        option.setEnableSimulateGps(false);


        /************************** 定位 获取地址***************************************/
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);

        /************************************************************************/

        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);

    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果

            /**************************获取全球经纬度***************************************/
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            mLatitude = location.getLatitude();    //获取纬度信息
            mLongtitude = location.getLongitude();    //获取经度信息

            setLatitude(mLatitude);
            setLongtitude(mLongtitude);

            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明


            /**************************获取地址***************************************/
            //以下只列举部分获取地址相关的结果信息
            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息

            setProvinceCity(country + province + city);
            setDetailAddr(addr);

            /************************************************************************/
        }
    }

    private void setLatitude(double latitude) {
        mLatitudeTV2.setText(Double.toString(latitude));
    }

    private void setLongtitude(double latitude) {
        mLongtitudeTV2.setText(Double.toString(latitude));
    }

    private void setProvinceCity(String addr){
        mAddrTV.setText(addr);
    }

    private void setDetailAddr(String addr){
        mAddrDetailTV.setText(addr);
    }
}


