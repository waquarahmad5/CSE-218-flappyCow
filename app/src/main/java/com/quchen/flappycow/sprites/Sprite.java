/**
 * The template for every game object
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.Interfaces.DrawBehavior;
import com.quchen.flappycow.Interfaces.MoveNonPlayableCharacterBehaviour;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Sprite {

    DrawBehavior drawBehavior;

    MoveNonPlayableCharacterBehaviour moveNonPlayer;

    /** The bitmaps that holds the frames that should be drawn */
    protected Bitmap bitmap;
    
    /** Height and width of one frame of the bitmap */
    protected int height, width;
    
    /** x and y coordinates on the canvas */
    protected int x, y;
    
    /** Horizontal and vertical speed of the sprite */
    protected float speedX, speedY;
    
    /** The source frame of the bitmap that should be drawn */
    protected Rect src;
    
    /** The destination area that the frame should be drawn to */
    protected Rect dst;
    
    /** Coordinates of the frame in the spritesheet */
    protected byte col, row;
    
    /** Number of columns the sprite has */
    protected byte colNr = 1;
    
    /** How long a frame should be displayed */
    protected short frameTime;
    
    /**
     * Counter for the frames
     * Cycling through the columns
     */
    protected short frameTimeCounter;
    
    /** The GameView that holds this Sprite */
    protected GameView view;
    
    /** The context */
    protected Game game;

    /**
     * Height of the ground relative to the height of the bitmap
     */
    final float GROUND_HEIGHT = (1f * /*45*/ 35) / 720;

    public Sprite(GameView view, Game game){
        this.view = view;
        this.game = game;
        frameTime = 1;
        src = new Rect();
        dst = new Rect();
    }
    
    /**
     * Modifies the x and y coordinates according to the speedX and speedY value
     */

    /**
     * Changes the frame by cycling through the columns.
     */
    protected void changeToNextFrame(){
        this.frameTimeCounter++;
        if(this.frameTimeCounter >= this.frameTime){
            this.col = (byte) ((this.col+1) % this.colNr);
            this.frameTimeCounter = 0;
        }
    }
    
    /**
     * Checks whether the sprite is touching this.
     * With the distance of the 2 centers.
     * @param sprite
     * @return
     */
    public boolean isCollidingRadius(Sprite sprite, float factor){
        int m1x = this.x+(this.width>>1);
        int m1y = this.y+(this.height>>1);
        int m2x = sprite.x+(sprite.width>>1);
        int m2y = sprite.y+(sprite.height>>1);
        int dx = m1x - m2x;
        int dy = m1y - m2y;
        int d = (int) Math.sqrt(dy*dy + dx*dx);
        
        if(d < (this.width + sprite.width) * factor
            || d < (this.height + sprite.height) * factor){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * Checks whether the point specified by the x and y coordinates is touching the sprite.
     * @param x
     * @param y
     * @return
     */
    public boolean isTouching(int x, int y){
        return (x  > this.x && x  < this.x + width
            && y  > this.y && y < this.y + height);
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
    
    public int getWidth() {
        return width;
    }

    public abstract void update( int speed, Canvas canvas );


}
