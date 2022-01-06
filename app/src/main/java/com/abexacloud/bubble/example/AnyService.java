package com.abexacloud.bubble.example;

import android.accessibilityservice.AccessibilityService;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

public class AnyService extends AccessibilityService {
    public static volatile WindowManager windowManager;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }
}
