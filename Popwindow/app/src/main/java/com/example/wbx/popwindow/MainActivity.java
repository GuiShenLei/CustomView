package com.example.wbx.popwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView mTvHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvHello = findViewById(R.id.hello_world);
        mTvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
            }
        });

    }

    private void showPopwindow(){
        View popwindowView = LayoutInflater.from(this).inflate(R.layout.popwindow_layout,null);

        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setContentView(popwindowView);
        popupWindow.setWidth(getResources().getDimensionPixelSize(R.dimen.size_50dp));
        popupWindow.setHeight(getResources().getDimensionPixelSize(R.dimen.size_50dp));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.showAtLocation(mTvHello, Gravity.CENTER,0,0);
    }
}
