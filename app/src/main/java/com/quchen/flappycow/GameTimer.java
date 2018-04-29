package com.quchen.flappycow;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private GameView gameView;
    private TimerTask task;
    private long UPDATE_INTERVAL;

    public GameTimer(GameView gameView, long UPDATE_INTERVAL){
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
        if(isNotNull(timer)){
            timer.cancel();
            timer.purge();
        }

        if(isNotNull(task)){
            task.cancel();
        }
    }

    private boolean isNotNull(Object timer) {

        try {
            if (timer instanceof Timer || timer instanceof TimerTask) {
                return timer != null;
            }
            else{
                throw new Exception("Expected instance of Timer");
            }
        }
        catch (Exception e)
        {
            System.out.println("Catch Block") ;
            System.out.println(e) ;
        }

        return false;
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
