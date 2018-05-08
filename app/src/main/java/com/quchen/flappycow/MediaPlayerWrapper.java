package com.quchen.flappycow;

import android.media.MediaPlayer;

public class MediaPlayerWrapper {

    MediaPlayer legacyPlayer;

    public MediaPlayerWrapper(Game g)
    {
        legacyPlayer = MediaPlayer.create(g , R.raw.nyan_cat_theme);
        legacyPlayer.setLooping(true);
        legacyPlayer.setVolume(MainActivity.volume, MainActivity.volume);
        legacyPlayer.seekTo(0);
    }
    void setLooping(){


    }
    void setVolume(float left_vol, float right_vol){

    }
    public boolean isPlaying(){
        return legacyPlayer.isPlaying();

    }
    public void pause(){
        legacyPlayer.pause();
    }
    void start(){
        legacyPlayer.start();
    }
    void seekTo() {

    }

}
