/**
 * Saves achievements and score in shared preferences.
 * You should use a SQLite DB instead, but I'm too lazy to chance it now.
 * 
 * @author Lars Harmsen
 * Copyright (c) <2014> <Lars Harmsen - Quchen>
 */

package com.quchen.flappycow;

import com.google.android.gms.games.Games;
import com.google.android.gms.common.api.GoogleApiClient;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class AccomplishmentBox{
    /** Points needed for a gold medal */
    private static final int GOLD_POINTS = 100;
    
    /** Points needed for a silver medal */
    private static final int SILVER_POINTS = 50;
    
    /** Points needed for a bronze medal */
    private static final int BRONZE_POINTS = 10;

    /** Points needed for a Toast */
    private static final int POINTS_TO_TOAST = 4; //TODO: SET TO 42 -AE

    private static final String SAVE_NAME = "ACCOMPLISHMENTS";
    private static final String ONLINE_STATUS_KEY = "online_status";
    private static final String KEY_POINTS = "points";

    private static final String ACHIEVEMENT_KEY_50_COINS = "achievement_survive_5_minutes";
    private static final String ACHIEVEMENT_KEY_TOASTIFICATION = "achievement_toastification";
    private static final String ACHIEVEMENT_KEY_BRONZE = "achievement_bronze";
    private static final String ACHIEVEMENT_KEY_SILVER = "achievement_silver";
    private static final String ACHIEVEMENT_KEY_GOLD = "achievement_gold";

    private int points; /** The amount of points gained */


    private int coins;    /** The amount of collected coins */


    private boolean achievement_50_coins;       /** 50 coins achievement attained */
    private boolean achievement_toastification; /** NyanCat achievement attained */
    private boolean achievement_bronze;         /** Bronze medal achievement attained */
    private boolean achievement_silver;         /** Silver medal achievement attained */
    private boolean achievement_gold;           /** Gold medal achievement attained */

    /**
     * Stores the score and achievements locally.
     * 
     * The accomblishments will be saved local via SharedPreferences.
     * This makes it very easy to cheat.
     * 
     * @param activity activity that is needed for shared preferences
     */
    public void saveLocal(Activity activity){
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        
        if(points > saves.getInt(KEY_POINTS, 0)){
            editor.putInt(KEY_POINTS, points);
        }
        if(achievement_50_coins){
            editor.putBoolean(ACHIEVEMENT_KEY_50_COINS, true);
        }
        if(achievement_toastification){
            editor.putBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, true);
        }
        if(achievement_bronze){
            editor.putBoolean(ACHIEVEMENT_KEY_BRONZE, true);
        }
        if(achievement_silver){
            editor.putBoolean(ACHIEVEMENT_KEY_SILVER, true);
        }
        if(achievement_gold){
            editor.putBoolean(ACHIEVEMENT_KEY_GOLD, true);
        }
        
        editor.commit();
    }
    
    /**
     * Uploads accomplishments to Google Play Services
     * @param activity
     * @param apiClient
     */
    public void submitScore(Activity activity, GoogleApiClient apiClient){
        Games.Leaderboards.submitScore(apiClient, activity.getResources().getString(R.string.leaderboard_highscore), this.points);
        
        if(achievement_50_coins){
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_50_coins));
        }
        if(achievement_toastification){
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_toastification));
        }
        if(achievement_bronze){
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_bronze));
        }
        if(achievement_silver){
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_silver));
        }
        if(achievement_gold){
            Games.Achievements.unlock(apiClient, activity.getResources().getString(R.string.achievement_gold));
        }
        
        AccomplishmentBox.savesAreOnline(activity);

        Toast.makeText(activity.getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * reads the local stored data
     * @param activity activity that is needed for shared preferences
     * @return local stored score and achievements
     */
    public static AccomplishmentBox getLocal(@NotNull Activity activity){
        AccomplishmentBox box = new AccomplishmentBox();
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        
        box.points = saves.getInt(KEY_POINTS, 0);
        box.achievement_50_coins = saves.getBoolean(ACHIEVEMENT_KEY_50_COINS, false);
        box.achievement_toastification = saves.getBoolean(ACHIEVEMENT_KEY_TOASTIFICATION, false);
        box.achievement_bronze = saves.getBoolean(ACHIEVEMENT_KEY_BRONZE, false);
        box.achievement_silver = saves.getBoolean(ACHIEVEMENT_KEY_SILVER, false);
        box.achievement_gold = saves.getBoolean(ACHIEVEMENT_KEY_GOLD, false);
        
        return box;
    }
    
    /**
     * marks the data as online
     * @param activity activity that is needed for shared preferences
     */

    public static void savesAreOnline(@NotNull Activity activity){
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putBoolean(ONLINE_STATUS_KEY, true);
        editor.commit();
    }
    
    /**
     * marks the data as offline
     * @param activity activity that is needed for shared preferences
     */

    public static void savesAreOffline(@NotNull Activity activity){
        SharedPreferences saves = activity.getSharedPreferences(SAVE_NAME, 0);
        SharedPreferences.Editor editor = saves.edit();
        editor.putBoolean(ONLINE_STATUS_KEY, false);
        editor.commit();
    }

    /**
     * checks if the last data is already uploaded
     * @param activity activity that is needed for shared preferences
     * @return wheater the last data is already uploaded
     */
    public static boolean isOnline(@NotNull Activity activity){
        return activity.getSharedPreferences(SAVE_NAME, 0).getBoolean(ONLINE_STATUS_KEY, true);
    }

    public void increasePoints(Game game) {
        points++;
        checkForPointAchievements(game);
    }

    @org.jetbrains.annotations.Contract(value = "true, _ -> false", pure = true)
    private boolean checkForAchievements(boolean achievement_state, int achievement_points) {
        if(!achievement_state && points >= achievement_points)
            return true;
        else
            return false;
    }
    private void checkForPointAchievements(Game game){
        if(checkForAchievements(achievement_gold,GOLD_POINTS)){
            achievement_gold = true;
            game.announcement(R.string.achievement_gold, R.string.toast_achievement_gold);
        }else if(checkForAchievements(achievement_silver,SILVER_POINTS)) {
            achievement_silver = true;
            game.announcement(R.string.achievement_silver, R.string.toast_achievement_silver);
        }else if(checkForAchievements(achievement_bronze,BRONZE_POINTS)) {
            achievement_bronze = true;
            game.announcement(R.string.achievement_bronze, R.string.toast_achievement_bronze);
        }
    }


    public int getPoints(){
        return points;
    }
    public int getCoins(){
        return coins;
    }
    public boolean getAchievementBronze() {return achievement_bronze;}
    public boolean getAchievementSilver() {return achievement_silver;}
    public boolean getAchievementGold() {return achievement_gold;}
    @Contract(pure = true)
    public static int getPointsToToast() {return POINTS_TO_TOAST;}
    public String getSaveName(){return SAVE_NAME;}
    public String getOnlineStatusKey(){return ONLINE_STATUS_KEY;}

    public void setCoins(int incCoins){
        coins = incCoins;
    }
    public void setToastification(){
        achievement_toastification = true;
    }

    public void increaseCoins(Game game){
        coins++;
        checkForCoinAchievements(game);
    }


    private void checkForCoinAchievements(Game game){
        if(!achievement_50_coins && coins >= 50){
            achievement_50_coins = true;
            game.announcement(R.string.achievement_50_coins, R.string.toast_achievement_50_coins);
        }
    }
}