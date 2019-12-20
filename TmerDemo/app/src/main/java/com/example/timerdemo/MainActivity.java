package com.example.timerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Timer mTimer;

    private TextView mText;
    private TextSwitchView mTextSwitch;

    private String[] searchHints = new String[]{"中心好书","全世界都是你","生日快乐","无敌风火轮"};

    private int searchHintIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mText = findViewById(R.id.text);
        mTextSwitch = findViewById(R.id.text_switch);

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
    }

    private void startTimer(){
        if(mTimer == null){
            mTimer = new Timer();
        }
        //每次timer启动都需要重新 new 一个TimerTask
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                mTimerHandler.sendEmptyMessage(0);
            }
        }, 0, 5000);
    }

    private void stopTimer(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

    private Handler mTimerHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if(searchHints == null){
                stopTimer();
                return;
            }

            if(searchHintIndex < searchHints.length){
                mText.setText(searchHints[searchHintIndex]);
                mTextSwitch.setText(searchHints[searchHintIndex]);
            }else{
                searchHintIndex = 0;
                mText.setText(searchHints[searchHintIndex]);
                mTextSwitch.setText(searchHints[searchHintIndex]);
            }
            searchHintIndex++;
        }
    };
}
