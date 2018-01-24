package com.example.administrator.broadcastreceivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by wbx on 2018/1/24.
 */

public class NetworkChangeReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            NetworkInfo networkInfo;

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = connectivityManager.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isAvailable()){
                //网络已连接
                String typeName = networkInfo.getTypeName();
                if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                    Toast.makeText(context, "Wifi", Toast.LENGTH_LONG).show();
                }else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                    Toast.makeText(context, "手机", Toast.LENGTH_LONG).show();
                }else if(networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET){
                    Toast.makeText(context, "有限", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(context, "其它类型网络", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(context, "网络断开", Toast.LENGTH_LONG).show();
            }
        }
    }
}
