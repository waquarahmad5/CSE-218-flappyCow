/**
 * Manages the Bitmap for the background
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

public class Background extends Scene {

    /** Static bitmap to reduce memory usage */
    public static Bitmap globalBitmap;
    
    public Background(GameView view, Game game) {
        super(view, game);
        
        if(globalBitmap == null){
            globalBitmap = Util.getDownScaledBitmapAlpha8(game, R.drawable.bg);
        }
        this.bitmap = globalBitmap;
    }
}
