package com.example.customringdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

public class RoundImage extends AppCompatImageView {
    float width, height;
    int roundPx = 0 ;

    public RoundImage(Context context) {
        this(context, null);
        init(context, null);
    }

    public RoundImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width >= 100 && height > 100) {
            Path path = new Path();
            //四个圆角
            path.moveTo(100, 0);
            path.lineTo(width - 100, 0);
            path.quadTo(width, 0, width, 100);
            path.lineTo(width, height - 100);
            path.quadTo(width, height, width - 100, height);
            path.lineTo(100, height);
            path.quadTo(0, height, 0, height - 100);
            path.lineTo(0, 100);
            path.quadTo(0, 0, 100, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }

}
