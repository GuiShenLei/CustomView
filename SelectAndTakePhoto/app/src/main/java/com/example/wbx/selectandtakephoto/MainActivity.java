package com.example.wbx.selectandtakephoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {
    Button mTakePhoto;
    ImageView mImg;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTakePhoto = findViewById(R.id.take_photo);
        mImg = findViewById(R.id.img);

        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] choices = { "相机", "相册" };
                final ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.kefu_choices_item, choices);
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setSingleChoiceItems(adapter, -1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                switch (which) {
                                    case 0: {
                                        String status = Environment.getExternalStorageState();
                                        if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
//                                            doTakePhoto();// 用户点击了从照相机获取
                                            Intent intent = new Intent();
                                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//配置
                                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                                            startActivityForResult(intent,1);
                                        } else {
                                            Toast.makeText(MainActivity.this, "没有sd卡",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                        break;

                                    }
                                    case 1:
//                                        doPickPhotoFromGallery();// 从相册中去获取
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        intent.setType("image/*");
                                        startActivityForResult(intent,2);
                                        break;
                                }
                            }
                        });
                builder.create().show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                mImg.setImageBitmap(bitmap);
                break;
            case 2:
                Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
                mImg.setImageBitmap(bitmap1);
                break;
        }
    }
}
