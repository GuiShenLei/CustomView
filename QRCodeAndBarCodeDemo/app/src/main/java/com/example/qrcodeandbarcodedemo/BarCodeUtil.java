package com.example.qrcodeandbarcodedemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


public class BarCodeUtil {
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

    /******************************条形码**************************************/

    public static Bitmap creatBarcode(String contents, int desiredWidth, int desiredHeight) {
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, barcodeFormat, desiredWidth,
                    desiredHeight);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /***********************************带文字的条形码******************************************/

    /**
     * 将字符串转成图片
     * @param text
     * @param width
     * @param height
     * @return
     */
    /**字体大小*/
    private  static final int TXT_SIZE = 24;
    /**文字距离条形码间距*/
    private  static final int y_offset = 10;

    public static Bitmap StringToBitmap(String text, int width, int height) {
        Bitmap newBitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(newBitmap, 0, 0, null);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(TXT_SIZE);
        textPaint.setColor(Color.BLACK);
        StaticLayout sl= new StaticLayout(text, textPaint, newBitmap.getWidth()-8, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        canvas.translate(0, y_offset);
        canvas.drawColor(Color.WHITE);
        sl.draw(canvas);
        return newBitmap;
    }

    /**
     * 连接两个bitmap
     *  以第一个图为准
     * 优化算法  1.图片不需要铺满，只需要以统一合适的宽度。然后让imageview自己去铺满，不然长图合成长图会崩溃，这里以第一张图为例
     *2.只缩放不相等宽度的图片。已经缩放过的不需要再次缩放
     * @param bit1
     * @param bit2
     * @return
     */
    public static Bitmap CombBitmap(Bitmap bit1, Bitmap bit2) {
        Bitmap newBit = null;
        int width = bit1.getWidth();
        if (bit2.getWidth() != width) {
            int h2 = bit2.getHeight() * width / bit2.getWidth();
            newBit = Bitmap.createBitmap(width, bit1.getHeight() + h2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBit);
            Bitmap newSizeBitmap2 = getNewSizeBitmap(bit2, width, h2);
            canvas.drawBitmap(bit1, 0, 0, null);
            canvas.drawBitmap(newSizeBitmap2, 0, bit1.getHeight(), null);
        } else {
            newBit = Bitmap.createBitmap(width, bit1.getHeight() + bit2.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(newBit);
            canvas.drawBitmap(bit1, 0, 0, null);
            canvas.drawBitmap(bit2, 0, bit1.getHeight(), null);
        }
        return newBit;
    }

    /**
     * 获取新的bitmap
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap getNewSizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        float scaleWidth = ((float) newWidth) / bitmap.getWidth();
        float scaleHeight = ((float) newHeight) / bitmap.getHeight();
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap bit1Scale = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
        return bit1Scale;
    }

    /**
     *  创建带文字的条形码
     * @param content
     * @param w
     * @param h
     * @param stringPrefix
     * @return
     * @throws WriterException
     */
    public static Bitmap CreateOneDCodeAndString(String content, int w, int h, String stringPrefix) throws WriterException {
        return CombBitmap(creatBarcode(content, w, h-TXT_SIZE-y_offset), StringToBitmap(stringPrefix + content, w, TXT_SIZE + y_offset));
    }
}
