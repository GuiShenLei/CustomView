package com.example.wbx.aplitanimation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * desc
 * 创建人：wbx
 * 创建时间：2019-01-28
 */
public class ScreenShot {

    /**屏幕分裂的位置*/
    public static int mScreenSplitPosition;

    public static Bitmap mScreenShotBitmap;

    public static void savaScreenShot(ViewGroup viewGroup){
        Bitmap bitmap = null;

        bitmap = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        viewGroup.draw(canvas);

        if(bitmap != null){
            mScreenShotBitmap = bitmap;
        }
    }

    // 程序入口
    public static void shot(MainActivity activity) {
        ScreenShot.savePic(ScreenShot.takeScreenShot(activity));
    }

    // 程序入口
    public static void shot2(ViewGroup viewGroup) {
        ScreenShot.savePic(ScreenShot.takeScreenShot2(viewGroup));
    }

    public static Bitmap takeScreenShot3(MainActivity activity) {
        View view = activity.getWindow().getDecorView();

        Bitmap bitmap = null;

        bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        mScreenShotBitmap = bitmap;

        return bitmap;
    }

    public static Bitmap takeScreenShot2(ViewGroup viewGroup){
        Bitmap bitmap = null;

        bitmap = Bitmap.createBitmap(viewGroup.getWidth(), viewGroup.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        viewGroup.draw(canvas);

        return bitmap;
    }


    // 获取指定Activity的截屏，包括状态栏，但状态栏是空白
    public static Bitmap takeScreenShot(MainActivity activity) {
        /*获取windows中最顶层的view*/
        View view = activity.getWindow().getDecorView();

        //允许当前窗口保存缓存信息
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bmp = view.getDrawingCache();

        //获取屏幕宽和高
        WindowManager windowManager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        //去掉状态栏
        Bitmap bitmap = Bitmap.createBitmap(bmp, 0, 0, width,
                height);

        //销毁缓存信息
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        mScreenShotBitmap = bitmap;

        return bitmap;
    }

    /**保存截图到sdcard*/
    public static void savePic(Bitmap bitmap) {
        File fileDir = new File(Environment.getExternalStorageDirectory() + "/miguzhongxin/migushot");

        if(!fileDir.exists()){
            fileDir.mkdirs();
        }

        File avaterFile = new File(fileDir, "shot.png");
        if(avaterFile.exists()){
            avaterFile.delete();
         }

        try {
            FileOutputStream fos = new FileOutputStream(avaterFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**从sd卡获取截图*/
    public static Bitmap getPic(){
        Bitmap bitmap = null;
        try {
            File avaterFile = new File(Environment.getExternalStorageDirectory() + "/migu/migushot/shot.png");
            if (avaterFile.exists()) {
                bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/migu/migushot/shot.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
