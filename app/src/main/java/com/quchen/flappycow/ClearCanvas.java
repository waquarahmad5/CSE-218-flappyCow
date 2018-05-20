package com.quchen.flappycow;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.quchen.flappycow.Interfaces.ReviveObserver;

public class ClearCanvas implements ReviveObserver {
    @Override
    public void update(GameView gameView) {
        SurfaceHolder holder = gameView.getHolderInstance();
        gameView.getPlayerInstance().revive();

        for(int i = 0; i < 6; ++i){
            while(!holder.getSurface().isValid()){/*wait*/}
            Canvas canvas = gameView.getCanvas();
            gameView.drawCanvas(canvas, i%2 == 0);
            holder.unlockCanvasAndPost(canvas);
            // sleep
            try { Thread.sleep(gameView.UPDATE_INTERVAL*6); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        //gameView.resume();

    }
}
