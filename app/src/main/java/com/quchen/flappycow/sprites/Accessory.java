package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;

public class Accessory extends Sprite {
    
    public Accessory(GameView view, Game game) {
        super(view, game);
        drawBehavior = new DrawSpriteBehavior();
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
    }

    public void draw(Canvas canvas) {
        if(this.bitmap != null){
            drawBehavior.draw(canvas, this);
        }
    }
}
