package com.quchen.flappycow;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.google.android.gms.games.Player;
import com.quchen.flappycow.sprites.Obstacle;
import com.quchen.flappycow.sprites.PlayableCharacter;
import com.quchen.flappycow.sprites.PowerUp;

import java.util.List;

public class Revive {

    private int numberOfRevive;
    public Revive(int n)
    {
        numberOfRevive = n;
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

        List<Obstacle> obstacles = gameView.getObstacles();
        List<PowerUp> powerUps = gameView.getPowerUps();
        PlayableCharacter player = gameView.getPlayerInstance();
        Game game = gameView.getGame();

        game.gameOverDialog.hide();
        player.setY(gameView.getHeight()/2 - player.getWidth()/2);
        player.setX(gameView.getWidth()/6);
        obstacles.clear();
        powerUps.clear();

        gameView.setObstacles(obstacles);
        gameView.setPowerUps(powerUps);
        SurfaceHolder holder = gameView.getHolderInstance();
        player.revive();

        for(int i = 0; i < 6; ++i){
            while(!holder.getSurface().isValid()){/*wait*/}
            Canvas canvas = gameView.getCanvas();
            gameView.drawCanvas(canvas, i%2 == 0);
            holder.unlockCanvasAndPost(canvas);
            // sleep
            try { Thread.sleep(gameView.UPDATE_INTERVAL*6); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        gameView.resume();
        gameView.setPowerUps(powerUps);
        gameView.setObstacles(obstacles);
        gameView.setPlayerInstance(player);
    }
}
