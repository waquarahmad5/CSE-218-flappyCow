package com.quchen.flappycow.sprites;

public class MoveCow extends MovePlayer {

    public void move( PlayableCharacter player ) {
        player.changeToNextFrame();
        super.move(player);
        if (player.row != 3) {
            // not dead
            if (player.speedY > player.getTabSpeed() / 3 && player.speedY < player.getMaxSpeed() * 1 / 3) {
                player.row = 0;
            } else if (player.speedY > 0) {
                player.row = 1;
            } else {
                player.row = 2;
            }
        }
    }
}
