package com.quchen.flappycow.sprites;

import android.graphics.Canvas;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public abstract class AbstractObservers extends Sprite implements ObserverInterface {
    public AbstractObservers( GameView view, Game game ) {
        super(view, game);
    }
    public abstract void update( int x, Canvas c);
}
