package com.quchen.flappycow.sprites;


import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;
import android.graphics.Canvas;

public class CheckCollision implements CollisionMediator{

    //Terrain spider
    public boolean CheckCollisionM(Sprite obs, PlayableCharacter p, Game g) {
            if (isColliding(obs,p,g))
            {
                return true;
    }
        return false;
    }
    public boolean isColliding(Sprite sprite, PlayableCharacter p, Game g) {
        if(sprite.x + getCollisionTolerance(g) < p.x + p.width
                && sprite.x + sprite.width > p.x + getCollisionTolerance(g)
                && sprite.y + getCollisionTolerance(g) < p.y + p.height
                && sprite.y + sprite.height > p.y + getCollisionTolerance(g)){
            return true;
        }
        return false;
    }
    public int getCollisionTolerance(Game g){
        // 25 @ 720x1280 px
        return g.getResources().getDisplayMetrics().heightPixels / 50;
    }

}
