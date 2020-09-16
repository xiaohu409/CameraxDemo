package com.github.cameraxdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.HashSet;
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

    public void addPermission(String permission) {
        permissionSet.add(permission);
    }

    public void requestPermission(RequestCode requestCode) {
        this.mRequestCode = requestCode;
        String[] permissionArray = permissionSet.toArray(new String[]{});
        ActivityCompat.requestPermissions(activity, permissionArray, requestCode.ordinal());
    }

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

    private boolean hasPermission(int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    public interface PermissionCallback {
        void grant(int requestCode);
        void deny(int requestCode);
    }

    public enum RequestCode {
        CameraCode
    }

}
