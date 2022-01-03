package com.abexacloud.bubble.example;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import androidx.core.content.ContextCompat;

import com.abexacloud.bubble.FloatingBubbleConfig;
import com.abexacloud.bubble.FloatingBubbleService;
import com.abexacloud.bubble.R;

public class SimpleService extends FloatingBubbleService {
    @Override
    protected FloatingBubbleConfig getConfig() {
        Context context = getApplicationContext();
        return new FloatingBubbleConfig.Builder()
                .bubbleIcon(ContextCompat.getDrawable(context, R.drawable.web_icon))
                .removeBubbleIcon(ContextCompat.getDrawable(context, R.drawable.close_default_icon))
                .expandableView(getInflater().inflate(R.layout.sample_view_1, null))
                .bubbleIconDp(100)
                .removeBubbleIconDp(100)
                .paddingDp(0)
                .borderRadiusDp(25)
                .physicsEnabled(true)
                .expandableColor(Color.TRANSPARENT)
                .triangleColor(Color.BLACK)
                .gravity(Gravity.LEFT)
//                .removeBubbleAlpha(0.5f)
                .build();
    }
}
