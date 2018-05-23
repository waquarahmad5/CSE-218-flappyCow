package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public class SpriteMaker {
    private SpriteBuilder spriteBuilder;
    GameView view;
    Game game;
    public SpriteMaker(SpriteBuilder sp, GameView view, Game game ) {
        this.spriteBuilder = sp;
        this.view = view;
        this.game = game;
    }

    public Sprite getSprite() {
        return this.spriteBuilder.getSprite();
    }

    public void makeSprite() {
        this.spriteBuilder.buildSprite(view, game);
    }

}
