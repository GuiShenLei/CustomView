package com.example.choosepicdemo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    Button mTakePhoto;
    ImageView mImg;
    TextView mTime;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;

    private Uri mImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTakePhoto = findViewById(R.id.take_photo);
        mImg = findViewById(R.id.img);
        mTime = findViewById(R.id.time);


        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File storageDir = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
                storageDir.mkdirs();// 创建照片的存储目录
                File outputImage = new File(storageDir, getPhotoFileName());

//                try{
//                    if(outputImage.exists()){
//                        outputImage.delete();
//                    }
//
//                    outputImage.createNewFile();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }

                mImgUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mImgUri);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        mTime.setText(getPhotoFileName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(mImgUri, "image/*");
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImgUri);
                    intent.putExtra("aspectX",1);
                    intent.putExtra("aspectY",1);
                    startActivityForResult(intent, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImgUri));
                        mImg.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
                default:
                    break;
        }
    }


    /**
     * 用当前时间给取得的图片命名
     *
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date) + ".jpg";
    }
}
