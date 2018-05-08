package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public interface DeadBehavior {

    /**
     * A dead character falls slowly to the ground.
     */
    void dead(PlayableCharacter player);
}
