package com.example.administrator.animation;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by wbx on 2018/1/5.
 */

public class Animation3 extends Activity{
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation3);

        btn1 = findViewById(R.id.btn1);

        float curTranslationX = btn1.getTranslationX();

        ObjectAnimator animator = ObjectAnimator.ofFloat(btn1, "translationX",curTranslationX,-500f,curTranslationX);
        animator.setDuration(5000);
        animator.start();
    }
}
