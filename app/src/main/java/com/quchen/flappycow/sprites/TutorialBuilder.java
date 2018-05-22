package com.quchen.flappycow.sprites;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public class TutorialBuilder implements SpriteBuilder {

    private Tutorial sprite;
    public TutorialBuilder(GameView view, Game game) {
        this.sprite = new Tutorial(view, game);
    }
    public void buildSprite(GameView view, Game game) {
        this.sprite = new Tutorial(view, game);
    }

    @Override
    public Sprite getSprite() {
        return this.sprite;
    }


}
