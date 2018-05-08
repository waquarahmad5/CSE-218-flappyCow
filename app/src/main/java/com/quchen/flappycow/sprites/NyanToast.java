/**
 * A yummy toast
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

public class NyanToast extends PowerUp {
    
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;

    public NyanToast(GameView view, Game game) {
        super(view, game);
        if(isGlobalBitmapNull()){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.toast);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    private boolean isGlobalBitmapNull() {
        return globalBitmap == null;
    }

    /**
     * When eaten the player will turn into nyan cat.
     */
    public void onCollision() {
        view.changeToNyanCat();
    }
}
