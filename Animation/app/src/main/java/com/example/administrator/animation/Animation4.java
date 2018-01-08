package com.example.administrator.animation;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by wbx on 2018/1/5.
 */

public class Animation4 extends Activity{
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation4);

        btn1 = findViewById(R.id.btn1);

        float curTranslationX = btn1.getTranslationX();

        ObjectAnimator animator = ObjectAnimator.ofFloat(btn1, "scaleY",1f,3f,1f);
        animator.setDuration(5000);
        animator.start();
    }
}
