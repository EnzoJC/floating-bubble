package com.abexacloud.bubble.example;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.abex.floating_bubble.FloatingBubbleConfig;
import com.abex.floating_bubble.FloatingBubbleService;
import com.abex.floating_bubble.dto.ItemDTO;
import com.abexacloud.bubble.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleService extends FloatingBubbleService {
    public static final String TAG = SimpleService.class.getSimpleName();
    private WindowManager auxWindowManager;

    @Override
    public void setupWindow() {
        super.setupWindow();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        auxWindowManager = windowManager;
    }

    @Override
    protected FloatingBubbleConfig getConfig() {
        Context context = getApplicationContext();
        List<ItemDTO> listaItemDTO = new ArrayList<>();

        listaItemDTO.add(new ItemDTO("Atrás", () -> {
            Log.i(TAG, "A");
        }));
        listaItemDTO.add(new ItemDTO("Inicio", () -> {
            Log.i(TAG, "I");
        }));
        listaItemDTO.add(new ItemDTO("Recientes", () -> {
            Log.i(TAG, "R");
        }));

        return new FloatingBubbleConfig.Builder()
//                .bubbleIcon(ContextCompat.getDrawable(context, R.drawable.web_icon))
                .removeBubbleIcon(ContextCompat.getDrawable(context, R.drawable.close_default_icon))
                .bubbleIconDp(64)
                .removeBubbleIconDp(64)
                .paddingDp(0)
                .borderRadiusDp(25)
                .physicsEnabled(true)
                .expandableColor(Color.TRANSPARENT)
                .triangleColor(Color.BLACK)
                .gravity(Gravity.START)
                .listaItemDTO(listaItemDTO)
//                .removeBubbleAlpha(0.5f)
                .build();
    }
}
