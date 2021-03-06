package com.quchen.flappycow;

import com.quchen.flappycow.Interfaces.ReviveObserver;

import java.util.ArrayList;

public class Revive {

    private int numberOfRevive;
    private ArrayList<ReviveObserver> reviveObservers;
    private ClearCanvas clearCanvas;
    private ClearObstacles clearObstacles;
    private ClearPowerUps clearPowerUps;
    private RevivePlayer revivePlayer;

    public Revive(int n)
    {
        numberOfRevive = n;
        reviveObservers = new ArrayList<ReviveObserver>();
        clearCanvas = new ClearCanvas();
        clearObstacles = new ClearObstacles();
        clearPowerUps = new ClearPowerUps();
        revivePlayer = new RevivePlayer();

        register(clearObstacles);
        register(clearPowerUps);
        register(clearCanvas);
        register(revivePlayer);

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
            //gameView.getPlayerInstance().revive();
            //gameView.resume();

            gameView.resume();





        }

    public void register(ReviveObserver r) {
        reviveObservers.add(r);
        //System.out.println(reviveObservers.size());

    }

    public void unregister (ReviveObserver r) {
        int index = reviveObservers.indexOf(r);
        reviveObservers.remove(index);
    }
}
