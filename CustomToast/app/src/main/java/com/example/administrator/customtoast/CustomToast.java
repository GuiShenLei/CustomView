package com.example.administrator.customtoast;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wbx on 2017/12/20.
 */

public class CustomToast{
    private static Toast mToast;
    private static TextView mText;

    private static Toast mToast1;
    private static TextView mText1;
    private static CustomToastView mCustomToastView;

    public static void show(Context context, String text){
        if(mToast == null){
            View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout, null);
            mText = view.findViewById(R.id.tv_text);

            mToast = new Toast(context);
            mToast.setView(view);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mText.setText(text);
        mToast.show();
    }

    public static void show1(Context context, String text){
        if(mToast1 == null){
            View view = LayoutInflater.from(context).inflate(R.layout.custom_toast_layout1, null);
            mText1 = view.findViewById(R.id.tv_text1);
            mCustomToastView=view.findViewById(R.id.successView);

            mToast1 = new Toast(context);
            mToast1.setView(view);
            mToast1.setGravity(Gravity.CENTER, 0, 0);
            mToast1.setDuration(Toast.LENGTH_LONG);
        }
        mText1.setText(text);
        mCustomToastView.startAnim();
        mToast1.show();
    }
}
