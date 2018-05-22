package com.quchen.flappycow.sprites;

import android.graphics.Canvas;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public class PauseButtonBuilder implements SpriteBuilder {
    private PauseButton sprite;
    public PauseButtonBuilder(GameView view, Game game) {
        this.sprite = new PauseButton(view,game);
    }

    public void update(int x, Canvas c) {
        this.move();
    }

    @Override
    public void buildSprite(GameView view, Game game) {
        this.sprite = new PauseButton(view, game);
    }

    @Override
    public Sprite getSprite() {
        return this.sprite;
    }


    public void move() {
        sprite.x = sprite.view.getWidth() / 2 - sprite.width / 2;
        sprite.y = sprite.view.getHeight() / 2 - sprite.height / 2;
    }


}
