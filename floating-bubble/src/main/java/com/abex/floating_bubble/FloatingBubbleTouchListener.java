package com.abex.floating_bubble;

public interface FloatingBubbleTouchListener {

    void onDown(float x, float y);

    void onTap(boolean expanded);

    void onRemove();

    void onMove(float x, float y);

    void onUp(float x, float y);

}
