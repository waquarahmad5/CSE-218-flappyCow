package com.quchen.flappycow;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private GameView gameView;
    TimerTask task;
    private long UPDATE_INTERVAL;

    public GameTimer(GameView gameView, TimerTask task, long UPDATE_INTERVAL){
        this.task = task;
        timer = new Timer();
        this.gameView = gameView;
        this.UPDATE_INTERVAL = UPDATE_INTERVAL;
    }

    public void startTimer() {
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(task, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    public void stopTimer()
    {
        if(timer != null){
            timer.cancel();
            timer.purge();
        }

        if(task != null){
            task.cancel();
        }
    }
    private void setUpTimerTask(){
        stopTimer();
        task = new TimerTask() {
            @Override
            public void run() {
                gameView.run();
            }
        };
    }
}
