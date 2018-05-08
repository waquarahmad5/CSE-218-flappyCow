/**
 * The pauseButton
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;
import android.graphics.Canvas;

public class PauseButton extends Sprite implements SharedObjects{
    public PauseButton(GameView view, Game game) {
        super(view, game);
        this.bitmap = Util.getScaledBitmapAlpha8(game, R.drawable.pause_button);
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        drawBehavior = new DrawSpriteBehavior();
    }
    
    /**
     * Sets the button in the right upper corner.
     */

    public void move(){
        this.x = this.view.getWidth() - this.width;
        this.y = 0;
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