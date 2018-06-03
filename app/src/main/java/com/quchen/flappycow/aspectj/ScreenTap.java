package com.quchen.flappycow.aspectj;

public class ScreenTap {
    private long tapCount;

    public ScreenTap() {
        //empty
    }

    private void reset() {
        tapCount = 0;
    }

    public void tap() {
        tapCount++;
    }

    public long getTapCount() {
        return tapCount;
    }

}
