package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Interfaces.DeadBehavior;

public class DeadCow implements DeadBehavior {

    /**
     * A dead cow falls slowly to the ground.
     */
    public void dead(PlayableCharacter player) {
        player.row = 3;
        player.frameTime = 3;
        player.setIsDead(true);
        player.speedY = player.getMaxSpeed()/2;
    }
}
