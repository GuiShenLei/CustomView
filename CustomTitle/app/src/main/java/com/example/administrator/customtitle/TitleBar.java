package com.example.administrator.customtitle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wbx on 2017/12/6.
 */

public class TitleBar extends RelativeLayout {
    private Context mContext;
    private TitleBarClickListner mClickListner;

    //左侧
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    private Button mLeftButton;
    private LayoutParams mLeftParams;

    //右侧
    private int mRightTextColor;
    private Drawable mRightBackground;
    private String mRightText;
    private Button mRightButton;
    private LayoutParams mRightParams;

    //中间标题
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitle;
    private TextView mTitleView;
    private LayoutParams mTitlParams;

    public TitleBar(Context context) {
        super(context);
        mContext = context;
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttrs(attrs);
        createLayout();
        initOnClickListener();
    }

    private void getAttrs(AttributeSet attrs){
        //通过这个方法，将attrs.xml中定义的所有属性值存储到TypedArray中
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TitleBar);

        //左侧
        mLeftTextColor = ta.getColor(R.styleable.TitleBar_leftTextColor, 0);
        mLeftBackground = ta.getDrawable(R.styleable.TitleBar_leftBackground);
        mLeftText = ta.getString(R.styleable.TitleBar_leftText);

        //右侧
        mRightTextColor = ta.getColor(R.styleable.TitleBar_rightTextColor, 0);
        mRightBackground = ta.getDrawable(R.styleable.TitleBar_rightBackground);
        mRightText = ta.getString(R.styleable.TitleBar_rightText);

        //中间标题
        mTitleTextSize = ta.getDimension(R.styleable.TitleBar_titleTextSize, 0);
        mTitleTextColor = ta.getColor(R.styleable.TitleBar_titleTextColor, 0);
        mTitle = ta.getString(R.styleable.TitleBar_title);

        ta.recycle();//当获取玩所有属性值后，需要调用TypedArray的recyle方法来完成资源的回收
    }

    private void createLayout(){
        mLeftButton = new Button(mContext);
        mRightButton = new Button(mContext);
        mTitleView = new TextView(mContext);

        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        mLeftButton.setText(mLeftText);

        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);
        mRightButton.setText(mRightText);

        mTitleView.setText(mTitle);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        //为组件元素设置相应的布局元素
        mLeftParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        //添加到ViewGroup
        addView(mLeftButton, mLeftParams);

        mRightParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightButton, mRightParams);

        mTitlParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mTitlParams.addRule(CENTER_IN_PARENT, TRUE);
        addView(mTitleView,mTitlParams);
    }

    private void initOnClickListener(){
        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListner.leftClick();
            }
        });
        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListner.rightClick();
            }
        });
    }

    //设置按钮的显示与否，通过id区分按钮，flag区分是否显示
    public void setButtonVisiable(int id, boolean flag){
        if(flag){
            if(id == 0){
                mLeftButton.setVisibility(VISIBLE);
            }else{
                mRightButton.setVisibility(VISIBLE);
            }
        }else{
            if(id == 0){
                mLeftButton.setVisibility(GONE);
            }else{
                mRightButton.setVisibility(GONE);
            }
        }
    }

    //定义接口
    public interface TitleBarClickListner{
        void leftClick();
        void rightClick();
    }

    //暴露一个方法给调用者来注册接口回调
    public void setOnClickListener(TitleBarClickListner listener){
        mClickListner = listener;
    }
}
