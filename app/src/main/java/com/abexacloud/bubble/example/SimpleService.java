package com.abexacloud.bubble.example;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;

import androidx.core.content.ContextCompat;

import com.abex.floating_bubble.FloatingBubbleConfig;
import com.abex.floating_bubble.FloatingBubbleService;
import com.abex.floating_bubble.dto.IBubbleCommand;
import com.abex.floating_bubble.dto.ItemDTO;
import com.abexacloud.bubble.R;

import java.util.ArrayList;
import java.util.List;

public class SimpleService extends FloatingBubbleService {
    public static final String TAG = SimpleService.class.getSimpleName();

    @Override
    public void setupWindow() {
        super.setupWindow();
        windowManager = AnyService.windowManager;
    }

    @Override
    protected FloatingBubbleConfig getConfig() {
        Context context = getApplicationContext();
        List<ItemDTO> listaItemDTO = new ArrayList<>();

        listaItemDTO.add(new ItemDTO(
                "Atrás",
                ContextCompat.getDrawable(context, R.drawable.back),
                new IBubbleCommand() {
                    @Override
                    public void execute() {
                        Log.i(TAG, "Back");
                    }
                }));
        listaItemDTO.add(new ItemDTO(
                "Inicio",
                ContextCompat.getDrawable(context, R.drawable.home),
                new IBubbleCommand() {
                    @Override
                    public void execute() {
                        Log.i(TAG, "Home");
                    }
                }));
        listaItemDTO.add(new ItemDTO(
                "Recientes",
                ContextCompat.getDrawable(context, R.drawable.bubble_default_icon),
                new IBubbleCommand() {
                    @Override
                    public void execute() {
                        Log.i(TAG, "Recent Apps");
                    }
                }));
        int bubbleSize = 70;
        return new FloatingBubbleConfig.Builder()
                .bubbleIcon(ContextCompat.getDrawable(context, R.drawable.web_icon))
                .removeBubbleIcon(ContextCompat.getDrawable(context, R.drawable.close_default_icon))
                .bubbleIconDp(bubbleSize)
                .removeBubbleIconDp(bubbleSize)
                .paddingDp(0)
                .borderRadiusDp(25)
                .physicsEnabled(true)
                .gravity(Gravity.START)
                .listaItemDTO(listaItemDTO)
//                .removeBubbleAlpha(0.5f)
                .build();
    }
}
