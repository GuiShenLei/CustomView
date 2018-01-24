package com.example.fragmeentdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by 鬼神泪 on 2018/1/14.
 */

public class Fragmnent1 extends Fragment {
    Button btn1;
    TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment01_layout,container,false);
        btn1 = view.findViewById(R.id.show_text);
        text = view.findViewById(R.id.text);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("hello");
            }
        });

        return view;
    }
}
