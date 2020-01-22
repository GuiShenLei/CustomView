package com.example.qrcodeandbarcodedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private ImageView mIvVerificationCodeImg;
    private ImageView mIvBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mIvVerificationCodeImg = findViewById(R.id.img);
        mIvBarCode = findViewById(R.id.barcode);
        createQRCode();
    }

    private void createQRCode() {

        Bitmap bitmap = QRCodeUtil.createQRCodeBitmapNoMargin("123456864769002", 400, 400);
        if (bitmap != null) {
            mIvVerificationCodeImg.setImageBitmap(bitmap);
        }

        Bitmap bitmap1 = BarCodeUtil.creatBarcode("123456864769002", 100, 50);
        if (bitmap != null) {
            mIvBarCode.setImageBitmap(bitmap1);
        }
    }
}
