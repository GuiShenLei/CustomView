package com.example.wbx.aplitanimation;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {
    private TextView mSplitTop;
    private TextView mSplitBottom;
    private Button mBtn;

    private ConstraintLayout mLayout;

    private ImageView mImg;
    private MediaProjectionManager mMediaProjectionManager;

    private int midData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        mSplitTop = findViewById(R.id.split_top);

        mLayout = findViewById(R.id.container);

        mImg = findViewById(R.id.show_img);

        mBtn = findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivityNoAnim();
            }
        });

//        getMessureHeight();
    }

    private void getMessureHeight() {
        mLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                midData = (int)mBtn.getY();
                ScreenShot.mScreenSplitPosition = midData;
                mLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void openActivityNoAnim() {
        midData = (int)mBtn.getY();
        ScreenShot.mScreenSplitPosition = midData;
        ScreenShot.takeScreenShot3(this);
        startactivity();
//        startShotScreen();
//        ScreenShot.savaScreenShot(mLayout);
//        ScreenShot.shot2(mLayout);
    }

    private void startactivity(){

                Intent intent = new Intent(MainActivity.this, NextActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                startActivity(intent);

    }

    private void startShotScreen(){
        if (Build.VERSION.SDK_INT >= 21) {
            mMediaProjectionManager = (MediaProjectionManager)getApplication().getSystemService(Context.MEDIA_PROJECTION_SERVICE);

            startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), 1);
        } else {
            Log.e("TAG", "版本过低,无法截屏");
        }
    }

    ImageReader mImageReader = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && Build.VERSION.SDK_INT >= 21) {
            final MediaProjection mMediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);

            DisplayMetrics outMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int windowWidth = outMetrics.widthPixels;
            int windowHeight = outMetrics.heightPixels;

            mImageReader = ImageReader.newInstance(windowWidth, windowHeight, 0x1, 2); //ImageFormat.RGB_565
            final VirtualDisplay mVirtualDisplay = mMediaProjection.createVirtualDisplay("screen-mirror",
                    windowWidth, windowHeight, Resources.getSystem().getDisplayMetrics().densityDpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                    mImageReader.getSurface(), null, null);

            mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                @SuppressLint("NewApi")
                @Override
                public void onImageAvailable(ImageReader imageReader) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Image image = null;
                            try {
                                image = mImageReader.acquireLatestImage();
                                if(image != null) {
                                    int width = image.getWidth();
                                    int height = image.getHeight();
                                    final Image.Plane[] planes = image.getPlanes();
                                    final ByteBuffer buffer = planes[0].getBuffer();
                                    int pixelStride = planes[0].getPixelStride();
                                    int rowStride = planes[0].getRowStride();
                                    int rowPadding = rowStride - pixelStride * width;
                                    Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
                                    bitmap.copyPixelsFromBuffer(buffer);
                                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);

                                    ScreenShot.mScreenShotBitmap = bitmap;

                                    startactivity();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            } finally {
                                if (image != null) {
                                    image.close();
                                }
                                if (mImageReader != null) {
                                    mImageReader.close();
                                }
                                if (mVirtualDisplay != null) {
                                    mVirtualDisplay.release();
                                }

                                mImageReader.setOnImageAvailableListener(null, null);
                                mMediaProjection.stop();
                            }
                        }
                    }, 300);

                }
            }, getBackgroundHandler());
        }
    }

    Handler backgroundHandler;

    private Handler getBackgroundHandler() {
        if (backgroundHandler == null) {
            HandlerThread backgroundThread =
                    new HandlerThread("catwindow", android.os.Process
                            .THREAD_PRIORITY_BACKGROUND);
            backgroundThread.start();
            backgroundHandler = new Handler(backgroundThread.getLooper());
        }
        return backgroundHandler;
    }


    private Bitmap getBitmap(){
        Bitmap oldBitmap = null;

        oldBitmap = Bitmap.createBitmap(mSplitTop.getWidth(), mSplitTop.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(oldBitmap);
        mSplitTop.draw(canvas);

        mImg.setImageBitmap(oldBitmap);

        return oldBitmap;
    }

    // 获取指定Activity的截屏，保存到png文件
    public Bitmap takeScreenShot() {
        // View是你需要截图的View
        View view = getWindow().getDecorView();
        view.buildDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap b1 = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        //获取屏幕宽和高
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }
}
