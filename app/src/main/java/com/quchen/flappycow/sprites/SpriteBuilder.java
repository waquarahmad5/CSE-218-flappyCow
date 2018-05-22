package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public interface SpriteBuilder {
    public void buildSprite( GameView v, Game g);
    public Sprite getSprite();
}
