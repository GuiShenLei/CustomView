package com.example.baidulocationdemo;

public interface PermissionCallback {
    /**
     * 权限通过
     */
    void onGranted();
    /**
     * 权限拒绝
     */
    void onDenied();
    /**
     * 用户以前拒绝过权限，且勾选了不再询问，或是手机系统本身禁止了该权限
     */
    void onNeverAsk();
}
