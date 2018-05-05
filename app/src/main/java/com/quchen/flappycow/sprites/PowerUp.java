/**
 * The abstract spriteclass for power-ups
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public abstract class PowerUp extends Sprite implements StaticObjects,SharedObjects{
    public PowerUp(GameView view, Game game) {
        super(view, game);
        moveNonPlayer = new MoveNonPlayer();
        init();
    }


    /**
     * Sets this sprite above the visible screen.
     * At x = 4/5 of the screen.
     * Uses the speed of the GameView to let the power-up fall slowly down.
     */
    private void init(){
        this.x = view.getWidth() * 4/5;
        this.y = 0 - this.height;
        this.speedX = - view.getSpeedX();
        this.speedY = (int) (view.getSpeedX() * (Math.random() + 0.5));
    }
    /**
     * Checks whether the sprite is touching this.
     * Seeing the sprites as rectangles.
     * @param sprite
     * @return
     */
    public boolean isColliding(Sprite sprite){
        if(this.x + getCollisionTolerance() < sprite.x + sprite.width
                && this.x + this.width > sprite.x + getCollisionTolerance()
                && this.y + getCollisionTolerance() < sprite.y + sprite.height
                && this.y + this.height > sprite.y + getCollisionTolerance()){
            return true;
        }
        return false;
    }
    /**
     * Gives a value that will be tolerated when touching a sprite.
     * Because my images have some whitespace to the edge.
     * @return
     */
    private int getCollisionTolerance(){
        // 25 @ 720x1280 px
        return game.getResources().getDisplayMetrics().heightPixels / 50;
    }

    public boolean isOutOfRange() {
        return this.x + width < 0;
    }

     public void move(){
        // changeToNextFrame();
        // Its more efficient if only the classes that need this implement it in their move method.

        moveNonPlayer.move(this);
    }
}
