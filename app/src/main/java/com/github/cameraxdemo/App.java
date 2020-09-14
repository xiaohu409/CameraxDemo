package com.github.cameraxdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.camera.camera2.Camera2Config;
import androidx.camera.core.CameraXConfig;

/**
 * 项目名称：CameraXDemo
 * 文件名称：
 * 文件描述：
 * 创建作者：胡涛
 * 创建日期：2020/9/14
 * 文件版本：1.0
 */
public class App extends Application implements CameraXConfig.Provider {

    @NonNull
    @Override
    public CameraXConfig getCameraXConfig() {
        return Camera2Config.defaultConfig();
    }
}
