package com.abexacloud.bubble;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Esta clase permite
 */
public class FloatingBubbleAnimator {
    private static final int ANIMATION_TIME = 500;
    private static final int ANIMATION_STEPS = 60;

    private View bubbleView;
    private WindowManager.LayoutParams bubbleParams;
    private WindowManager windowManager;
    private int sizeX;
    private int sizeY;

    private FloatingBubbleAnimator(Builder builder) {
        bubbleView = builder.bubbleView;
        bubbleParams = builder.bubbleParams;
        windowManager = builder.windowManager;
        sizeX = builder.sizeX;
        sizeY = builder.sizeY;
    }

    public void animate(final float x, final float y) {
        final float startX = bubbleParams.x;
        final float startY = bubbleParams.y;

        // Permite animar el tamaño de la vista en 5 pasos de 0 a 5 en 1 segundo
        ValueAnimator animator = ValueAnimator.ofInt(0, ANIMATION_STEPS).setDuration(ANIMATION_TIME);

        // Se ejecuta cada vez que se modifica el valor del animador 
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                try {
                    // Se obtiene el valor actual del animador y se divide entre 5 para obtener el
                    // valor de 0 a 5 que representa el tamaño de la vista en el eje X y Y respectivamente
                    float currentX = startX + ((x - startX) * (Integer) valueAnimator.getAnimatedValue() / ANIMATION_STEPS);
                    float currentY = startY + ((y - startY) * (Integer) valueAnimator.getAnimatedValue() / ANIMATION_STEPS);

                    // Se actualiza el tamaño de la vista en el eje X y Y respectivamente
                    bubbleParams.x = (int) currentX;
                    bubbleParams.x = Math.max(bubbleParams.x, 0); // No se puede mover la vista hacia la izquierda de la pantalla 
                    bubbleParams.x = Math.min(bubbleParams.x, sizeX - bubbleView.getWidth()); // No se puede mover la vista hacia la derecha de la pantalla

                    bubbleParams.y = (int) currentY;
                    bubbleParams.y = Math.max(bubbleParams.y, 0);
                    bubbleParams.y = Math.min(bubbleParams.y, sizeY - bubbleView.getWidth());

                    // Se actualiza la vista en la pantalla
                    windowManager.updateViewLayout(bubbleView, bubbleParams);
                } catch (Exception exception) {
                    Log.e(FloatingBubbleAnimator.class.getSimpleName(), exception.getMessage());
                }
            }
        });
        animator.start();
    }

    public static final class Builder {
        private View bubbleView;
        private WindowManager.LayoutParams bubbleParams;
        private WindowManager windowManager;
        private int sizeX;
        private int sizeY;

        public Builder() {
        }

        public Builder bubbleView(View val) {
            bubbleView = val;
            return this;
        }

        public Builder bubbleParams(WindowManager.LayoutParams val) {
            bubbleParams = val;
            return this;
        }

        public Builder windowManager(WindowManager val) {
            windowManager = val;
            return this;
        }

        public Builder sizeX(int val) {
            sizeX = val;
            return this;
        }

        public Builder sizeY(int val) {
            sizeY = val;
            return this;
        }

        public FloatingBubbleAnimator build() {
            return new FloatingBubbleAnimator(this);
        }
    }
}

