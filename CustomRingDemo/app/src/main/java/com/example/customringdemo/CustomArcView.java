package com.example.customringdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomArcView extends View {
    private final Paint paint;

    private int mCircleRadius;
    private int mStartAngle;
    private int mOverAngle;

    public CustomArcView(Context context) {
        this(context,null);
    }

    public CustomArcView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRingView);
        mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CustomRingView_circle_radius, 0);
        mStartAngle = typedArray.getInteger(R.styleable.CustomRingView_start_angle, 0);
        mOverAngle = typedArray.getInteger(R.styleable.CustomRingView_over_angle, 0);

        typedArray.recycle();

        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿

        //绘制空心圆,也就是圆环，圆环的宽度通过paint.setStrokeWidth()设置
        //圆环的宽度是 圆的半径 + 圆环宽的1/2
        this.paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.paint.setARGB(155, 167, 190, 206);

        //矩形确定画扇形的范围
        RectF rectF = new RectF(10, 10,getWidth()-10,getHeight()-10);
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
