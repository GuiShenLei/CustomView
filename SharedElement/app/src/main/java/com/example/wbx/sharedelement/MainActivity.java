package com.example.wbx.sharedelement;

import android.app.ActivityOptions;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mBtn;
    private TextView mShowText1;
    private TextView mShowText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_main);

        mBtn = findViewById(R.id.btn);
        mShowText1 = findViewById(R.id.show_text_1);
        mShowText2 = findViewById(R.id.show_text_2);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NextActivity.class);

                Pair first = new Pair<View, String>(mShowText1,"show_text_1");
                Pair sencond = new Pair<View, String>(mShowText2,"show_text_1");

                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                        first , sencond);
                startActivity(intent, transitionActivityOptions.toBundle());
            }
        });
    }
}
