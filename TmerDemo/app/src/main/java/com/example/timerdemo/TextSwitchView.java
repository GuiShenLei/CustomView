package com.example.timerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {
    private Context mContext;

    public TextSwitchView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init(){
        setFactory(this);
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.text_in_anim));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.text_out_anim));
    }

    @Override
    public View makeView() {
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        return textView;
    }
}
