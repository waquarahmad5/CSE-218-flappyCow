/**
 * The Game
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.google.android.gms.games.Games;
import com.google.android.gms.common.api.GoogleApiClient;
import com.quchen.flappycow.sprites.Coin;
import com.quchen.flappycow.sprites.Obstacle;
import com.quchen.flappycow.sprites.PowerUp;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;
import com.quchen.flappycow.sprites.NyanToast;

import java.util.List;

public class Game extends BaseGameActivity{
    /** Name of the SharedPreference that saves the medals */
    public static final String coin_save = "coin_save";
    
    /** Key that saves the medal */
    public static final String coin_key = "coin_key";
    
    /** Will play things like mooing */
    public static SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    
    public static final int GAMES_PER_AD = 3;
    /** Counts number of played games */
    private static int gameOverCounter = 1;

    
    /**
     * Will play songs like:
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan nyan
     * Does someone know the second verse ???
     */
    public static MediaPlayer musicPlayer = null;
    
    /**
     * Whether the music should play or not
     */
    public boolean musicShouldPlay = false;
    
    /** Time interval (ms) you have to press the backbutton twice in to exit */
    private static final long DOUBLE_BACK_TIME = 1000;
    
    /** Saves the time of the last backbutton press*/
    private long backPressed;

    /** Hold all accomplishments */
    AccomplishmentBox accomplishmentBox;

    /** The view that handles all kind of stuff */
    GameView view;

    /** This will increase the revive price */
    public int numberOfRevive = 1;

    /** The dialog displayed when the game is over*/
    GameOverDialog gameOverDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accomplishmentBox = new AccomplishmentBox();
        view = new GameView(this);
        gameOverDialog = new GameOverDialog(this);
        setContentView(view);
        // Query check takes place in the client, isMusicPlayerNull passed as a boolean argument
        initMusicPlayer(isMusicPlayerNull());
        loadCoins();
        if(gameOverCounter % GAMES_PER_AD == 0) {
            view.setupAd();
        }
    }

    /**
     * Initializes the player with the nyan cat song
     * and sets the position to 0.
     */
    //Supplier, avoids the query check on supplier.
    public void initMusicPlayer(boolean isNull){
        if(isNull){
            // to avoid unnecessary reinitialisation
            musicPlayer = MediaPlayer.create(this, R.raw.nyan_cat_theme);
            musicPlayer.setLooping(true);
            musicPlayer.setVolume(MainActivity.volume, MainActivity.volume);
        }
        musicPlayer.seekTo(0);    // Reset song to position 0
    }
    //Created a method to check for pre-condition
    private boolean isMusicPlayerNull() {
        return musicPlayer == null;
    }

    private void loadCoins(){
        SharedPreferences saves = this.getSharedPreferences(coin_save, 0);
        accomplishmentBox.setCoins(saves.getInt(coin_key, 0) + 100); //TODO: REMOVE 100 :P -SR
    }

    /**
     * Pauses the view and the music
     */
    @Override
    protected void onPause() {
        view.pause();
        if(musicPlayer.isPlaying()){
            musicPlayer.pause();
        }
        super.onPause();
    }

    /**
     * Resumes the view (but waits the view waits for a tap)
     * and starts the music if it should be running.
     * Also checks whether the Google Play Services are available.
     */
    //separated command that checks for availablity of play services and sets the status in the same method.
    @Override
    protected void onResume() {
        view.drawOnce();
        if(musicShouldPlay){
            musicPlayer.start();
        }
        if(isPlayServicesNotAvailable()){
            Toast.makeText(this, "Please check your Google Services", Toast.LENGTH_LONG).show();
        }
        super.onResume();
    }
    //separated query for if playservices is not available
    private boolean isPlayServicesNotAvailable() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) != ConnectionResult.SUCCESS;
    }

    /**
     * Prevent accidental exits by requiring a double press.
     */
    //Separated command from query
    @Override
    public void onBackPressed() {
        if(isBackPressed()){
            super.onBackPressed();
        }else{
            backPressed = System.currentTimeMillis();
            Toast.makeText(this, getResources().getString(R.string.on_back_press), Toast.LENGTH_LONG).show();
        }
    }
    //Query
    private boolean isBackPressed() {
        return System.currentTimeMillis() - backPressed < DOUBLE_BACK_TIME;
    }

    /**
     * Sends the handler the command to show the GameOverDialog.
     * Because it needs an UI thread.
     */
    public int getGameOverCounter(){
        return gameOverCounter;
    }

    /**
     * Increase coins of the game
     */
    public void increaseCoin(){
        accomplishmentBox.increaseCoins(this);
    }

    /**
     * Increase points of the game
     */
    public void increasePoints(){
        accomplishmentBox.increasePoints(this);
        this.view.getPlayer().upgradeBitmap(accomplishmentBox.getPoints()); //CURRENTLY EMPTY
    }

    /**
     * Make an announcement in the game
     */
    public void announcement(int toBeAnnounced, int announcementText){
        if(getApiClient().isConnected()){
            Games.Achievements.unlock(getApiClient(), getResources().getString(toBeAnnounced));
        }else{
            view.msgHandler.sendMessage(Message.obtain(view.msgHandler, MessageHandler.SHOW_TOAST, announcementText, MessageHandler.SHOW_TOAST));
        }
    }

    /** Moved Obstacle class from gameview to game.
     * Checks whether an obstacle is passed.
     */
    public void checkPasses(){

        List<Obstacle> obstacles = view.getObstacles();
        List<PowerUp> powerUps = view.getPowerUps();

        for(Obstacle o : obstacles){
            if(o.isPassed()){
                if(!o.isAlreadyPassed){    // probably not needed
                    o.onPass();
                    createPowerUp(powerUps);
                }
            }
        }

        view.setPowerUps(powerUps);
    }

    /**
     * Creates a power up
     */
    private void createPowerUp(List<PowerUp> powerUps){
        // Toast
        if(accomplishmentBox.getPoints() >= AccomplishmentBox.getPointsToToast() /*&& powerUps.size() < 1*/ && !(view.isPlayerNyanCat())){
            // If no powerUp is present and you have more than / equal 42 points
            if(accomplishmentBox.getPoints() == AccomplishmentBox.getPointsToToast()){    // First time 100 % chance
                powerUps.add(new NyanToast(view,this));
            } else if(Math.random()*100 < 33){    // 33% chance
                powerUps.add(new NyanToast(view,this));
            }
        }

        if((powerUps.size() < 1) && (Math.random()*100 < 20)){
            // If no powerUp is present and 20% chance
            powerUps.add(new Coin(view,this));
        }
    }

    public void setNumberOfRevive(int numberOfRevive) {
        this.numberOfRevive = numberOfRevive;
    }


    public GoogleApiClient getApiClient(){
        return mHelper.getApiClient();
    }

    public void incrementGameCounter(){
        ++gameOverCounter;
    }

    @Override
    public void onSignInFailed() {}

    @Override
    public void onSignInSucceeded() {}

}
