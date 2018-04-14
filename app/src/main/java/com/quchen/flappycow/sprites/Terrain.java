package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;

public class Terrain extends Sprite {
    /**
     * Static bitmap to reduce memory usage.
     */
    public Bitmap globalBitmap;

    public enum type {SPIDER, WOODLOG}

    public Terrain(GameView view, Game game, type obstacleType) {
        super(view, game);
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
                }
            }
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
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

}
