package com.quchen.flappycow.sprites;


import android.graphics.Canvas;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public class ViewSprite extends AbstractObservers implements SpriteComponents {

    private String type;

    public ViewSprite( GameView view, Game game ) {
        super(view, game);
    }

    @Override
    public void update( int x, Canvas c ) {
        
    }

    public void setSpriteType(String type) {
        this.type = type;
    }


}
