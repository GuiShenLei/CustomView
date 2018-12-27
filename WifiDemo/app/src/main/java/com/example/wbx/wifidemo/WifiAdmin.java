package com.example.wbx.wifidemo;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.util.List;

/**
 * desc
 * 创建人：wbx
 * 创建时间：2018-11-21
 */
public class WifiAdmin {
    private final static String TAG = "WifiAdmin";
    private StringBuffer mStringBuffer = new StringBuffer();
    private List<ScanResult> listResult;
    private ScanResult mScanResult;
    private WifiManager mWifiManger;
    private WifiInfo mWifiInfo;
    private List<WifiConfiguration> mWifiConfiguration;
    WifiManager.WifiLock mWifiLock;

    public WifiAdmin(Context context){
        mWifiManger = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManger.getConnectionInfo();
    }

    /**打开WiFi网卡*/
    public void openNetCard(){
        if(!mWifiManger.isWifiEnabled()){
            mWifiManger.setWifiEnabled(true);
        }
    }

    /**关闭WiFi网卡*/
    public void closeNetCard(){
        if(mWifiManger.isWifiEnabled()){
            mWifiManger.setWifiEnabled(false);
        }
    }

    /**检查当前WiFi状态*/
    public void checkNetCardState(){
        if(mWifiManger.getWifiState() == 0){
            Log.i(TAG,"网卡正在关闭");
        }else if(mWifiManger.getWifiState() == 1){
            Log.i(TAG,"网卡已经关闭");
        }else if(mWifiManger.getWifiState() == 2){
            Log.i(TAG,"网卡正在打开");
        }else if(mWifiManger.getWifiState() == 3){
            Log.i(TAG,"网卡已经打开");
        }else {
            Log.i(TAG,"--_--晕......没有获取到网卡状态--_--");
        }
    }

    /**扫描周边网络*/
    public void scan(){
        mWifiManger.startScan();
        listResult = mWifiManger.getScanResults();
        if(listResult != null){
            Log.i(TAG,"当前区域存在无线网络，请查看扫描结果");
        }else {
            Log.i(TAG, "当前区域没有无线网络");
        }
    }

    /**得到扫描结果*/
    public String getScanResult(){
        //每次点击扫描之前清空上一次的扫描结果
        if(mStringBuffer != null){
            mStringBuffer = new StringBuffer();
        }
        scan();
        listResult = mWifiManger.getScanResults();
        if(listResult != null){
            for(int i=0;i<listResult.size();i++){
                mScanResult = listResult.get(i);
                mStringBuffer = mStringBuffer.append("NO.").append(i+1).
                        append(":").append(mScanResult.SSID).append("->").
                        append(mScanResult.BSSID).append("->")
                        .append(mScanResult.capabilities).append("->")
                        .append(mScanResult.frequency).append("->")
                        .append(mScanResult.level).append("->")
                        .append(mScanResult.describeContents()).append("->");
            }
        }
        Log.i(TAG,mStringBuffer.toString());
        return  mStringBuffer.toString();
    }

    public void connect(){
        mWifiInfo = mWifiManger.getConnectionInfo();
    }

    public void disconnetWifi(){
        int netId = getNetWoekId();
        mWifiManger.disableNetwork(netId);
        mWifiManger.disconnect();
        mWifiInfo = null;
    }

    public void checkNetWorkState(){
        if(mWifiInfo != null){
            Log.i(TAG, "网络工作正常");
        }else{
            Log.i(TAG, "网络已断开");
        }
    }

    public int getNetWoekId(){
        return (mWifiInfo == null)?0:mWifiInfo.getNetworkId();
    }

    public int getIPAddress(){
        return (mWifiInfo == null)?0:mWifiInfo.getIpAddress();
    }

    public void acquireWifiLock(){
        mWifiLock.acquire();
    }

    public void releaseWifiLock(){
        if(mWifiLock.isHeld()){
            mWifiLock.release();
        }
    }

    public void createWifiLock(){
        mWifiLock = mWifiManger.createWifiLock("Test");
    }

    public List<WifiConfiguration> getConfiguration(){
        return mWifiConfiguration;
    }

    public void connectConfiguration(int index){
        if(index >= mWifiConfiguration.size()){
            return;
        }

        mWifiManger.enableNetwork(mWifiConfiguration.get(index).networkId, true);
    }

    public String getMacAddress(){
        return (mWifiInfo == null)?"NULL":mWifiInfo.getMacAddress();
    }

    public String getBSSID(){
        return (mWifiInfo == null)?"NULL":mWifiInfo.getBSSID();
    }

    public String getWifiInfo(){
        return (mWifiInfo == null)?"NULL":mWifiInfo.toString();
    }

    public int addNetWork(WifiConfiguration wch){
        int wcgID = mWifiManger.addNetwork(mWifiConfiguration.get(3));
        mWifiManger.enableNetwork(wcgID,true);
        return wcgID;
    }
}
