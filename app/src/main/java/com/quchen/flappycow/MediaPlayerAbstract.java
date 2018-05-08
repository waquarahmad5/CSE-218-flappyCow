package com.quchen.flappycow;

import org.jetbrains.annotations.Contract;

public abstract class MediaPlayerAbstract {

    MediaPlayerInterface musicPlayer;
    Game g;

    public abstract void initMusicPlayer(boolean isNull);
    //Created a method to check for pre-condition

    public abstract boolean isMusicPlayerNull();
    public abstract boolean isPlaying();

    public abstract void pause();

    public abstract void start();

}
