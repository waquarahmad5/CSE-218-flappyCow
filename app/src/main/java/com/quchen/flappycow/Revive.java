package com.quchen.flappycow;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.google.android.gms.games.Player;
import com.quchen.flappycow.sprites.Obstacle;
import com.quchen.flappycow.sprites.PlayableCharacter;
import com.quchen.flappycow.sprites.PowerUp;
import com.quchen.flappycow.sprites.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Revive {

    private int numberOfRevive;
    private ArrayList<ReviveObserver> reviveObservers;
    private ClearCanvas clearCanvas;
    private ClearObstacles clearObstacles;
    private ClearPowerUps clearPowerUps;

    public Revive(int n)
    {
        numberOfRevive = n;
        clearCanvas = new ClearCanvas();
        clearObstacles = new ClearObstacles();
        clearPowerUps = new ClearPowerUps();

        register(clearCanvas);
        register(clearObstacles);
        register(clearPowerUps);

    }
    public void revive(final GameView gameView) {

        // This needs to run another thread, so the dialog can close.
        new Thread(new Runnable() {
            @Override
            public void run() {
                setupRevive(gameView);
            }
        }).start();

    }

    /**
     * Sets the player into startposition
     * Removes obstacles.
     * Let's the character blink a few times.
     */
    public void setupRevive(GameView gameView)
        {
            for(ReviveObserver ro : reviveObservers)
            {
                ro.update(gameView);
            }
            gameView.resume();
        }

    public void register(ReviveObserver r) {
        reviveObservers.add(r);
        System.out.println(reviveObservers.size());

    }

    public void unregister (ReviveObserver r) {
        int index = reviveObservers.indexOf(r);
        reviveObservers.remove(index);
    }
}
