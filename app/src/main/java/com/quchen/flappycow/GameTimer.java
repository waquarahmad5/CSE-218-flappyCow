package com.quchen.flappycow;

import java.util.Timer;
import java.util.TimerTask;
import org.jetbrains.annotations.Contract;

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
        if(isTimerNotNull(timer)){
            timer.cancel();
            timer.purge();
        }
        if(isTimerTaskNotNull(task)){
            task.cancel();
        }


    }

    @Contract(value = "null -> false; !null -> true", pure = true)
    private boolean isTimerTaskNotNull(TimerTask task){
        return task != null;
    }
    @Contract(value = "null -> false; !null -> true", pure = true)
    private boolean isTimerNotNull(Timer timer) {
    return timer != null;
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
