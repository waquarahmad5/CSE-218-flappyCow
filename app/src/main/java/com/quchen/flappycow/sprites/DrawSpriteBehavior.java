package com.quchen.flappycow.sprites;

import android.graphics.Canvas;

public class DrawSpriteBehavior implements DrawBehavior {
    /**
     * Draws the frame of the bitmap specified by col and row
     * at the position given by x and y
     * @param canvas Canvas that should be drawn on
     */
    public void draw(Canvas canvas, Sprite sprite){
        sprite.src.set(sprite.col*sprite.width, sprite.row*sprite.height, (sprite.col+1)*sprite.width, (sprite.row+1)*sprite.height);
        sprite.dst.set(sprite.x, sprite.y, sprite.x+sprite.width, sprite.y+sprite.height);
        canvas.drawBitmap(sprite.bitmap, sprite.src, sprite.dst, null);
    }
}
