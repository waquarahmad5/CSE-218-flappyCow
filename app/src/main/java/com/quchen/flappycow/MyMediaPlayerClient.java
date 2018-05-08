package com.quchen.flappycow;

import android.media.MediaPlayer;

import org.jetbrains.annotations.Contract;

public class MyMediaPlayerClient {

    MediaPlayerWrapper musicPlayer;
    Game g;
    MyMediaPlayerClient(Game g){
        this.g = g;
    }
    public void initMusicPlayer(boolean isNull){
        if(isNull) {
            musicPlayer = new MediaPlayerWrapper(this.g);
        }
    }
    //Created a method to check for pre-condition
    @Contract(pure = true)
    public boolean isMusicPlayerNull() {
        return musicPlayer == null;
    }

    public boolean isPlaying() {
        return musicPlayer.isPlaying();
    }

    public void pause(){
        musicPlayer.pause();
    }

    public void start() {
        musicPlayer.start();
    }



}
