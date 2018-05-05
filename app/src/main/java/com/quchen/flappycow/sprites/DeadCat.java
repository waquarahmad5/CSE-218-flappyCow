package com.quchen.flappycow.sprites;

public class DeadCat implements DeadBehavior{

    public void dead(PlayableCharacter player) {
        player.isDead = true;
        player.speedY = player.getMaxSpeed()/2;
        player.row = 1;

    }
}
