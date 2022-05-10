package com.abex.floating_bubble.dto;

import android.graphics.drawable.Drawable;

public class ItemDTO {
    private String name;
    private Drawable icon;
    private IBubbleCommand bubbleCommand;

    public ItemDTO(String name, Drawable icon, IBubbleCommand bubbleCommand) {
        this.name = name;
        this.icon = icon;
        this.bubbleCommand = bubbleCommand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public IBubbleCommand getBubbleCommand() {
        return bubbleCommand;
    }

    public void setBubbleCommand(IBubbleCommand bubbleCommand) {
        this.bubbleCommand = bubbleCommand;
    }
}
