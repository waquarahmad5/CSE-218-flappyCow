package com.quchen.flappycow.Interfaces;

import android.graphics.Canvas;

import com.quchen.flappycow.sprites.Sprite;

public interface DrawBehavior {
    void draw(Canvas canvas, Sprite sprite);
}
