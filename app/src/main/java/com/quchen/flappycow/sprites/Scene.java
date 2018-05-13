package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;

public class Scene extends Sprite implements SharedObjects {

    /** Static bitmap to reduce memory usage */
    public Bitmap globalBitmap;

    public enum X_GROUND {FOREGROUND, BACKGROUND}
    int state;
    public Scene(GameView view, Game game, X_GROUND x_ground){
        super(view, game);

        if(isGlobalBitmapNull()) {
            switch (x_ground) {
                case BACKGROUND: {
                    globalBitmap = Util.getDownScaledBitmapAlpha8(game, R.drawable.bg);
                    state = 0;
                    break;
                }
                case FOREGROUND: {
                    globalBitmap = Util.getDownScaledBitmapAlpha8(game, R.drawable.fg);
                    state = 1;
                    break;
                }
            }
        }
        this.bitmap = globalBitmap;
        moveNonPlayer = new MoveNonPlayer();
    }

    private boolean isGlobalBitmapNull() {
        return globalBitmap == null;
    }

    /**
     * Draws the bitmap to the Canvas.
     * The height of the bitmap will be scaled to the height of the canvas.
     * When the bitmap is scrolled to far to the left, so it won't cover the whole screen,
     * the bitmap will be drawn another time behind the first one.
     */
    public void draw(Canvas canvas) {
        double factor = (1.0 * canvas.getHeight()) / bitmap.getHeight();

        if (-x > bitmap.getWidth()) {
            // The first bitmap is completely out of the screen
            x += bitmap.getWidth();
        }

        int endBitmap = Math.min(-x + (int) (canvas.getWidth() / factor), bitmap.getWidth());
        int endCanvas = (int) ((endBitmap + x) * factor) + 1;
        src.set(-x, 0, endBitmap, bitmap.getHeight());
        dst.set(0, 0, endCanvas, canvas.getHeight());
        canvas.drawBitmap(this.bitmap, src, dst, null);

        if (endBitmap == bitmap.getWidth()) {
            // draw second bitmap
            src.set(0, 0, (int) (canvas.getWidth() / factor), bitmap.getHeight());
            dst.set(endCanvas, 0, endCanvas + canvas.getWidth(), canvas.getHeight());
            canvas.drawBitmap(this.bitmap, src, dst, null);
        }
    }


     public void move(){
        // changeToNextFrame();
        // Its more efficient if only the classes that need this implement it in their move method.
         moveNonPlayer.move(this);
    }

    public void update( int speed, Canvas canvas ) {
        if (state == 0) {
            this.setSpeedX(-speed / 2);
        }
        else {
            this.setSpeedX(-speed * 4 / 3);
        }
        move();
        draw(canvas);
    }
}
