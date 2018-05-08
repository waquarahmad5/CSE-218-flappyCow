package com.quchen.flappycow;

import android.media.MediaPlayer;

import org.jetbrains.annotations.Contract;

public class MyMediaPlayerClient extends MediaPlayerAbstract {


    MyMediaPlayerClient(Game g){
        this.g = g;
    }
    public void initMusicPlayer(boolean isNull){
        if(isNull) {
            musicPlayer = new MediaPlayerWrapper(this.g);
        }
    }
    //Created a method to check for pre-condition
    @Override
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
