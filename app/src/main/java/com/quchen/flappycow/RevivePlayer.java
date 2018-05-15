package com.quchen.flappycow;

import com.quchen.flappycow.sprites.PlayableCharacter;

public class RevivePlayer implements ReviveObserver {

    @Override
    public void update(GameView gameView) {
        PlayableCharacter player = gameView.getPlayerInstance();
        Game game = gameView.getGame();

        game.gameOverDialog.hide();
        player.setY(gameView.getHeight()/2 - player.getWidth()/2);
        player.setX(gameView.getWidth()/6);
        gameView.setPlayerInstance(player);
    }
}
