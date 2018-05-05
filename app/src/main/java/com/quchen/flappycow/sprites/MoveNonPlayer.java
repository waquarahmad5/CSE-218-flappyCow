package com.quchen.flappycow.sprites;

public class MoveNonPlayer implements MoveNonPlayableCharacterBehaviour {
    public void move(Sprite sprite){
        sprite.x+= sprite.speedX;
        sprite.y+= sprite.speedY;
    };

}
