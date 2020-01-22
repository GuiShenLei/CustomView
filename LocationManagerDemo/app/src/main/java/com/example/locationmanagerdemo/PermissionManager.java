package com.example.locationmanagerdemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PermissionManager {
    private PermissionCallback mCallBack;
    private int mRequestCode;

    private Activity mActivity;

    public PermissionManager(Activity activity){
        mActivity = activity;
    }

    /**
     * Request permissions.
     *
     * @param permissions permission list
     */
    public void askForPermission(PermissionCallback permissionCallback, String... permissions) {
        if (null == permissionCallback || null == permissions || null == mActivity) {
            return;
        }

        mCallBack = permissionCallback;
        Random random = new Random();
        mRequestCode = random.nextInt(255);

        if (Build.VERSION.SDK_INT < 23 || permissions.length == 0) {// android6.0已下不需要申请，直接为"同意"
            if (mCallBack != null) {
                mCallBack.onGranted();
            }
            return;
        }

        List<String> denyPermissions = checkSelfPermissions(mActivity, permissions);

        if (denyPermissions == null || denyPermissions.size() == 0) {
            permissionCallback.onGranted();
            return;
        }

        ActivityCompat.requestPermissions(mActivity, denyPermissions.toArray(new String[denyPermissions.size()]), mRequestCode);
    }

    /**
     * @param context     context
     * @param permissions permission list
     */
    private List<String> checkSelfPermissions(final Context context, String... permissions) {
        if (null == permissions || null == context) {
            return null;
        }

        List<String> denyPermissions = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                denyPermissions.add(permission);
            }
        }

        return denyPermissions;
    }

    /**
     * activity 必须重写onRequestPermissionsResult方法，并调用此方法，否则没有回调
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(mCallBack == null || requestCode != mRequestCode){
            return;
        }

        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {// 拒绝权限
                // 对于 ActivityCompat.shouldShowRequestPermissionRationale
                // 1：用户拒绝了该权限，没有勾选"不再提醒"，此方法将返回true。
                // 2：用户拒绝了该权限，有勾选"不再提醒"，此方法将返回 false。
                // 3：如果用户同意了权限，此方法返回false
                if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[i])) {
                    // 拒绝选了"不再提醒"，一般提示跳转到权限设置页面
                    mCallBack.onNeverAsk();
                } else {
                    mCallBack.onDenied();
                }
                return;
            }
        }
        mCallBack.onGranted();
    }

}
