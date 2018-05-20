package com.quchen.flappycow;

import com.quchen.flappycow.sprites.Cow;
import com.quchen.flappycow.sprites.NyanCat;
import com.quchen.flappycow.sprites.PlayableCharacter;

public class PlayCharFactory extends CharacterFactory {
    public  PlayCharFactory() {}
    public PlayableCharacter inject(Game game, GameView gameView, String charType) {
        if(hasCharacter(charType)) {
            switch (charType) {
                case "nyan cat":
                    return new NyanCat(gameView, game);

                case "pikachu":

                    break;
            }
        }
        return new Cow(gameView, game);
    }
}
