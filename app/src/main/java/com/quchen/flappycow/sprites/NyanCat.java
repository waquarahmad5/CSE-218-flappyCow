/**
 * Nyan Cat character
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 * 
 * Nyan Cat was drawn by Christopher Torres and momo momo remixed the music by daniwell
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class NyanCat extends PlayableCharacter {
    
    /** Static bitmap to reduce memory usage */
    public static Bitmap globalBitmap;
    
    /** The rainbow tail behind the cat */
    private Rainbow rainbow;
    
    public NyanCat(GameView view, Game game) {
        super(view, game);
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.nyan_cat);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight()/2;
        this.y = game.getResources().getDisplayMetrics().heightPixels / 2;
        
        this.rainbow = new Rainbow(view, game);
        this.deadBehavior = new DeadCat();
        movePlayer = new MoveCat();
    }
    
    /**
     * Moves itself via super.move
     * and moves the rainbow and manages its frames
     */

    public void move(){
        movePlayer.move(this);
        if(rainbow != null){
            manageRainbowMovement();            
        }
    }
    
    private void manageRainbowMovement(){
        rainbow.y = this.y;        // nyan cat and rainbow bitmap have the same height
        rainbow.x = this.x - rainbow.width;
        rainbow.move();
        
        // manage frames of the rainbow
        if(speedY > getTabSpeed() / 3 && speedY < getMaxSpeed() * 1/3){
            rainbow.row = 0;
        }else if(speedY > 0){
            rainbow.row = 1;
        }else{
            rainbow.row = 2;
        }
    }

    /**
     * Draws the NyanCat onto the canvas
     * @param canvas Canvas that should be drawn on
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(rainbow != null && !isDead){
            rainbow.draw(canvas);
        }
    }

    /**
     * Calls super.dead,
     * removes the rainbow tail
     * and set the bitmapframe to a dead cat -.-
     */

    public void dead() {
        deadBehavior.dead(this);
        
        // Maybe an explosion
    }

    public void revive() {
        this.isDead = false;
        this.row = 0;
        manageRainbowMovement();
    }


}
