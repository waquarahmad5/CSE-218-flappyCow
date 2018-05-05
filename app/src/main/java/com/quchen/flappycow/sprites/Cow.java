/**
 * The cow that is controlled by the player
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.MainActivity;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Cow extends PlayableCharacter{
    
    private static final int POINTS_TO_SIR = 23;
    private static final int POINTS_TO_COOL = 35;

    /** Static bitmap to reduce memory usage. */
    public static Bitmap globalBitmap;

    /** The moo sound */
    private static int sound = -1;
    
    /** sunglasses, hats and stuff */
    private Accessory accessory;

    public Cow(GameView view, Game game) {
        super(view, game);
        if(isGlobalBitmapNull()){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.cow);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth()/(colNr = 8);    // The image has 8 frames in a row
        this.height = this.bitmap.getHeight()/4;            // and 4 in a column
        this.frameTime = 3;        // the frame will change every 3 runs
        this.y = game.getResources().getDisplayMetrics().heightPixels / 2;    // Startposition in in the middle of the screen
        
        if(isMute()){
            sound = Game.soundPool.load(game, R.raw.cow, 1);
        }
        
        this.accessory = new Accessory(view, game);
        this.deadBehavior = new DeadCow();
    }

    private boolean isGlobalBitmapNull() {
        return globalBitmap == null;
    }

    private boolean isMute() {
        return sound == -1;
    }

    private void playSound(){
        Game.soundPool.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
    }

    @Override
    public void onTap(){
        super.onTap();
        playSound();
    }
    
    /**
     * Calls super.move
     * and manages the frames. (flattering cape)
     */
    @Override
    public void move(){
        changeToNextFrame();
        super.move();
        
        // manage frames
        if(row != 3){
            // not dead
            if(speedY > getTabSpeed() / 3 && speedY < getMaxSpeed() * 1/3){
                row = 0;
            }else if(speedY > 0){
                row = 1;
            }else{
                row = 2;
            }
        }
        
        if(this.accessory != null){
            this.accessory.moveTo(this.x, this.y);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(shouldDraw()){
            this.accessory.draw(canvas);
        }
    }

    private boolean shouldDraw() {
        return this.accessory != null && !isDead;
    }

    /**
     * Calls super.dead
     * And changes the frame to a dead cow -.-
     */

    public void dead() {
        deadBehavior.dead(this);
    }

    public void revive() {
        this.isDead = false;
        this.row = 0;
        this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_scumbag));
    }

    @Override
    public void upgradeBitmap(int points) {
        super.upgradeBitmap(points);
        if(isPointsToChangePlayerSprite(points, POINTS_TO_SIR)){
            this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_sir));
        }else if(isPointsToChangePlayerSprite(points, POINTS_TO_COOL)){
            this.accessory.setBitmap(Util.getScaledBitmapAlpha8(game, R.drawable.accessory_sunglasses));
        }
    }

    private boolean isPointsToChangePlayerSprite(int points, int pointsTo) {
        return points == pointsTo;
    }

}
