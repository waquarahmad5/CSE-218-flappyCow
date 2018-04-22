package com.quchen.flappycow.sprites;

public interface DynamicObjects {
    /**
     * Checks whether the sprite is touching the ground or the sky.
     * @return
     */
    public boolean isTouchingEdge();
    public boolean isTouchingGround();
    public boolean isTouchingSky();
}
