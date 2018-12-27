package com.example.wbx.snackbar;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.container);
        TextView textView = (TextView) findViewById(R.id.text);
        TSnackbar.make(textView, "顶部", Snackbar.LENGTH_LONG).show();
    }
}
