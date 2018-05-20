package com.quchen.flappycow.Interfaces;

import android.graphics.Bitmap;

import com.quchen.flappycow.Game;
import com.quchen.flappycow.GameView;
import com.quchen.flappycow.R;
import com.quchen.flappycow.Util;
import com.quchen.flappycow.sprites.PlayableCharacter;
import com.quchen.flappycow.sprites.Sprite;

import android.graphics.Canvas;

public interface CollisionMediator{

    public boolean CheckCollisionM(Sprite object, PlayableCharacter p, Game g);
}
