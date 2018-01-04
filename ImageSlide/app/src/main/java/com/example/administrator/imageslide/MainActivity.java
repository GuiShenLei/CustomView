package com.example.administrator.imageslide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<View> mViewImgList;
    private ViewPager mViewPage;
    private int[] mImgRes = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mViewImgList = new ArrayList<View>();

        mViewImgList = new ArrayList<View>();
//        LayoutInflater lf = getLayoutInflater().from(this);
//        for(int i=0; i<mImgRes.length; i++){
//            View view = lf.inflate(R.layout.layout1, null);
//            ImageView img = view.findViewById(R.id.img);
//            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            img.setBackgroundResource(mImgRes[i]);
//            mViewImgList.add(view);
//        }

        LayoutInflater lf = getLayoutInflater().from(this);
        View img1 = lf.inflate(R.layout.layout1, null);
        View img2 = lf.inflate(R.layout.layout2, null);
        View img3 = lf.inflate(R.layout.layout3, null);
        mViewImgList.add(img1);
        mViewImgList.add(img2);
        mViewImgList.add(img3);

        mViewPage = findViewById(R.id.viewpage);
        mViewPage.setAdapter(new MyViewPageAdapter(mViewImgList));
        mViewPage.setCurrentItem(0);
        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mViewPage.setPageTransformer(true, new DepthPageTransformer());
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);
                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}
