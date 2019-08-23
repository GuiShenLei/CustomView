package com.example.customringdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CustomCircleView extends View {
    private final Paint paint;
    private final Context context;

    /**圆的半径*/
    private int mCircleRadius;

    public CustomCircleView(Context context) {
        this(context,null);
    }

    public CustomCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRingView);
        mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CustomRingView_circle_radius, 0);
        typedArray.recycle();

        this.context = context;
        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿

        //绘制实心圆，
        this.paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;

        //绘制内圆
        this.paint.setARGB(155, 167, 190, 206);
        //绘制圆设不设置画笔粗细都行，无明显区别
        //this.paint.setStrokeWidth(5);
        canvas.drawCircle(centerX,centerY, mCircleRadius, this.paint);

        super.onDraw(canvas);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
