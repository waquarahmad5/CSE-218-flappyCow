package com.quchen.flappycow;

import android.media.MediaPlayer;

public class MediaPlayerWrapper implements MediaPlayerInterface {

    MediaPlayer legacyPlayer;

    public MediaPlayerWrapper(Game g)
    {
        legacyPlayer = MediaPlayer.create(g , R.raw.nyan_cat_theme);
        legacyPlayer.setLooping(true);
        legacyPlayer.setVolume(MainActivity.volume, MainActivity.volume);
        legacyPlayer.seekTo(0);
    }
    public void setLooping(boolean val){
        legacyPlayer.setLooping(val);
    }
    public void setVolume(float left_vol, float right_vol){
        legacyPlayer.setVolume(left_vol, right_vol);

    }
    public boolean isPlaying(){
        return legacyPlayer.isPlaying();

    }
    public void pause(){
        legacyPlayer.pause();
    }
    public void start(){
        legacyPlayer.start();
    }
    public void seekTo(int x) {
        legacyPlayer.seekTo(x);

    }

}
