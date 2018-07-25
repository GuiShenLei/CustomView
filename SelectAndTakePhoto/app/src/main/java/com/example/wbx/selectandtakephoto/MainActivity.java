package com.example.wbx.selectandtakephoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends Activity {
    Button mTakePhoto;
    ImageView mImg;
    TextView mImagePath;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTakePhoto = findViewById(R.id.take_photo);
        mImg = findViewById(R.id.img);
        mImagePath = findViewById(R.id.image_path);

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
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_PICK);
                                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                "image/*");
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
                try {
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        Bitmap bitmap1 = BitmapFactory.decodeFile(path);
                        mImagePath.setText(path);
                        mImg.setImageBitmap(bitmap1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
