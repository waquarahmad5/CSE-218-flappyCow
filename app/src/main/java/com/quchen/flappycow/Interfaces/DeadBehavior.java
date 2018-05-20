package com.quchen.flappycow.Interfaces;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.quchen.flappycow.sprites.PlayableCharacter;

public interface DeadBehavior {

    /**
     * A dead character falls slowly to the ground.
     */
    void dead(PlayableCharacter player);
}
