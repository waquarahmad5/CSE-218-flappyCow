/**
 * The SuperClass of every character that is controlled by the player
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public abstract class PlayableCharacter extends Sprite implements DynamicObjects,SharedObjects{
    
    protected boolean isDead = false;
    
    public PlayableCharacter(GameView view, Game game) {
        super(view, game);
        move();
    }
    
    /**
     * Calls super.move
     * Moves the character to 1/6 of the horizontal screen
     * Manages the speed changes -> Falling
     */

    public void move(){
        this.x = this.view.getWidth() / 6;
        
        if(speedY < 0){
            // The character is moving up
            speedY = speedY * 2 / 3 + getSpeedTimeDecrease() / 2;
        }else{
            // the character is moving down
            this.speedY += getSpeedTimeDecrease();
        }
        
        if(this.speedY > getMaxSpeed()){
            // speed limit
            this.speedY = getMaxSpeed();
        }

        x+= speedX;
        y+= speedY;
    }

    /**
     * A dead character falls slowly to the ground.
     */
//    public void dead(){
//        this.isDead = true;
//        this.speedY = getMaxSpeed()/2;
//    }
    
    /**
     * Let the character flap up.
     */
    public void onTap(){
        this.speedY = getTabSpeed();
        this.y += getPosTabIncrease();
    }
    
    /**
     * Falling speed limit
     * @return
     */
    protected float getMaxSpeed(){
        // 25 @ 720x1280 px
        return view.getHeight() / 51.2f;
    }
    
    /**
     * Every run cycle the speed towards the ground will increase.
     * @return
     */
    protected float getSpeedTimeDecrease(){
        // 4 @ 720x1280 px
        return view.getHeight() / 320;
    }
    
    /**
     * The character gets this speed when taped.
     * @return
     */
    protected float getTabSpeed(){
        // -80 @ 720x1280 px
        return - view.getHeight() / 16f;
    }
    
    /**
     * The character jumps up the pixel height of this value.
     * @return
     */
    protected int getPosTabIncrease(){
        // -12 @ 720x1280 px
        return - view.getHeight() / 100;
    }
    public abstract void dead();
    public abstract void revive();
    public void upgradeBitmap(int points){
        // Change bitmap, maybe when a certain amount of point is reached.
    }
    
    public boolean isDead(){
        return this.isDead;
    }
    /**
     * Checks whether the sprite is touching the ground or the sky.
     * @return
     */
    public boolean isTouchingEdge(){
        return isTouchingGround() || isTouchingSky();
    }

    /**
     * Checks whether the sprite is touching the ground.
     * @return
     */
    public boolean isTouchingGround(){
        return this.y + this.height > this.view.getHeight() - this.view.getHeight() * GROUND_HEIGHT;
    }

    /**
     * Checks whether the sprite is touching the sky.
     * @return
     */
    public boolean isTouchingSky(){
        return this.y < 0;
    }
}
