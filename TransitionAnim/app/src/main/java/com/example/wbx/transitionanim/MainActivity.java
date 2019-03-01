package com.example.wbx.transitionanim;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.transtion_top));
//        getWindow().setReenterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transtion_top));
        getWindow().setReenterTransition(null);

        setContentView(R.layout.activity_main);

        mBtn = findViewById(R.id.btn);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(new Intent(MainActivity.this, SecondActivity.class), compat.toBundle());
            }
        });
    }

//    @Override
//    public void finish() {
//        super.finish();
//        finishAfterTransition();
//    }
}
