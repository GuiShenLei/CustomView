package com.example.customringdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomArcAndRingView extends View {
    private final Paint paint;

    private int mRingRadius;
    private int mRingWidth;
    private int mArcWidth;
    private int mStartAngle;
    private int mOverAngle;

    public CustomArcAndRingView(Context context) {
        this(context,null);
    }

    public CustomArcAndRingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRingView);
        mRingRadius = typedArray.getDimensionPixelSize(R.styleable.CustomRingView_circle_radius, 0);
        mRingWidth = typedArray.getDimensionPixelSize(R.styleable.CustomRingView_cicle_width,0);
        mArcWidth = typedArray.getDimensionPixelSize(R.styleable.CustomRingView_arc_width, 0);

        mStartAngle = typedArray.getInteger(R.styleable.CustomRingView_start_angle, 0);
        mOverAngle = typedArray.getInteger(R.styleable.CustomRingView_over_angle, 0);

        typedArray.recycle();

        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿
        paint.setDither(true);//防抖动

        //绘制空心圆,也就是圆环，圆环的宽度通过paint.setStrokeWidth()设置
        //圆环的宽度是 圆的半径 + 圆环宽的1/2
        this.paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.paint.setARGB(155, 167, 190, 206);

        //圆心
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;

        this.paint.setStrokeWidth(mRingWidth);
        canvas.drawCircle(centerX,centerY, mRingRadius, this.paint);

        paint.setStyle(Paint.Style.FILL);
        //矩形确定画扇形的范围
        RectF rectF = new RectF(centerX - mArcWidth, centerY-mArcWidth,centerX+mArcWidth, centerY+mArcWidth);
        canvas.drawArc(rectF, mStartAngle, mOverAngle, true, paint);
//
//        //绘制圆环
//        this.paint.setARGB(255, 212 ,225, 233);
//        this.paint.setStrokeWidth(ringWidth);
//        canvas.drawCircle(center,center, innerCircle+1+ringWidth/2, this.paint);
//
//        //绘制外圆
//        this.paint.setARGB(155, 167, 190, 206);
//        this.paint.setStrokeWidth(2);
//        canvas.drawCircle(center,center, innerCircle+ringWidth, this.paint);

        super.onDraw(canvas);
    }
}
