package com.quchen.flappycow.sprites;

public interface StaticObjects {
    /**
     * What should be done, when the player collide with this StaticObject?
     */
    void onCollision();

    //boolean isColliding(Sprite sprite);

    /**
     * Checks whether this object is so far to the left, it's not visible anymore.
     * @return
     */
    boolean isOutOfRange();
}
