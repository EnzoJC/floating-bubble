package com.abexacloud.bubble.example;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.abex.floating_bubble.FloatingBubblePermissions;
import com.abexacloud.bubble.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // FloatingBubbleConfig
        startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 101);
        View startBubble = findViewById(R.id.start_bubble);
        startBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FloatingBubblePermissions.startPermissionRequest(MainActivity.this);
//                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        startService(new Intent(MainActivity.this.getApplicationContext(), SimpleService.class));
//                    }
//                }, 5000);
            }
        });
    }
}