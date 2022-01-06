package com.abex.floating_bubble.dto;

import android.graphics.drawable.Drawable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private String name;
    private Drawable icon;
    private IBubbleCommand bubbleCommand;
}
