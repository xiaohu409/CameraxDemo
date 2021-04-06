package com.github.cameraxdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ProcessCameraProvider cameraProvider;
    private int defaultCamera = CameraSelector.LENS_FACING_BACK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initView();
        bindData();
    }

    private void initView() {
        previewView = findViewById(R.id.preview_view);
        Button switchBtn = findViewById(R.id.switch_btn_id);
        switchBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (defaultCamera == CameraSelector.LENS_FACING_BACK) {
            defaultCamera = CameraSelector.LENS_FACING_FRONT;
        }
        else {
            defaultCamera = CameraSelector.LENS_FACING_BACK;
        }
        bindPreview(cameraProvider);
    }

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(defaultCamera)
                .build();
        Preview preview = new Preview.Builder()
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview);
        preview.setSurfaceProvider(previewView.createSurfaceProvider());
    }

    private void bindData() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(new Runnable() {

            @Override
            public void run() {
                try {
                    cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }
}