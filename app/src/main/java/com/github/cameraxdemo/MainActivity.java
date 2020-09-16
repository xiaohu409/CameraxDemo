package com.github.cameraxdemo;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PermissionUtil permissionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionUtil = new PermissionUtil(this);
        initView();
    }

    private void initView() {
        Button openBtn = findViewById(R.id.open_btn_id);
        openBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissionsCa();
            }
        });
    }

    private void requestPermissionsCa() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionUtil.setPermissionCallback(new PermissionUtil.PermissionCallback() {
                @Override
                public void grant(int requestCode) {
                    startActivity(new Intent(MainActivity.this, CameraActivity.class));
                }

                @Override
                public void deny(int requestCode) {
                    Toast.makeText(MainActivity.this, "拒绝了权限申请", Toast.LENGTH_SHORT).show();
                }
            });
            permissionUtil.addPermission(Manifest.permission.CAMERA);
            permissionUtil.requestPermission(PermissionUtil.RequestCode.CameraCode);
        }
        else {
            startActivity(new Intent(MainActivity.this, CameraActivity.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}