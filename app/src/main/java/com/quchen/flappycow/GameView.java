/**
 * GameView
 * Probably the most important class for the game
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.quchen.flappycow.sprites.Cow;
import com.quchen.flappycow.sprites.NyanCat;
import com.quchen.flappycow.sprites.Obstacle;
import com.quchen.flappycow.sprites.PauseButton;
import com.quchen.flappycow.sprites.PlayableCharacter;
import com.quchen.flappycow.sprites.PowerUp;
import com.quchen.flappycow.sprites.Scene;
import com.quchen.flappycow.sprites.Tutorial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Message;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.google.android.gms.games.Games;

public class GameView extends SurfaceView{
    
    /** Milliseconds for game timer tick */
    public static final long UPDATE_INTERVAL = 50;        // = 20 FPS
    public InterstitialAd interstitial;
    private GameTimer gameTimer = new GameTimer(this, UPDATE_INTERVAL);

    /** The surfaceholder needed for the canvas drawing */
    private SurfaceHolder holder;
    
    private Game game;
    private PlayableCharacter player;
    private Scene background;
    private Scene foreground;
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private List<PowerUp> powerUps = new ArrayList<PowerUp>();
    
    private PauseButton pauseButton;
    volatile private boolean paused = true;
    
    private Tutorial tutorial;
    private boolean tutorialIsShown = true;

    /** To do UI things from different threads */
    public MessageHandler msgHandler;

    public GameView(Context context) {
        super(context);
        this.game = (Game) context;
        setFocusable(true);

        holder = getHolder();
        player = new Cow(this, game);
        background = new Scene(this, game, Scene.X_GROUND.BACKGROUND);
        foreground = new Scene(this, game, Scene.X_GROUND.FOREGROUND);
        pauseButton = new PauseButton(this, game);
        tutorial = new Tutorial(this, game);
        msgHandler = new MessageHandler(this.game, this);
    }
    
    @Override
    public boolean performClick() {
        return super.performClick();
        // Just to remove the stupid warning
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if(event.getAction() == MotionEvent.ACTION_DOWN  // Only for "touchdowns"
                && !this.player.isDead()){ // No support for dead players
            if(tutorialIsShown){
                // dismiss tutorial
                tutorialIsShown = false;
                resume();
                this.player.onTap();
            }else if(paused){
                resume();
            }else if(pauseButton.isTouching((int) event.getX(), (int) event.getY()) && !this.paused){
                pause();
            }else{
                this.player.onTap();
            }
        }
        return true;
    }
    
    /**
     * content of the timertask
     */
    public void run() {
        game.checkPasses();
        checkPowerUpsOutOfRange();
        checkObstaclesOutOfRange();
        checkObstaclesCollision();
        checkPowerUpsCollision();
        createObstacle();
        move();
        draw();
    }

    public Canvas getCanvas() {
        Canvas canvas;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas = holder.lockHardwareCanvas();
        } else {
            canvas = holder.lockCanvas();
        }

        return canvas;
    }
    
    /**
     * Draw Tutorial
     */
    public void showTutorial(){
        player.move();
        pauseButton.move();
        
        while(!holder.getSurface().isValid()){
            /*wait*/
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        
        Canvas canvas = getCanvas();
        drawCanvas(canvas, true);
        tutorial.move();
        tutorial.draw(canvas);
        holder.unlockCanvasAndPost(canvas);
    }
    
    public void pause(){
        //stopTimer();
        gameTimer.stopTimer();
        paused = true;
    }
    
    public void drawOnce(){
        (new Thread(new Runnable() {
            @Override
            public void run() {
                if(tutorialIsShown){
                    showTutorial();
                } else {
                    draw();
                }
            }
        })).start();
    }
    
    public void resume(){
        paused = false;
        //startTimer();
        gameTimer.startTimer();

    }

    /**
     * Draws all gameobjects on the surface
     */
    private void draw() {
        while(!holder.getSurface().isValid()){
            /*wait*/
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        Canvas canvas = getCanvas();

        drawCanvas(canvas, true);

        holder.unlockCanvasAndPost(canvas);
    }

    /**
     * Draws everything normal,
     * except the player will only be drawn, when the parameter is true
     * @param drawPlayer
     */
    public void drawCanvas(Canvas canvas, boolean drawPlayer){
        background.draw(canvas);
        for(Obstacle r : obstacles){
            r.draw(canvas);
        }
        for(PowerUp p : powerUps){
            p.draw(canvas);
        }
        if(drawPlayer){
            player.draw(canvas);
        }
        foreground.draw(canvas);
        pauseButton.draw(canvas);
        
        // Score Text
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(getScoreTextMetrics());
        canvas.drawText(game.getResources().getString(R.string.onscreen_score_text) + " " + game.accomplishmentBox.getPoints()
                        + " / " + game.getResources().getString(R.string.onscreen_coin_text) + " " + game.accomplishmentBox.getCoins(),
                        0, getScoreTextMetrics(), paint);
    }
    
    /**
     * Let the player fall to the ground
     */
    private void playerDeadFall(){
        player.dead();
        do{
            player.move();
            draw();
            // sleep
            try { Thread.sleep(UPDATE_INTERVAL/4); } catch (InterruptedException e) { e.printStackTrace(); }
        }while(!player.isTouchingGround());
    }

    public boolean isPlayerNyanCat(){
        return(player instanceof NyanCat);
    }
    
    /**
     * Checks whether the obstacles or powerUps are out of range and deletes them
     */
    private void checkObstaclesOutOfRange() {
        for (int i = 0; i < obstacles.size(); i++) {
            if (this.obstacles.get(i).isOutOfRange()) {
                this.obstacles.remove(i);
                i--;
            }
        }
    }
    private void checkPowerUpsOutOfRange() {
        for(int i=0; i<powerUps.size(); i++){
            if(this.powerUps.get(i).isOutOfRange()){
                this.powerUps.remove(i);
                i--;
            }
        }
    }
    
    /**
     * Checks collision with obstacles/edges and end game if so
     */
    private void checkObstaclesCollision(){
        for(Obstacle o : obstacles){
            if(o.isColliding(player)){
                o.onCollision();
                gameOver();
            }
        }
        if(player.isTouchingEdge()){
            gameOver();
        }
    }

    /**    checks collisions with PowerUps
     *
     */
    private void checkPowerUpsCollision() {
        for(int i=0; i<powerUps.size(); i++){
            if(this.powerUps.get(i).isColliding(player)){
                this.powerUps.get(i).onCollision();
                this.powerUps.remove(i);
                i--;
            }
        }
    }

    /**
     * if no obstacle is present a new one is created
     */
    private void createObstacle(){
        if(obstacles.size() < 1){
            obstacles.add(new Obstacle(this, game));
        }
    }
    
    /**
     * Update sprite movements
     */
    private void move(){
        for(Obstacle o : obstacles){
            o.setSpeedX(-getSpeedX());
            o.move();
        }
        for(PowerUp p : powerUps){
            p.move();
        }
        
        background.setSpeedX(-getSpeedX()/2);
        background.move();
        
        foreground.setSpeedX(-getSpeedX()*4/3);
        foreground.move();
        
        pauseButton.move();
        
        player.move();
    }
    
    /**
     * Changes the player to Nyan Cat
     */
    public void changeToNyanCat(){
        game.accomplishmentBox.setToastification();
        game.announcement(R.string.achievement_toastification, R.string.toast_achievement_toastification);
        
        PlayableCharacter tmp = this.player;
        this.player = new NyanCat(this, game);
        this.player.setX(tmp.getX());
        this.player.setY(tmp.getY());
        this.player.setSpeedX(tmp.getSpeedX());
        this.player.setSpeedY(tmp.getSpeedY());
        
        game.musicShouldPlay = true;
        Game.musicPlayer.start();
    }
    
    /**
     * return the speed of the obstacles/cow
     */
    public int getSpeedX(){
        // 16 @ 720x1280 px
        int speedDefault = this.getWidth() / 45;
        
        // 1,2 every 4 points @ 720x1280 px
        int speedIncrease = (int) (this.getWidth() / 600f * (game.accomplishmentBox.getPoints() / 4));
        
        int speed = speedDefault + speedIncrease;
        
        return Math.min(speed, 2*speedDefault);
    }
    
    /**
     * Let's the player fall down dead, makes sure the runcycle stops
     * and invokes the next method for the dialog and stuff.
     */
    //Separate the command deciding whether to show AD or not from the query, to check if an Ad is to be shown
    public void gameOver(){
        pause();
        playerDeadFall();
        if(shouldShowAd()) {
            msgHandler.sendMessage(Message.obtain(msgHandler, MessageHandler.SHOW_AD));
        } else {
            msgHandler.sendMessage(Message.obtain(msgHandler, MessageHandler.GAME_OVER_DIALOG));
        }
    }
    //the query that returns true if numbr of games per Ad threshold is reacehd.
    private boolean shouldShowAd() {
        return game.getGameOverCounter() % Game.GAMES_PER_AD == 0;
    }


    /**
     * A value for the position and size of the onScreen score Text
     */
    public int getScoreTextMetrics(){
        return (int) (this.getHeight() / 21.0f);
        /*/ game.getResources().getDisplayMetrics().density)*/
    }
    
    public PlayableCharacter getPlayer(){
        return this.player;
    }
    
    public Game getGame(){
        return this.game;
    }

    public void setupAd() {
        MobileAds.initialize(this.game, getResources().getString(R.string.ad_app_id));

        interstitial = new InterstitialAd(this.game);
        interstitial.setAdUnitId(getResources().getString(R.string.ad_unit_id));

        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new MyAdListener());
    }

    private class MyAdListener extends AdListener {
        public void onAdClosed() {
            msgHandler.sendMessage(Message.obtain(msgHandler, MessageHandler.GAME_OVER_DIALOG));
        }
    }

    public List<Obstacle> getObstacles()
    {
        return this.obstacles;
    }
    public List<PowerUp> getPowerUps()
    {
        return this.powerUps;
    }
    public void setObstacles(List<Obstacle> obs)
    {
        this.obstacles = obs;
    }
    public void setPowerUps(List<PowerUp> powers)
    {
        this.powerUps = powers;
    }
    public PlayableCharacter getPlayerInstance()
    {
        return this.player;
    }
    public void setPlayerInstance(PlayableCharacter p)
    {
        this.player = p;
    }
    public SurfaceHolder getHolderInstance()
    {
        return this.holder;
    }

}
