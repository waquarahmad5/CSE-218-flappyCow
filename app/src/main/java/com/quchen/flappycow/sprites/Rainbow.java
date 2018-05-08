/**
 * Rainbow tail for the nyan cat
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Rainbow extends Sprite implements SharedObjects {
    
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;
    
    public Rainbow(GameView view, Game game) {
        super(view, game);
        moveNonPlayer = new MoveNonPlayer();
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.rainbow);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth()/(colNr = 4);
        this.height = this.bitmap.getHeight()/3;
        drawBehavior = new DrawSpriteBehavior();
    }


    public void move() {
        changeToNextFrame();
        moveNonPlayer.move(this);
    }


    /**
     * Draws the frame of the bitmap specified by col and row
     * at the position given by x and y
     * @param canvas Canvas that should be drawn on
     */
    public void draw(Canvas canvas){
        drawBehavior.draw(canvas, this);
    }
    
}
