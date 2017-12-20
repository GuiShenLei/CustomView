package com.example.administrator.customtoast;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by wbx on 2017/12/20.
 */

public class CustomToastView extends View{

    /**初始化其中的一些变量，实现3个构造函数*/

    RectF mRectF = new RectF();    //矩形，设置toast布局时用

    ValueAnimator mValueAnimator;    //属性动画
    float mAnimatedValue = 0f;

    private Paint mPaint;    //自定义view的画笔

    private float mWidth = 0f;      //view的宽
    private float mEyeWidth = 0f;   //笑脸的眼睛半径
    private float mPadding = 0f;    //view的偏移量
    private float mEndAngle = 0f;    //圆弧结束的度数

    //是左眼还是右眼
    private boolean isSmileLeft = false;
    private boolean isSmileRight = false;


    public CustomToastView(Context context) {
        super(context);
    }

    public CustomToastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToastView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**设置画笔的参数以及矩形的参数*/

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#5cb85c"));
        mPaint.setStrokeWidth(dip2px(2));
    }

    private void initRect(){
        mRectF = new RectF(mPadding, mPadding, mWidth-mPadding, mWidth-mPadding);
    }

    //dip转px。为了支持多分辨率手机
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**重写onMeasure*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        initPaint();
        initRect();
        mWidth = getMeasuredWidth();
        mPadding = dip2px(10);
        mEyeWidth = dip2px(3);

    }

    /*重写OnDraw*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.STROKE);
        //画微笑弧（从左向右画弧）
        canvas.drawArc(mRectF, 180, mEndAngle, false, mPaint);

        //设置画笔为实心
        mPaint.setStyle(Paint.Style.FILL);
        //左眼
        if (isSmileLeft) {
            canvas.drawCircle(mPadding + mEyeWidth + mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
        }
        //右眼
        if (isSmileRight) {
            canvas.drawCircle(mWidth - mPadding - mEyeWidth - mEyeWidth / 2, mWidth / 3, mEyeWidth, mPaint);
        }
    }

    /*自定义View中的动画效果实现*/

    public void startAnim(){
        stopAnim();
        startViewAnim(0f, 1f, 2000);
    }

    public void stopAnim(){
        if(mValueAnimator != null){
            clearAnimation();
            isSmileLeft = false;
            isSmileRight = false;
            mAnimatedValue = 0f;
            mValueAnimator.end();
        }
    }

    /**
     * 开始动画的方法
     * @param startF 起始值
     * @param endF   结束值
     * @param time  动画的时间
     * @return
     */
    private ValueAnimator startViewAnim(float startF, final float endF, long time){
        //设置valueAnimator 的起始值和结束值
        mValueAnimator = ValueAnimator.ofFloat(startF, endF);
        mValueAnimator.setDuration(time);//设置动画时间
        mValueAnimator.setInterpolator(new LinearInterpolator());//设置补间器。控制动画的变化速率
        //设置监听器。监听动画值的变化，做出相应方式
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatedValue = (float)valueAnimator.getAnimatedValue();
                //如果value的值小于0.5
                if(mAnimatedValue < 0.5){
                    isSmileLeft = false;
                    isSmileRight = false;
                    mEndAngle = -360 * (mAnimatedValue);
                }//如果value的值在0.55和0.7之间
                else if (mAnimatedValue > 0.55 && mAnimatedValue < 0.7){
                    mEndAngle = -180;
                    isSmileLeft = true;
                    isSmileRight = false;
                }//其他
                else{
                    mEndAngle = -180;
                    isSmileLeft = true;
                    isSmileRight = true;
                }

                postInvalidate();
            }
        });

        if(!mValueAnimator.isRunning()){
            mValueAnimator.start();
        }
        return mValueAnimator;
    }
}
