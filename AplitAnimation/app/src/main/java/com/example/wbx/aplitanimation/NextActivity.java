package com.example.wbx.aplitanimation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * desc
 * 创建人：wbx
 * 创建时间：2019-01-28
 */
public class NextActivity extends AppCompatActivity {
    private int midData;
    private Bitmap bitmapTop;
    private Bitmap bitmapBottom;

    private int topHeight;
    private int bottomHeight;

    private ImageView mShowImg;

    private ImageView loading_iv_top;
    private ImageView loading_iv_bottm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.next_layout);

        mShowImg = findViewById(R.id.show_img);

        loading_iv_top = findViewById(R.id.loading_iv_top);
        loading_iv_bottm = findViewById(R.id.loading_iv_bottm);

        midData = ScreenShot.mScreenSplitPosition;

        startAnima();
    }

    private void startAnima() {
        cutting();

        final ConstraintLayout container = findViewById(R.id.id_relative_capture);

        loading_iv_top.setImageBitmap(bitmapTop);
        loading_iv_bottm.setImageBitmap(bitmapBottom);

        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                //设置了图片所以在这里获取他们两个的高,就是执行动画的距离

                topHeight = loading_iv_top.getHeight();
                bottomHeight = loading_iv_bottm.getHeight();

                AnimatorSet animatorSet = new AnimatorSet();

               ObjectAnimator topAnim1 = ObjectAnimator.ofFloat(loading_iv_top, "translationY", 0, - topHeight);

               animatorSet.setDuration(3000);
               animatorSet.play(topAnim1);
               animatorSet.start();

                ObjectAnimator bottomAnim = ObjectAnimator.ofFloat(loading_iv_bottm, "translationY", 0, bottomHeight);
                bottomAnim.setDuration(2000);
                bottomAnim.setStartDelay(1000);
                bottomAnim.start();

                container.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void cutting() {
        Bitmap bitmap = ScreenShot.mScreenShotBitmap;
        mShowImg.setImageBitmap(bitmap);

        if(bitmap != null) {
            // 切割第一个图
            bitmapTop = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), midData);
            //且第二个图
            bitmapBottom = Bitmap.createBitmap(bitmap, 0, midData, bitmap.getWidth(), bitmap.getHeight() - midData);
        }
    }

}
