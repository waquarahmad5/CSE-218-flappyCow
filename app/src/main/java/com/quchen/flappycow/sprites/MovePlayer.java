package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Interfaces.MovePlayableCharacterBehavior;
import com.quchen.flappycow.aspectj.DebugTrace;

public abstract class MovePlayer implements MovePlayableCharacterBehavior {

    @DebugTrace
    public void move( PlayableCharacter player) {

        player.x = player.view.getWidth() / 6;

        if(player.speedY < 0){
            // The character is moving up
            player.speedY = player.speedY * 2 / 3 + player.getSpeedTimeDecrease() / 2;
        }else{
            // the character is moving down
            player.speedY += player.getSpeedTimeDecrease();
        }

        if(player.speedY > player.getMaxSpeed()){
            // speed limit
            player.speedY = player.getMaxSpeed();
        }

        player.x+= player.speedX;
        player.y+= player.speedY;
    }

}
