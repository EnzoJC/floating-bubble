package com.abexacloud.bubble.example;

import android.content.Intent;
import android.os.Bundle;
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
        FloatingBubblePermissions.startPermissionRequest(this);
//        startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 101);
        View startBubble = findViewById(R.id.start_bubble);
        startBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), SimpleService.class));
            }
        });
    }
}