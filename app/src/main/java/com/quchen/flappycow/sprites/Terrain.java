package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;
import android.graphics.Canvas;

public class Terrain extends Sprite implements StaticObjects {
    /**
     * Static bitmap to reduce memory usage.
     */
    public Bitmap globalBitmap;

    @Override
    public void update( int x, Canvas canvas ) {
        draw(canvas);

    }

    public enum type {SPIDER, WOODLOG}

    public Terrain(GameView view, Game game, type obstacleType) {
        super(view, game);
        moveNonPlayer = new MoveNonPlayer();
        if(globalBitmap == null){
            switch(obstacleType){
                case WOODLOG:
                {
                    globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.log_full);
                    break;
                }

                case SPIDER:
                {
                    globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.spider_full);
                    break;
                }
            }
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        drawBehavior = new DrawSpriteBehavior();
    }

    /**
     * Sets the position
     * @param x
     * @param y
     */
    public void init(int x, int y){
        this.x = x;
        this.y = y;
    }


    public void onCollision() {

    }

    /**
     * Checks whether the sprite is touching this.
     * Seeing the sprites as rectangles.
     * @param
     * @return
     */
    /*public boolean isColliding(Sprite sprite){
        if(this.x + getCollisionTolerance() < sprite.x + sprite.width
                && this.x + this.width > sprite.x + getCollisionTolerance()
                && this.y + getCollisionTolerance() < sprite.y + sprite.height
                && this.y + this.height > sprite.y + getCollisionTolerance()){
            return true;
        }
        return false;
    }*/

    public boolean isOutOfRange() {
        return this.x + width < 0;
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

    /**
     * Checks whether the play has passed this sprite.
     * @return
     */
    public boolean isPassed(){
        return this.x + this.width < view.getPlayer().getX();
    }

    public void move(){
        // changeToNextFrame();
        // Its more efficient if only the classes that need this implement it in their move method.
        moveNonPlayer.move(this);
    }

    /**
     * Draws the Terrain onto the canvas
     * @param canvas Canvas that should be drawn on
     */
    public void draw(Canvas canvas){
        drawBehavior.draw(canvas, this);
    }
}
