package com.quchen.flappycow;

public interface MediaPlayerInterface {

    void setLooping(boolean val);
    boolean isPlaying();
    void setVolume(float a , float b);
    void start();
    void pause();
    void seekTo(int a);

}
