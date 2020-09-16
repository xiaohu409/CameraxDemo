package com.github.cameraxdemo;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 项目名称：CameraXDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2020/9/16
 * 文件版本：1.0
 */
public class PermissionUtil {

    private Activity activity;
    private Set<String> permissionSet;
    private RequestCode mRequestCode;
    private PermissionCallback permissionCallback;

    public PermissionUtil(Activity activity) {
        this.activity = activity;
        permissionSet = new HashSet<>();
    }

    /**
     * 添加权限
     * @param permission
     */
    public void addPermission(String permission) {
        permissionSet.add(permission);
    }

    /**
     * 移除权限
     * @param permission
     */
    public void removePermission(String permission) {
        Iterator<String> iterator = permissionSet.iterator();
        while (iterator.hasNext()) {
            String curPermission = iterator.next();
            if (curPermission.equals(permission)) {
                iterator.remove();
            }
        }
    }

    /**
     * 清空权限
     */
    public void clearPermission() {
        permissionSet.clear();
    }

    /**
     * 设置请求code
     * @param requestCode
     */
    public void requestPermission(RequestCode requestCode) {
        this.mRequestCode = requestCode;
        String[] permissionArray = permissionSet.toArray(new String[]{});
        ActivityCompat.requestPermissions(activity, permissionArray, requestCode.ordinal());
    }

    /**
     * 设置回调
     * @param permissionCallback
     */
    public void setPermissionCallback(PermissionCallback permissionCallback) {
        this.permissionCallback = permissionCallback;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == mRequestCode.ordinal()) {
            if (permissionCallback == null) {
                throw new IllegalArgumentException("permissionCallback is null");
            }
            if (hasPermission(grantResults)) {
                permissionCallback.grant(requestCode);
            }
            else {
                permissionCallback.deny(requestCode);
            }
        }
    }

    /**
     * 判断是否授予了权限 是返回true 否则返回false
     * @param grantResults
     * @return
     */
    private boolean hasPermission(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 回调
     */
    public interface PermissionCallback {
        /**
         * 授权权限
         * @param requestCode
         */
        void grant(int requestCode);

        /**
         * 拒绝权限
         * @param requestCode
         */
        void deny(int requestCode);
    }

    /**
     * 请求Code枚举
     */
    public enum RequestCode {
        CameraCode
    }

}
