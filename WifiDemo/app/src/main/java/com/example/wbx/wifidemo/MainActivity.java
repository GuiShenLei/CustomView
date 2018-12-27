package com.example.wbx.wifidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    private ScrollView sView;
    private Button openNetCard;
    private Button closeNetCard;
    private Button checkNetCard;
    private Button scan;
    private Button getScanResult;
    private Button connect;
    private Button disconnect;
    private Button checkNetWorkState;
    private TextView scanResult;
    private String mScanResult;
    private WifiAdmin mWifiAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWifiAdmin = new WifiAdmin(this);
        init();
    }

    public void init(){
        openNetCard = findViewById(R.id.startBtn);
        closeNetCard = findViewById(R.id.stopBtn);
        checkNetWorkState = findViewById(R.id.checkBtn);
        scan = findViewById(R.id.scan_net);
        getScanResult = findViewById(R.id.scan_result);
        connect = findViewById(R.id.link_wifi);
        disconnect = findViewById(R.id.break_wifi);
        checkNetWorkState = findViewById(R.id.wifi_link_state);
        scanResult = findViewById(R.id.tv_scan_result);

        openNetCard.setOnClickListener(this);
        closeNetCard.setOnClickListener(this);
        checkNetWorkState.setOnClickListener(this);
        scan.setOnClickListener(this);
        getScanResult.setOnClickListener(this);
        connect.setOnClickListener(this);
        disconnect.setOnClickListener(this);
        checkNetWorkState.setOnClickListener(this);
    }

    public void openNetCard(){
        mWifiAdmin.openNetCard();
    }

    public void closeNetCard(){
        mWifiAdmin.closeNetCard();
    }

    public void checkNetCardState(){
        mWifiAdmin.checkNetCardState();
    }

    public void scan(){
        mWifiAdmin.scan();
    }

    public void getScanResult(){
        mScanResult = mWifiAdmin.getScanResult();
        scanResult.setText(mScanResult);
    }

    public void connect(){
        mWifiAdmin.connect();
        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
    }

    public void disconnect(){
        mWifiAdmin.disconnetWifi();
    }

    public void checkNetWorkSate(){
        mWifiAdmin.checkNetWorkState();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.startBtn:
                openNetCard();
                break;
            case R.id.stopBtn:
                closeNetCard();
                break;
            case R.id.checkBtn:
                checkNetCardState();
                break;
            case R.id.scan_net:
                scan();
                break;
            case R.id.scan_result:
                getScanResult();
                break;
            case R.id.link_wifi:
                connect();
                break;
            case R.id.break_wifi:
                disconnect();
                break;
            case R.id.wifi_link_state:
                checkNetWorkSate();
                break;
             default:
                    break;
        }
    }
}
