package com.quchen.flappycow.sprites;

import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;
import android.graphics.Canvas;

public interface CollisionMediator{

    public boolean CheckCollisionM(Obstacle object, PlayableCharacter p, Game g);
}
