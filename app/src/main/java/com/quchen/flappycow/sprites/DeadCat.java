package com.quchen.flappycow.sprites;

public class DeadCat implements DeadBehavior{

    /**
     * A dead cat falls slowly to the ground.
     */
    public void dead(PlayableCharacter player) {
        player.isDead = true;
        player.speedY = player.getMaxSpeed()/2;
        player.row = 1;

    }
}
