package com.example.administrator.animation;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Administrator on 2018/1/5.
 */

public class Animation2 extends Activity{
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation2);

        btn1 = findViewById(R.id.btn1);

        ObjectAnimator animator = ObjectAnimator.ofFloat(btn1, "rotation",0f,360f);
        animator.setDuration(5000);
        animator.start();
    }
}
