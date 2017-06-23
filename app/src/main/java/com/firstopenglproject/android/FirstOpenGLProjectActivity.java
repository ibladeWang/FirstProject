package com.firstopenglproject.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class FirstOpenGLProjectActivity extends Activity {
    private boolean rendererSet = false;
    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
//        setContentView(R.layout.activity_first_open_glproject);
        ConfigurationInfo cif = ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getDeviceConfigurationInfo();
        boolean supportsEs2 = cif.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic"))
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));

        if (supportsEs2) {
            glSurfaceView.setEGLContextClientVersion(2);
            glSurfaceView.setRenderer(new FirstOpenGLRenderer());
            rendererSet = true;
        } else {
            Toast.makeText(FirstOpenGLProjectActivity.this, "该设备不支持OpenGL ES 2.0", Toast.LENGTH_SHORT).show();
            return;
        }
        setContentView(glSurfaceView);
    }

    //与Activity的生命周期绑定，否则会崩溃
    @Override
    protected void onPause() {
        super.onPause();
        if (rendererSet) glSurfaceView.onPause();
    }

    private <T extends View> T f(int id) {
        return (T) findViewById(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rendererSet) glSurfaceView.onResume();
    }
}
