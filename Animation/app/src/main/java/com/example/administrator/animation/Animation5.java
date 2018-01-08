package com.example.administrator.animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by wbx on 2018/1/5.
 */

public class Animation5 extends Activity{
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation4);

        btn1 = findViewById(R.id.btn1);

        ObjectAnimator moveIn = ObjectAnimator.ofFloat(btn1, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(btn1, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(btn1, "alpha", 1f, 0f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(rotate).with(fadeInOut).after(moveIn);
        animatorSet.setDuration(5000);
        animatorSet.start();
    }
}
