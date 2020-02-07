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
    private ImageView mIvBarCodeNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mIvVerificationCodeImg = findViewById(R.id.img);
        mIvBarCode = findViewById(R.id.barcode);
        mIvBarCodeNo = findViewById(R.id.barcode_with_no);
        createQRCode();
    }

    private void createQRCode() {

        Bitmap bitmap = QRCodeUtil.createQRCodeBitmapNoMargin("9787121336775", 400, 400);
        if (bitmap != null) {
            mIvVerificationCodeImg.setImageBitmap(bitmap);
        }

        Bitmap bitmap1 = BarCodeUtil.creatBarcode("9787121336775", 400, 200);
        if (bitmap != null) {
            mIvBarCode.setImageBitmap(bitmap1);
        }

        try {
            Bitmap bitmap2 = BarCodeUtil.CreateOneDCodeAndString("9787121336775", 400, 200, "9787121336775");
            if (bitmap != null) {
                mIvBarCodeNo.setImageBitmap(bitmap2);
            }
        }catch (Exception e){

        }
    }
}
