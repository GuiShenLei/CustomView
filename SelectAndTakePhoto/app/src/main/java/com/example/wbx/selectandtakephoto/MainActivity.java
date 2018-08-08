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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    Button mTakePhoto;
    ImageView mImg;
    TextView mImagePath;
    private Uri mImgUri;

    private File mTempFile;
    private Uri mTempUri;

    public static final int TAKE_PHOTO = 1;
    public static final int SELECT_PHOTO = 2;
    public static final int CROP_PHOTO = 3;

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
                                            startActivityForResult(intent,TAKE_PHOTO);
                                        } else {
                                            Toast.makeText(MainActivity.this, "没有sd卡",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                        break;

                                    }
                                    case 1:
                                        mTempFile = new File(Environment.getExternalStorageDirectory() + File.separator + getPhotoFileName());
                                        mTempUri = Uri.fromFile(mTempFile);

                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_PICK);
                                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                "image/*");
                                        startActivityForResult(intent,SELECT_PHOTO);
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
                    mImgUri = data.getData(); //获取系统返回的照片的Uri
                    cropPhoto();

//                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
//                    if (cursor != null) {
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String path = cursor.getString(columnIndex);  //获取照片路径
//                        cursor.close();
//                        Bitmap bitmap1 = BitmapFactory.decodeFile(path);
//                        mImagePath.setText(path);
//                        mImg.setImageBitmap(bitmap1);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case CROP_PHOTO:
                if(resultCode == RESULT_OK){
                    setPicToView();


//                    Uri uri = data.getData();
//                    try{
//                        Bitmap bitmap1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//                        mImg.setImageBitmap(bitmap1);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
                }
                break;
        }
    }

        private void cropPhoto(){

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(mImgUri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mTempUri);
        startActivityForResult(intent, 3);
    }

    private void setPicToView() {
        try{
            Uri uri = mTempUri;
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            mImg.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    private void cropPhoto(Uri uri){
//
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 150);
//        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, 3);
//    }
//
//    /**
//     * 保存裁剪之后的图片数据
//     *
//     * @param picdata
//     */
//    private void setPicToView(Intent picdata) {
//        Bundle extras = picdata.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            mImg.setImageBitmap(photo);
//        }
//    }



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
