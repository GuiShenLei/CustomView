package com.example.wbx.transitionanim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.Window;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(null);
        getWindow().setReturnTransition(null);
//        getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.transtion_bottom));
//        getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.transtion_bottom));

        setContentView(R.layout.activity_second);
    }

    @Override
    public void onBackPressed() {
        finishAfterTransition();
    }
}
