package com.quchen.flappycow.sprites;

import android.graphics.Canvas;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.Interfaces.ObserverInterface;

public abstract class AbstractObservers extends Sprite implements ObserverInterface {
    public GameView view;
    public AbstractObservers( GameView view, Game game ) {
        super(view, game);
    }
    public abstract void update( int x, Canvas c);
}
