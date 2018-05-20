/**
 * A Coin
 *
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.Interfaces.SharedObjects;
import com.quchen.flappycow.MainActivity;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;

import android.graphics.Bitmap;

import static com.quchen.flappycow.R.drawable.heart;

public class Heart extends PowerUp implements SharedObjects {
    /**
     * Static bitmap to reduce memory usage.
     */
    public static Bitmap globalBitmap;
    private static int sound = -1;

    public Heart(GameView view, Game game) {
        super(view, game);
        moveNonPlayer = new MoveNonPlayer();
        if(globalBitmap == null){
            globalBitmap = Util.getScaledBitmapAlpha8(game, R.drawable.heart);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth()/(colNr = 12);
        this.height = this.bitmap.getHeight();
        this.frameTime = 1;
        if(sound == -1){
            sound = Game.soundPool.load(game, R.raw.heart, 1);
        }
    }

    /**
     * When eaten the player will turn into nyan cat.
     */
    public void onCollision() {
        playSound();
        game.increaseCoin();
    }

    /**
     * Checks whether this coin is so far to the left, it's not visible anymore.
     * @return
     */
    public boolean isOutOfRange() {
        return this.x + width < 0;
    }

    private void playSound(){
        Game.soundPool.play(sound, MainActivity.volume, MainActivity.volume, 0, 0, 1);
    }


    public void move() {
        changeToNextFrame();
        moveNonPlayer.move(this);
    }
}
