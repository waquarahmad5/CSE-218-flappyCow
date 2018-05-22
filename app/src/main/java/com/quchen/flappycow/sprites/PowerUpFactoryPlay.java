package com.quchen.flappycow.sprites;
import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
public class PowerUpFactoryPlay extends PowerUpFactory {
        public  PowerUpFactoryPlay() {}
        public PowerUp inject(Game game, GameView gameView, String powerType) {
            if(hasPowerUp(powerType)) {
                switch (powerType) {
                    case "coin":
                        return new Coin(gameView,game);

                    case "heart":
                        return new Heart(gameView,game);

                }
            }
            return new Coin(gameView,game);
        }
    }


